package ar.com.emanar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.emanar.domain.Gasto;
import ar.com.emanar.domain.Proveedor;
import ar.com.emanar.repository.GastoRepository;
import ar.com.emanar.service.ProveedorService;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProveedorServiceTest {
	private final ProveedorService proveedorService;
	private final GastoRepository gastoRepository;
	private Proveedor proveedorGuardado;
	
	@BeforeEach
	void setup () {
		Proveedor proveedor = new Proveedor();
		
		proveedor.setEmpresa("empresa");
		proveedor.setNombreDelContacto("Julian Lezcano");
		proveedor.setNumeroDeContacto("4444466666");
		proveedor.setNumeroDeCliente("157porta");
		proveedor.setRolDelContacto("proventista");
		proveedor.setDiaDeEntrega("Martes");
		proveedor.setDiaDeVisita("Lunes");
		
		proveedorGuardado = this.proveedorService.save(proveedor);
	}
	
	@Test
	void testSaveProveedor() {
		assertNotNull(proveedorGuardado.getId());
		assertEquals("empresa", proveedorGuardado.getEmpresa());
		assertEquals("Julian Lezcano", proveedorGuardado.getNombreDelContacto());
		assertEquals("4444466666", proveedorGuardado.getNumeroDeContacto());
		assertEquals("157porta", proveedorGuardado.getNumeroDeCliente());
		assertEquals("proventista", proveedorGuardado.getRolDelContacto());
		assertEquals("Martes", proveedorGuardado.getDiaDeEntrega());
		assertEquals("Lunes", proveedorGuardado.getDiaDeVisita());
	}
	
	@Test
	void testFindAllProveedor() {
		List<Proveedor> proveedores = this.proveedorService.findAll();
		assertFalse(proveedores.isEmpty());
	}
	
	@Test
	void testUpdateProveedor() {
		Long idProveedor = 1L;
		Proveedor proveedorActualizado = new Proveedor();
		
		proveedorActualizado.setEmpresa("empresa 2");
		proveedorActualizado.setNombreDelContacto("Julian");
		proveedorActualizado.setNumeroDeContacto("3333466666");
		proveedorActualizado.setNumeroDeCliente("19874");
		proveedorActualizado.setRolDelContacto("promotor");
		proveedorActualizado.setDiaDeEntrega("Miercoles");
		proveedorActualizado.setDiaDeVisita("Martes");
		
		Proveedor proveedorDespuesDeActualizar = this.proveedorService.update(proveedorActualizado, idProveedor);
		
		assertNotNull(proveedorDespuesDeActualizar.getId());
		assertEquals("empresa 2", proveedorDespuesDeActualizar.getEmpresa());
		assertEquals("Julian", proveedorDespuesDeActualizar.getNombreDelContacto());
		assertEquals("3333466666", proveedorDespuesDeActualizar.getNumeroDeContacto());
		assertEquals("19874", proveedorDespuesDeActualizar.getNumeroDeCliente());
		assertEquals("promotor", proveedorDespuesDeActualizar.getRolDelContacto());
		assertEquals("Miercoles", proveedorDespuesDeActualizar.getDiaDeEntrega());
		assertEquals("Martes", proveedorDespuesDeActualizar.getDiaDeVisita());		
	}
	
	@Test
	void testDeleteByIdProveedor() {
		List<Proveedor> proveedores = this.proveedorService.findAll();
		Long idProveedorEliminado = proveedores.get(proveedores.size() - 1).getId();
		Proveedor proveedorEliminado = this.proveedorService.findById(idProveedorEliminado);
		
		this.proveedorService.deleteById(idProveedorEliminado);
		List<Gasto> gastos = this.gastoRepository.findByProveedor(proveedorEliminado);
		
		Exception exception = assertThrows(RuntimeException.class, () -> this.proveedorService.findById(idProveedorEliminado));
		
		String expectedMessage = "El proveedor con ese ID no existe: " + idProveedorEliminado;
		String messageActual = exception.getMessage();
		
		assertTrue(gastos.isEmpty());
		assertTrue(messageActual.contains(expectedMessage));
	}
	
}
