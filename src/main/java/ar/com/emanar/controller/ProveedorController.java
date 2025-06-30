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

import ar.com.emanar.domain.Proveedor;
import ar.com.emanar.service.ProveedorService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProveedorController {
	private ProveedorService proveedorService;
	
	@GetMapping("/proveedor/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Proveedor findById(@PathVariable Long id) {
		return this.proveedorService.findById(id);
	}
	
	@GetMapping("/proveedores")
	@ResponseStatus(HttpStatus.OK)
	public List<Proveedor> findAll() {
		return this.proveedorService.findAll();
	}
	
	@PostMapping("/proveedor")
	@ResponseStatus(HttpStatus.CREATED)
	public Proveedor save(@RequestBody Proveedor proveedor) {
		return this.proveedorService.save(proveedor);
	}
	
	@PutMapping("/proveedor/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Proveedor update (@RequestBody Proveedor proveedor, @PathVariable Long id) {
		return this.proveedorService.update(proveedor, id);
	}
	
	@DeleteMapping("/proveedor/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.proveedorService.deleteById(id);
	}
	
}
