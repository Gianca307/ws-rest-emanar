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
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class GastoController {
	private GastoService gastoService;
	
	@GetMapping("/gasto/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Gasto findById (@PathVariable Long id) {
		return this.gastoService.findById(id);
	}
	
	@GetMapping("/gastos")
	@ResponseStatus(HttpStatus.OK)
	public List<Gasto> findAll(){
		return this.gastoService.findAll();
	}
	
	@PostMapping("/gasto")
	@ResponseStatus(HttpStatus.CREATED)
	public Gasto save (@RequestBody Gasto gasto,@RequestParam Long idProveedor,@RequestParam String formaDePago) {
		return this.gastoService.save(gasto, idProveedor, formaDePago);
	}
	
	@PutMapping("/gasto/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Gasto update (@RequestBody Gasto gasto, @PathVariable Long id, @RequestParam Long idProveedor, @RequestParam String formaDePago) {
		return this.gastoService.update(gasto, id, idProveedor, formaDePago);
	}
	
	@DeleteMapping("/gasto/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		this.gastoService.deleteById(id);
	}
	
}
