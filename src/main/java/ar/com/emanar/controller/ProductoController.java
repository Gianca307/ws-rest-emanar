package ar.com.emanar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Productos", description = "Operaciones sobre productos")
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ProductoController {
	private ProductoService productoService;
	
	@Operation(summary = "Obtiene producto por ID", description = "Devuelve un producto, identificado por su ID.")
	@GetMapping("/producto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Producto findById (@PathVariable Long id) {
		return this.productoService.findById(id);
	}
	
	@Operation(summary = "Obtiene todos los productos", description = "Devuelve una lista de productos.")
	@GetMapping("/productos")
	@CrossOrigin(origins = {"http://localhost:5173" , "http://127.0.0.1:5173", "https://silly-custard-9f91d4.netlify.app"})
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> findAll (){
		return this.productoService.findAll();
	} 
	
	@Operation(summary = "Guarda un producto", description = "Persiste un producto en el sistema.")
	@PostMapping("/producto")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto save(@RequestBody Producto producto, @RequestParam Long idCategoriaProducto) {
		return this.productoService.save(producto, idCategoriaProducto);
	}
	
	@Operation(summary = "Modifica un producto por su ID", description = "Actualiza un producto en el sistema.")
	@PutMapping("/producto/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto update (@RequestBody Producto producto, @RequestParam Long idCategoriaProducto, @PathVariable Long id) {
		return this.productoService.update(producto, idCategoriaProducto, id);
	}
	
	@Operation(
			summary = "Elimina un producto por su ID", 
			description = "Elimina un producto, identificado por su ID. " + 
			"Elimina de los productos comprados y vendidos la referencia vinculada al id del producto.")
	@DeleteMapping("/producto/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.productoService.deleteById(id);
	}
	
}
