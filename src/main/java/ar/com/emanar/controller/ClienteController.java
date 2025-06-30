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

import ar.com.emanar.domain.Cliente;
import ar.com.emanar.service.ClienteService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ClienteController {
	private ClienteService clienteService;
	
	@GetMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente findById(@PathVariable Long id) {
		return this.clienteService.findById(id);
	}	
	
	@GetMapping("/clientes")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> findAll(){
		return this.clienteService.findAll();
	}
	
	@PostMapping("/cliente")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save (@RequestBody Cliente cliente) {
		return this.clienteService.save(cliente);
	}
	
	@PutMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update (@RequestBody Cliente cliente, @PathVariable Long id) {
		return this.clienteService.update(cliente, id);
	}
	
	@DeleteMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById (@PathVariable Long id) {
		this.clienteService.deleteById(id);
	}
	
}
