package ar.com.emanar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.com.emanar.domain.FormaDePago;
import ar.com.emanar.domain.Gasto;
import ar.com.emanar.domain.Producto;
import ar.com.emanar.domain.ProductoComprado;
import ar.com.emanar.domain.Proveedor;
import ar.com.emanar.exception.InsufficientStockException;
import ar.com.emanar.exception.ResourceNotFoundException;
import ar.com.emanar.repository.GastoRepository;
import ar.com.emanar.repository.ProductoRepository;
import ar.com.emanar.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GastoService {
	private GastoRepository gastoRepository;
	private ProveedorRepository proveedorRepository;
	private ProductoRepository productoRepository;
	
	
	public Gasto findById(Long id) {
		return this.gastoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El gasto con ese ID no existe: " + id));
	}
	
	public List<Gasto> findAll(){
		return this.gastoRepository.findAll();
	}
	
	@Transactional
	public Gasto save (Gasto gasto, Long idProveedor, String nuevaFormaDePago) {
		Proveedor proveedorGuardado = this.proveedorRepository.findById(idProveedor).orElseThrow(() -> new ResourceNotFoundException("El proveedor con ese ID no existe: " +idProveedor));
		
		gasto.setProveedor(proveedorGuardado);
		gasto.setFormaDePago(FormaDePago.valueOf(nuevaFormaDePago));
		
		gasto.getProductosComprados().forEach(productoComprado ->{
			Producto producto = this.productoRepository.findById(productoComprado.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoComprado.getProducto().getId()));
			Integer stock = producto.getStock();
			producto.setStock(stock + productoComprado.getCantidad());
			this.productoRepository.save(producto);
			
			productoComprado.setGasto(gasto);
		});
		
		return this.gastoRepository.save(gasto);
	}
	
	@Transactional
	public Gasto update (Gasto gastoActualizado, Long idGasto ,Long idProveedor, String formaDePago) {
		Optional<Gasto> gastoOptional = this.gastoRepository.findById(idGasto);		
		
		Proveedor proveedorGuardado = this.proveedorRepository.findById(idProveedor).orElseThrow(() -> new ResourceNotFoundException("El proveedor con ese ID no existe: " +idProveedor));
		gastoActualizado.setProveedor(proveedorGuardado);
		gastoActualizado.setFormaDePago(FormaDePago.valueOf(formaDePago));
		
		List<ProductoComprado> productosCompradosGuardados = gastoOptional.get().getProductosComprados();
		List<ProductoComprado> productosCompradosActualizados = gastoActualizado.getProductosComprados();
		
		productosCompradosActualizados.forEach(productoCompradoActualizado ->{
			Producto producto = this.productoRepository.findById(productoCompradoActualizado.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoCompradoActualizado.getProducto().getId()));
			if(productoCompradoActualizado.getId() == null) {
				Integer stock = producto.getStock();
				producto.setStock(stock + productoCompradoActualizado.getCantidad());
				this.productoRepository.save(producto);				
			} else {
				productosCompradosGuardados.forEach(productoCompradoGuardado ->{
					if(productoCompradoActualizado.getId().equals(productoCompradoGuardado.getId())) {
						if(!(productoCompradoActualizado.getId().equals(productoCompradoGuardado.getId()))) {
							Integer stock = producto.getStock();
							producto.setStock(stock + productoCompradoActualizado.getCantidad() - productoCompradoGuardado.getCantidad());
							this.productoRepository.save(producto);
						}
					}
				});
			}
			productoCompradoActualizado.setGasto(gastoActualizado);
		});
		
		productosCompradosGuardados.forEach(productoCompradoGuardado ->{
			Producto producto = this.productoRepository.findById(productoCompradoGuardado.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoCompradoGuardado.getProducto().getId()));
			
			boolean eliminado = productosCompradosActualizados.stream()
					.filter(productoComprado -> productoComprado.getId() != null)
			        .noneMatch(productoComprado -> productoComprado.getId().equals(productoCompradoGuardado.getId()));
			
			if(eliminado) {
				Integer stock = producto.getStock();
				producto.setStock(stock - productoCompradoGuardado.getCantidad());
				if (producto.getStock() < 0) throw new InsufficientStockException("La actualización de stock no puede ser menor a 0.");
				this.productoRepository.save(producto);
			}
		});
		
		Gasto gastoExistente = buildGasto(gastoOptional, gastoActualizado);
		
		return this.gastoRepository.save(gastoExistente);
	}
	
	@Transactional
	public Map<String, Boolean> deleteById (Long id){
		Gasto gastoGuardado = this.gastoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El gasto con ese ID no existe: " + id));
		
		gastoGuardado.getProductosComprados().forEach(productoComprado ->{
			Producto producto = this.productoRepository.findById(productoComprado.getProducto().getId()).orElseThrow(
					() -> new ResourceNotFoundException("El producto con ese ID no existe: " + productoComprado.getProducto().getId()));
			Integer stock = producto.getStock();
			producto.setStock(stock - productoComprado.getCantidad());
			if (producto.getStock() < 0) throw new InsufficientStockException("La actualización de stock no puede ser menor a 0.");
			this.productoRepository.save(producto);
		});
		
		this.gastoRepository.delete(gastoGuardado);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return response;
	}

	private Gasto buildGasto(Optional<Gasto> gastoOptional, Gasto gastoActualizado) {
		Gasto.GastoBuilder gastoBuilder = Gasto.builder();
		
		gastoOptional.ifPresent(gasto ->{
			gastoBuilder.id(gasto.getId())
			.fechaDeCompra(gastoActualizado.getFechaDeCompra())
			.costoTotal(gastoActualizado.getCostoTotal())
			.proveedor(gastoActualizado.getProveedor())
			.formaDePago(gastoActualizado.getFormaDePago())
			.productosComprados(gastoActualizado.getProductosComprados());
		});
		
		return gastoBuilder.build();
	}	
}
