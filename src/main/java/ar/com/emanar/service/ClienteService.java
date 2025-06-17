package ar.com.emanar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.com.emanar.domain.Cliente;
import ar.com.emanar.domain.Venta;
import ar.com.emanar.exception.ResourceNotFoundException;
import ar.com.emanar.repository.ClienteRepository;
import ar.com.emanar.repository.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClienteService {
	private ClienteRepository clienteRepository;
	private VentaRepository ventaRepository;
	
	public Cliente findById(Long id) {
		return this.clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El cliente con ese ID no existe: " + id)); 
	} 
	
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public Cliente save (Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}
	
	@Transactional
	public Cliente update (Cliente clienteActualizado, Long id) {
		
		Optional<Cliente> clienteOptional = this.clienteRepository.findById(id);
		
		Cliente clienteExistente = buildCliente(clienteActualizado, clienteOptional);
		
		return this.clienteRepository.save(clienteExistente);
	}
	
	@Transactional
	public Map<String, Boolean> deleteById (Long id){
		
		Cliente clienteGuardado = findById(id);
		
		List<Venta> ventas = this.ventaRepository.findByCliente(clienteGuardado);
		
		ventas.forEach(venta ->{
			venta.setCliente(null);
			this.ventaRepository.save(venta);
		});
		
		this.clienteRepository.delete(clienteGuardado);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}

	private Cliente buildCliente(Cliente clienteActualizado, Optional<Cliente> clienteOptional) {
		Cliente.ClienteBuilder clienteBuilder = Cliente.builder();
		
		clienteOptional.ifPresent(cliente ->{
			clienteBuilder.id(cliente.getId())
			.nombre(clienteActualizado.getNombre())
			.direccion(clienteActualizado.getDireccion())
			.numeroDeCelular(cliente.getNumeroDeCelular());
		});
		
		return clienteBuilder.build();
	}
	
}
