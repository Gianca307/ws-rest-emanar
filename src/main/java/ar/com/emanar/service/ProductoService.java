package ar.com.emanar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.com.emanar.domain.CategoriaProducto;
import ar.com.emanar.domain.Producto;
import ar.com.emanar.domain.ProductoComprado;
import ar.com.emanar.domain.ProductoVendido;
import ar.com.emanar.exception.ResourceNotFoundException;
import ar.com.emanar.repository.CategoriaProductoRepository;
import ar.com.emanar.repository.ProductoCompradoRepository;
import ar.com.emanar.repository.ProductoRepository;
import ar.com.emanar.repository.ProductoVendidoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductoService {
	private ProductoRepository productoRepository;
	private CategoriaProductoRepository categoriaProductoRepository;
	private ProductoCompradoRepository productoCompradoRepository;
	private ProductoVendidoRepository productoVendidoRepository;
	
	public Producto findById(Long id) {
		return this.productoRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("El proveedor con ese ID no existe: " + id));
	}
	
	public List<Producto> findAll(){
		return this.productoRepository.findAll();
	}
	
	public Producto save (Producto producto, Long idCategoriaProducto) {
		CategoriaProducto categoriaProductoGuardado = this.categoriaProductoRepository.findById(idCategoriaProducto).orElseThrow(
				()-> new ResourceNotFoundException("La categoria con ese ID no existe " + idCategoriaProducto));
		
		producto.setCategoriaProducto(categoriaProductoGuardado);
		
		return this.productoRepository.save(producto);
		
	}
	
	@Transactional
	public Producto update (Producto productoActualizado, Long idCategoriaProducto, Long idProducto) {
		Optional<Producto> optionalProducto = this.productoRepository.findById(idProducto);
		
		CategoriaProducto categoriaProducto = this.categoriaProductoRepository.findById(idCategoriaProducto).orElseThrow(
				()-> new ResourceNotFoundException("La categoria con ese ID no existe " + idCategoriaProducto));
		
		
		productoActualizado.setCategoriaProducto(categoriaProducto);
		
		Producto productoExistente = buildProducto(optionalProducto, productoActualizado);
		
		return this.productoRepository.save(productoExistente);
	}
	
	@Transactional
	public Map<String, Boolean> deleteById(Long Id){
		Producto productoGuardado = findById(Id);
		
		List<ProductoComprado> productosComprados = this.productoCompradoRepository.findByProducto(productoGuardado);
		List<ProductoVendido> productosVendidos = this.productoVendidoRepository.findByProducto(productoGuardado);
		
		productosComprados.forEach(productoComprado -> {
			productoComprado.setProducto(null);
			this.productoCompradoRepository.save(productoComprado);
		});
		
		productosVendidos.forEach(productoVendido -> {
			productoVendido.setProducto(null);
			this.productoVendidoRepository.save(productoVendido);
		});
		
		this.productoRepository.delete(productoGuardado);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return response;
	}

	private Producto buildProducto(Optional<Producto> optionalProducto, Producto productoActualizado) {
		Producto.ProductoBuilder productoBuilder = Producto.builder();
		
		optionalProducto.ifPresent(producto -> {
			productoBuilder.id(producto.getId())
			.marca(productoActualizado.getMarca())
			.variedad(productoActualizado.getVariedad())
			.capacidad(productoActualizado.getCapacidad())
			.costo(productoActualizado.getCosto())
			.precio(productoActualizado.getPrecio())
			.ean(productoActualizado.getEan())
			.stock(productoActualizado.getStock())
			.disponible(productoActualizado.isDisponible())
			.activo(productoActualizado.isActivo())
			.imgUrl(productoActualizado.getImgUrl())
			.categoriaProducto(productoActualizado.getCategoriaProducto());
		});
		
		return productoBuilder.build();
	}
	
}
