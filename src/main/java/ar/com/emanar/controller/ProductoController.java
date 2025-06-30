package ar.com.emanar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.emanar.domain.Producto;
import ar.com.emanar.service.ProductoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductoController {
	private ProductoService productoService;
	
	@GetMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Producto findById (@PathVariable Long id) {
		return this.productoService.findById(id);
	}
	
	@GetMapping("/productos")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> findAll (){
		return this.productoService.findAll();
	} 
	
	@PostMapping("/producto")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto save(@RequestBody Producto producto, @RequestParam Long idCategoriaProducto) {
		return this.productoService.save(producto, idCategoriaProducto);
	}
	
	@PutMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto update (@RequestBody Producto producto, @RequestParam Long idCategoriaProducto, @PathVariable Long id) {
		return this.productoService.update(producto, idCategoriaProducto, id);
	}
	
	@DeleteMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.productoService.deleteById(id);
	}
	
}
