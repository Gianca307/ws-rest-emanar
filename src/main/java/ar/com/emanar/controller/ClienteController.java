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

import ar.com.emanar.domain.Cliente;
import ar.com.emanar.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Clientes", description = "Operaciones sobre clientes")
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ClienteController {
	private ClienteService clienteService;
	
	@Operation(summary = "Obtener Cliente por ID", description = "Devuelve una Cliente, identificada por su id.")
	@GetMapping("/cliente/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ResponseStatus(HttpStatus.OK)
	public Cliente findById(@PathVariable Long id) {
		return this.clienteService.findById(id);
	}	
	
	@Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista de Clientes.")
	@GetMapping("/clientes")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> findAll(){
		return this.clienteService.findAll();
	}
	
	@Operation(summary = "Guarda en el sistema un cliente")
	@PostMapping("/cliente")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save (@RequestBody Cliente cliente) {
		return this.clienteService.save(cliente);
	}
	
	@Operation(
			summary = "Modifica un cliente por su id", 
			description = "Modifica un cliente existente en el sistema, identificada por su ID.")
	@PutMapping("/cliente/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update (@RequestBody Cliente cliente, @PathVariable Long id) {
		return this.clienteService.update(cliente, id);
	}
	
	@Operation(
			summary = "Elimina un cliente por su id", 
			description = "Elimina un cliente existente en el sistema, identificada por su ID. " + 
			"También elimina de las ventas la referencia vinculadas al id del cliente.")
	@DeleteMapping("/cliente/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById (@PathVariable Long id) {
		this.clienteService.deleteById(id);
	}
	
}
