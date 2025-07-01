package ar.com.emanar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.com.emanar.domain.Cliente;
import ar.com.emanar.domain.Producto;
import ar.com.emanar.domain.ProductoVendido;
import ar.com.emanar.domain.Venta;
import ar.com.emanar.exception.InsufficientStockException;
import ar.com.emanar.exception.ResourceNotFoundException;
import ar.com.emanar.repository.ClienteRepository;
import ar.com.emanar.repository.ProductoRepository;
import ar.com.emanar.repository.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VentaService {
	private VentaRepository ventaRepository;
	private ClienteRepository clienteRepository;
	private ProductoRepository productoRepository;
	
	public Venta findById(Long id) {
		return this.ventaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("La venta con ese ID no existe: " + id));
	}
	
	public List<Venta> findAll(){
		return this.ventaRepository.findAll();
	}
	
	@Transactional
	public Venta save (Venta venta, Long idCliente) {
		Cliente clienteGuardado = this.clienteRepository.findById(idCliente).orElseThrow(() -> new ResourceNotFoundException("El cliente con ese ID no existe: " + idCliente));
		
		List<ProductoVendido> productosVendidos = venta.getProductosVendidos();
		
		productosVendidos.forEach(productoVendido ->{
			Producto producto = this.productoRepository.findById(productoVendido.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoVendido.getProducto().getId()));
			if (producto.getStock() >= productoVendido.getCantidad()) {
				Integer stock = producto.getStock();
				producto.setStock(stock - productoVendido.getCantidad());
				this.productoRepository.save(producto);
			} else {
				throw new InsufficientStockException("La cantidad vendida excede al stock disponible.");
			}
			productoVendido.setVenta(venta);
		});
		
		venta.setCliente(clienteGuardado);
		
		return this.ventaRepository.save(venta);
	}
	
	@Transactional
	public Venta update (Venta ventaActualizada, Long idCliente, Long idVenta) {
		Optional<Venta> ventaOptional = this.ventaRepository.findById(idVenta);
		ventaActualizada.setId(idVenta);
		
		Cliente clienteGuardado = this.clienteRepository.findById(idCliente).orElseThrow(() -> new ResourceNotFoundException("El cliente con ese ID no existe: " + idCliente));
		ventaActualizada.setCliente(clienteGuardado);
		
		List<ProductoVendido> productosVendidosGuardados = ventaOptional.get().getProductosVendidos();
		List<ProductoVendido> productosVendidosActualizados = ventaActualizada.getProductosVendidos();
		
		productosVendidosActualizados.forEach(productoVendidoActualizado->{
			Producto producto = this.productoRepository.findById(productoVendidoActualizado.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoVendidoActualizado.getProducto().getId()));
			
			if (productoVendidoActualizado.getId() == null) {
				if (producto.getStock() >= productoVendidoActualizado.getCantidad()) {
					Integer stock = producto.getStock();
					producto.setStock(stock - productoVendidoActualizado.getCantidad());
					this.productoRepository.save(producto);
				} else {
					throw new InsufficientStockException("La cantidad vendida excede al stock disponible.");
				}
			} else {
				productosVendidosGuardados.forEach(productoVendidoGuardado ->{
					if (productoVendidoActualizado.getId().equals(productoVendidoGuardado.getId())) {
						if (!(productoVendidoActualizado.getCantidad().equals(productoVendidoGuardado.getCantidad()))) {
							Integer stock = producto.getStock();
							producto.setStock(stock + productoVendidoGuardado.getCantidad() - productoVendidoActualizado.getCantidad());
							if (producto.getStock() < 0) throw new InsufficientStockException("La cantidad vendida excede al stock disponible.");
							this.productoRepository.save(producto);
						}
					}
				});
			}			
			productoVendidoActualizado.setVenta(ventaActualizada);
		});
		
		productosVendidosGuardados.forEach(productoVendidoGuardado ->{
			Producto producto = this.productoRepository.findById(productoVendidoGuardado.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoVendidoGuardado.getProducto().getId()));
			
			boolean eliminado = productosVendidosActualizados.stream()
					.filter(productoVendido -> productoVendido.getId() != null)
			        .noneMatch(productoVendido -> productoVendido.getId().equals(productoVendidoGuardado.getId()));
			
			if (eliminado) {
				Integer stock = producto.getStock();
				producto.setStock(stock + productoVendidoGuardado.getCantidad());
				this.productoRepository.save(producto);
			}			
		});
		
		Venta ventaExistente = buildVenta(ventaOptional, ventaActualizada);
		
		return this.ventaRepository.save(ventaExistente);
	}
	
	@Transactional
	public void deleteById (Long id){
		Venta ventaGuardada = findById(id);
		
		ventaGuardada.getProductosVendidos().forEach(productoVendido ->{
			Producto producto = this.productoRepository.findById(productoVendido.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoVendido.getProducto().getId()));
			
			Integer stock = producto.getStock();
			producto.setStock(stock + productoVendido.getCantidad());
			this.productoRepository.save(producto);			
		});
		
		this.ventaRepository.delete(ventaGuardada);
	}

	private Venta buildVenta(Optional<Venta> ventaOptional, Venta ventaActualizada) {
		Venta.VentaBuilder ventaBuilder = Venta.builder();
		
		ventaOptional.ifPresent(venta ->{
			ventaBuilder.id(venta.getId())
			.fechaDeVenta(ventaActualizada.getFechaDeVenta())
			.total(ventaActualizada.getTotal())
			.cliente(ventaActualizada.getCliente())
			.productosVendidos(ventaActualizada.getProductosVendidos());
		});
		
		return ventaBuilder.build();
	}	
}
