package ar.com.emanar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.emanar.domain.Cliente;
import ar.com.emanar.domain.Venta;
import ar.com.emanar.repository.VentaRepository;
import ar.com.emanar.service.ClienteService;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteServiceTest extends BaseTest{
	private final ClienteService clienteService;
	private final VentaRepository ventaRepository;
	private Cliente clienteGuardado;
	
	@BeforeEach
	void setup () {
		Cliente cliente = new Cliente ();
		
		cliente.setNombre("Agustin Gomez");
		cliente.setDireccion("Calle false 123");
		cliente.setNumeroDeCelular("3456654987");
		
		clienteGuardado = this.clienteService.save(cliente);		
	}
	
	@Test
	void testSaveCliente () {
		assertNotNull(clienteGuardado.getId());
		assertEquals("Agustin Gomez", clienteGuardado.getNombre());
		assertEquals("Calle false 123", clienteGuardado.getDireccion());
		assertEquals("3456654987", clienteGuardado.getNumeroDeCelular());
	}
	
	@Test
	void testUpdateCliente() {
		Long idCliente = 1L;
		
		Cliente clienteActualizado = new Cliente();
		
		clienteActualizado.setNombre("Gomez Agustin");
		clienteActualizado.setDireccion("Calle false 957");
		clienteActualizado.setNumeroDeCelular("7777711111");
		
		Cliente clienteDespuesDeActualizar = this.clienteService.update(clienteActualizado, idCliente);
		
		assertNotNull(clienteDespuesDeActualizar.getId());
		assertEquals("Gomez Agustin", clienteDespuesDeActualizar.getNombre());
		assertEquals("Calle false 957", clienteDespuesDeActualizar.getDireccion());
		assertEquals("7777711111", clienteDespuesDeActualizar.getNumeroDeCelular());
	}
	
	@Test
	void testDeleteById() {
		List<Cliente> clientes = this.clienteService.findAll();
		Long idClienteAEliminar = clientes.get(clientes.size() - 1).getId();
		Cliente clienteEliminado = this.clienteService.findById(idClienteAEliminar);
		
		this.clienteService.deleteById(idClienteAEliminar);
		
		List<Venta> ventas = this.ventaRepository.findByCliente(clienteEliminado);
		
		Exception exception = assertThrows(RuntimeException.class, () -> this.clienteService.findById(idClienteAEliminar));
		
		String expectedMessage = "El cliente con ese ID no existe: " + idClienteAEliminar;
		String messageActual = exception.getMessage();
		
		assertTrue(messageActual.contains(expectedMessage));
		assertTrue(ventas.isEmpty());
	}
	
}
