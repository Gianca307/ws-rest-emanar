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

import ar.com.emanar.domain.Proveedor;
import ar.com.emanar.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Proveedores", description = "Operaciones sobre proveedores")
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProveedorController {
	private ProveedorService proveedorService;
	
	@Operation(summary = "Obtener proveedor por ID", description = "Devuelve un proveedor, identificado por su ID.")
	@GetMapping("/proveedor/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public Proveedor findById(@PathVariable Long id) {
		return this.proveedorService.findById(id);
	}
	
	@Operation(summary = "Obtiene todos los proveedores", description = "Devuelve una lista de proveedores.")
	@GetMapping("/proveedores")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ResponseStatus(HttpStatus.OK)
	public List<Proveedor> findAll() {
		return this.proveedorService.findAll();
	}
	
	@Operation(summary = "Guarda un proveedor", description = "Persiste un proveedor en el sistema.")
	@PostMapping("/proveedor")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Proveedor save(@RequestBody Proveedor proveedor) {
		return this.proveedorService.save(proveedor);
	}
	
	@Operation(summary = "Modifica un proveedor por ID", description = "Actualiza proveedor que se encuentra en el sistema, identificado por su ID.")
	@PutMapping("/proveedor/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Proveedor update (@RequestBody Proveedor proveedor, @PathVariable Long id) {
		return this.proveedorService.update(proveedor, id);
	}
	
	@Operation(
			summary = "Elimina proveedor por Id", 
			description = "Elimina del sistema el proveedor, identificado por ID." +
			"Elimina de los gastos la referencia vinculada al id del proveedor.")
	@DeleteMapping("/proveedor/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.proveedorService.deleteById(id);
	}
	
}
