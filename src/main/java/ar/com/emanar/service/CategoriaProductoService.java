package ar.com.emanar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.emanar.domain.CategoriaProducto;
import ar.com.emanar.domain.Producto;
import ar.com.emanar.exception.ResourceNotFoundException;
import ar.com.emanar.repository.CategoriaProductoRepository;
import ar.com.emanar.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoriaProductoService {
	private CategoriaProductoRepository categoriaProductoRepository;
	private ProductoRepository productoRepository;
	
	public CategoriaProducto findById (Long id) {		
		return this.categoriaProductoRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("La categoria con ese ID no existe " + id));
	}
	
	public List<CategoriaProducto> findAll(){
		return this.categoriaProductoRepository.findAll();
	}
	
	public CategoriaProducto save (CategoriaProducto categoriaProducto) {
		return this.categoriaProductoRepository.save(categoriaProducto);
	}
	
	@Transactional
	public CategoriaProducto update (CategoriaProducto categoriaProductoActualizado, Long id) {
		CategoriaProducto categoriaProductoGuardado = findById(id);
		
		categoriaProductoGuardado.setCategoriaProducto(categoriaProductoActualizado.getCategoriaProducto());
		
		return this.categoriaProductoRepository.save(categoriaProductoGuardado);		
	}
	
	@Transactional
	public void deleteById (Long id){
		
		CategoriaProducto categoriaProductoGuardado = findById(id);
		
		List<Producto> productos = this.productoRepository.findByCategoriaProducto(categoriaProductoGuardado);
		
		productos.forEach(producto -> {
			producto.setCategoriaProducto(null);
			this.productoRepository.save(producto);
		});
		
		this.categoriaProductoRepository.delete(categoriaProductoGuardado);
	}
	
}
