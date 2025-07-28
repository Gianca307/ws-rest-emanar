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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.emanar.domain.Venta;
import ar.com.emanar.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Ventas", description = "Operaciones sobre ventas.")
@AllArgsConstructor
@RequestMapping("/api/v1")
public class VentaController {
	private VentaService ventaService;
	
	@Operation(summary = "Obtiene una venta por ID", description = "Devuelve una venta, identificada por su ID.")
	@GetMapping("/venta/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Venta findById(@PathVariable Long id) {
		return this.ventaService.findById(id);
	}
	
	@Operation(summary = "Obtiene todas las ventas", description = "Devuelve una lista de ventas, con los productos vendidos.")
	@GetMapping("/ventas")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public List<Venta> findAll() {
		return this.ventaService.findAll();
	}
	
	@Operation(
			summary = "Guarda una venta", 
			description = "Persiste una venta, con sus productos vendidos." + 
			"Actualiza el stock de los productos relacionados con la venta.")
	@PostMapping("/venta")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Venta save(@RequestBody Venta venta,@RequestParam Long idCliente) {
		return this.ventaService.save(venta, idCliente);
	}
	
	@Operation(
			summary = "Modifica una venta por ID", 
			description = "Actualiza una venta, identificada por su id. " +
			"Agrega, modifica o elimina los productos vendidos. " +
					"Actualiza el stock según la operación.")
	@PutMapping("/venta/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Venta update(@RequestBody Venta venta, @RequestParam Long idCliente, @PathVariable Long id) {
		return this.ventaService.update(venta, idCliente, id);
	}
	
	@Operation(
			summary = "Elimina una venta por ID",
			description = "Elimina una venta, identificada por su id. " +
			"Elimina los productos vendidos vinculados a la venta." +
					"Actualiza el stock.")
	@DeleteMapping("/venta/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.ventaService.deleteById(id);
	}
	
}
