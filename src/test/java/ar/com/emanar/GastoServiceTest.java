package ar.com.emanar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.emanar.domain.FormaDePago;
import ar.com.emanar.domain.Gasto;
import ar.com.emanar.domain.Producto;
import ar.com.emanar.domain.ProductoComprado;
import ar.com.emanar.service.GastoService;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GastoServiceTest {
	private final GastoService gastoService;
	private Gasto gastoGuardado;
	
	@BeforeEach
	void setup () {
		Gasto gasto = new Gasto();
		ProductoComprado productoComprado = new ProductoComprado();
		Producto producto = new Producto();
		List<ProductoComprado> productosComprados = new ArrayList<>();
		
		productoComprado.setCantidad(6);
		productoComprado.setPrecioEstablecido(1235.1F);
		productoComprado.setProducto(producto);
		productoComprado.getProducto().setId(1L);
		productosComprados.add(productoComprado);
		
		gasto.setFechaDeCompra(LocalDate.now());
		gasto.setCostoTotal(7410.6);
		gasto.setProductosComprados(productosComprados);
		gasto.setFormaDePago(FormaDePago.EFECTIVO);
		
		gastoGuardado = this.gastoService.save(gasto, 1L, "EFECTIVO");
	}	
	
	@Test
	void testSaveGasto() {
		assertNotNull(gastoGuardado.getId());
		assertEquals(LocalDate.now(), gastoGuardado.getFechaDeCompra());
		assertEquals(7410.6, gastoGuardado.getCostoTotal());
		assertEquals(FormaDePago.EFECTIVO, gastoGuardado.getFormaDePago());
		
		gastoGuardado.getProductosComprados().forEach(productoComprado ->{
			assertNotNull(productoComprado.getId());
		});
	}
	
	@Test
	void testFindAllGasto () {
		List<Gasto> gastos = this.gastoService.findAll();		
		assertFalse(gastos.isEmpty());
	}
	
	@Test
	void testUpdateGasto () {
		Gasto gastoActualizado = new Gasto();
		ProductoComprado productoComprado = new ProductoComprado();
		Producto producto = new Producto();
		List<ProductoComprado> productosComprados = new ArrayList<>();
		
		productoComprado.setCantidad(2);
		productoComprado.setPrecioEstablecido(7075.21F);
		productoComprado.setProducto(producto);
		productoComprado.getProducto().setId(6L);
		productosComprados.add(productoComprado);
		
		gastoActualizado.setFechaDeCompra(LocalDate.now());
		gastoActualizado.setCostoTotal(14150.02);
		gastoActualizado.setProductosComprados(productosComprados);
		gastoActualizado.setFormaDePago(FormaDePago.TARJETA_CREDITO);
		
		Gasto gastoDespuesDeActualizar = this.gastoService.update(gastoActualizado, 1L, 2L, "TARJETA_CREDITO");
		
		assertNotNull(gastoDespuesDeActualizar.getId());
		assertEquals(LocalDate.now(), gastoDespuesDeActualizar.getFechaDeCompra());
		assertEquals(14150.02, gastoDespuesDeActualizar.getCostoTotal());
		assertEquals(FormaDePago.TARJETA_CREDITO, gastoDespuesDeActualizar.getFormaDePago());
		
		gastoDespuesDeActualizar.getProductosComprados().forEach(productoCompradoA ->{
			assertNotNull(productoCompradoA.getId());
		});
	}
	
	@Test
	void testDeleteById() {
		List<Gasto> gastos = this.gastoService.findAll();
		Long idGastoEliminado = gastos.get(gastos.size() - 1).getId();
		
		this.gastoService.deleteById(idGastoEliminado);
		
		Exception exception = assertThrows(RuntimeException.class, () -> this.gastoService.findById(idGastoEliminado));
		
		String expectedMessage = "El gasto con ese ID no existe: " + idGastoEliminado;
		String messageActual = exception.getMessage();
		
		assertTrue(messageActual.contains(expectedMessage));
	}
	
}
