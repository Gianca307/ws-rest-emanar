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

import ar.com.emanar.domain.Gasto;
import ar.com.emanar.service.GastoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Gastos", description = "Operaciones sobre gastos.")
@AllArgsConstructor
@RequestMapping("/api/v1")
public class GastoController {
	private GastoService gastoService;
	
	@Operation(summary = "Obtiene un gasto por ID", description = "Devuelve un gasto, identificado por su ID.")
	@GetMapping("/gasto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Gasto findById (@PathVariable Long id) {
		return this.gastoService.findById(id);
	}
	
	@Operation(summary = "Obtiene todos los gastos", description = "Devuelve una lista de gastos.")
	@GetMapping("/gastos")
	@ResponseStatus(HttpStatus.OK)
	public List<Gasto> findAll(){
		return this.gastoService.findAll();
	}
	
	@Operation(
			summary = "Guarda un gasto", 
			description = "Persiste un gasto en el sistema. " + 
			"Persiste los productos comprados. " + 
					"Actualiza el stock de los productos relacionados al gasto.")
	@PostMapping("/gasto")
	@ResponseStatus(HttpStatus.CREATED)
	public Gasto save (@RequestBody Gasto gasto,@RequestParam Long idProveedor,@RequestParam String formaDePago) {
		return this.gastoService.save(gasto, idProveedor, formaDePago);
	}
	
	@Operation(
			summary = "Modifica gasto por ID", 
			description = "Actualiza gasto, identificado por su ID. " + 
			"Agrega, modifica o elimina los productos comprados. " +
					"Actualiza el stock de los productos relacionados al gasto.")
	@PutMapping("/gasto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Gasto update (@RequestBody Gasto gasto, @PathVariable Long id, @RequestParam Long idProveedor, @RequestParam String formaDePago) {
		return this.gastoService.update(gasto, id, idProveedor, formaDePago);
	}
	
	@Operation(
			summary = "Elimina gasto por ID", 
			description = "Elimina el gasto del sistema, identificado por su ID. " +
			"Actualiza el stock de los productos relacionados al gasto.")
	@DeleteMapping("/gasto/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.gastoService.deleteById(id);
	}
	
}
