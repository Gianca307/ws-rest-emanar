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

import ar.com.emanar.domain.Venta;
import ar.com.emanar.service.VentaService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class VentaController {
	private VentaService ventaService;
	
	@GetMapping("/venta/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Venta findById(@PathVariable Long id) {
		return this.ventaService.findById(id);
	}
	
	@GetMapping("/ventas")
	@ResponseStatus(HttpStatus.OK)
	public List<Venta> findAll() {
		return this.ventaService.findAll();
	}
	
	@PostMapping("/venta")
	@ResponseStatus(HttpStatus.CREATED)
	public Venta save(@RequestBody Venta venta,@RequestParam Long idCliente) {
		return this.ventaService.save(venta, idCliente);
	}
	
	@PutMapping("/venta/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Venta update(@RequestBody Venta venta, @RequestParam Long idCliente, @PathVariable Long id) {
		return this.ventaService.update(venta, idCliente, id);
	}
	
	@DeleteMapping("/venta/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.ventaService.deleteById(id);
	}
	
}
