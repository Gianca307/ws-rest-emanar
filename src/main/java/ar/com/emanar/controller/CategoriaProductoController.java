package ar.com.emanar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Categoría de Productos", description = "Operaciones sobre categorías")
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoriaProductoController {
	private CategoriaProductoService categoriaProductoService;
	
	@Operation(summary = "Obtener Categoria de Producto por ID", description = "Devuelve una Categoría")
	@GetMapping("/categoriaProducto/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ResponseStatus(HttpStatus.OK)
	public CategoriaProducto findId (@PathVariable Long id) {		
		return this.categoriaProductoService.findById(id);
	};
	
	@Operation(summary = "Obtener todas las Categoría de Productos", description = "Devuelve una lista de categoría de productos")
	@GetMapping("/categoriasProductos")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ResponseStatus(HttpStatus.OK)
	public List<CategoriaProducto> findAll () {
		return this.categoriaProductoService.findAll();
	};
	
	@Operation(summary = "Guarda en la base de datos una categoría")
	@PostMapping("/categoriaProducto")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaProducto create(@RequestBody CategoriaProducto categoriaProducto) {
		return this.categoriaProductoService.save(categoriaProducto);
	};
	
	@Operation(
			summary = "Modifica una categoría de producto utilizando su ID", 
			description = "Este endpoint modifica una categoría de producto existente en el sistema, identificada por su ID.")
	@PutMapping("/categoriaProducto/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaProducto update(@RequestBody CategoriaProducto categoriaProducto, @PathVariable Long id) {
		return this.categoriaProductoService.update(categoriaProducto, id);
	};
	
	@Operation(
			summary = "Elimina una categoría de producto utilizando su ID", 
			description = "Este endpoint elimina de forma permanente una categoría de producto existente en el sistema, identificada por su ID.")
	@DeleteMapping("/categoriaProducto/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		this.categoriaProductoService.deleteById(id);
	}
	
}
