package ar.com.emanar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.com.emanar.domain.Gasto;
import ar.com.emanar.domain.Proveedor;
import ar.com.emanar.exception.ResourceNotFoundException;
import ar.com.emanar.repository.GastoRepository;
import ar.com.emanar.repository.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProveedorService {
	private ProveedorRepository proveedorRepository;
	private GastoRepository gastoRepository;
	
	public Proveedor findById (Long id) {
		return this.proveedorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El proveedor con ese ID no existe: " +id));
	}
	
	public List<Proveedor> findAll(){
		return this.proveedorRepository.findAll();
	}
	
	public Proveedor save(Proveedor proveedor) {
		return this.proveedorRepository.save(proveedor);
	};
	
	@Transactional
	public Proveedor update (Proveedor proveedorActualizado, Long id) {
		Optional<Proveedor> proveedorOptional = this.proveedorRepository.findById(id);
		
		Proveedor proveedorExistente = buildProveedor(proveedorActualizado, proveedorOptional);
		
		return this.proveedorRepository.save(proveedorExistente);		
	}
	
	@Transactional
	public void deleteById(Long id){
		
		Proveedor proveedorGuardado = findById(id);
		
		List<Gasto> gastos = this.gastoRepository.findByProveedor(proveedorGuardado);
		
		gastos.forEach(gasto -> {
			gasto.setProveedor(null);
			this.gastoRepository.save(gasto);
		});
		
		proveedorRepository.delete(proveedorGuardado);
	}

	private Proveedor buildProveedor(Proveedor proveedorActualizado, Optional<Proveedor> proveedorOptional) {
		Proveedor.ProveedorBuilder proveedorBuilder = Proveedor.builder();
		
		proveedorOptional.ifPresent(proveedor ->{
			proveedorBuilder.id(proveedor.getId())
			.empresa(proveedorActualizado.getEmpresa())
			.nombreDelContacto(proveedorActualizado.getNombreDelContacto())
			.numeroDeContacto(proveedorActualizado.getNumeroDeContacto())
			.numeroDeCliente(proveedorActualizado.getNumeroDeCliente())
			.rolDelContacto(proveedorActualizado.getRolDelContacto())
			.diaDeEntrega(proveedorActualizado.getDiaDeEntrega())
			.diaDeVisita(proveedorActualizado.getDiaDeVisita());
		});		
		
		return proveedorBuilder.build();
	}
}
