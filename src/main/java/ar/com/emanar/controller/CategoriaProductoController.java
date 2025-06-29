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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.emanar.domain.CategoriaProducto;
import ar.com.emanar.service.CategoriaProductoService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoriaProductoController {
	private CategoriaProductoService categoriaProductoService;
	
	@GetMapping("/categoriaProducto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CategoriaProducto findId (@PathVariable Long id) {		
		return this.categoriaProductoService.findById(id);
	};
	
	@GetMapping("/categoriasProductos")
	@ResponseStatus(HttpStatus.OK)
	public List<CategoriaProducto> findAll () {
		return this.categoriaProductoService.findAll();
	};
	
	@PostMapping("/categoriaProducto")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaProducto create(@RequestBody CategoriaProducto categoriaProducto) {
		return this.categoriaProductoService.save(categoriaProducto);
	};
	
	@PutMapping("/categoriaProducto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaProducto update(@RequestBody CategoriaProducto categoriaProducto, @PathVariable Long id) {
		return this.categoriaProductoService.update(categoriaProducto, id);
	};

	@DeleteMapping("/categoriaProducto/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		this.categoriaProductoService.deleteById(id);
	}
	
}
