package ar.com.emanar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.emanar.domain.Producto;
import ar.com.emanar.domain.ProductoVendido;
import ar.com.emanar.domain.Venta;
import ar.com.emanar.service.VentaService;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VentaServiceTest {
	private final VentaService ventaService;
	private Venta ventaGuardada;
	
	@BeforeEach
	void setup () {
		Venta venta = new Venta();
		List<ProductoVendido> productosVendidos = new ArrayList<>();
		Producto producto = new Producto();
		
		ProductoVendido productoVendido1 = new ProductoVendido();
		productoVendido1.setCantidad(6);
		productoVendido1.setProducto(producto);
		productoVendido1.getProducto().setId(1L);
		productoVendido1.setPrecioEstablecido(1800F);
		
		productosVendidos.add(productoVendido1);
		
		venta.setFechaDeVenta(LocalDate.now());
		venta.setTotal(10800D);
		venta.setProductosVendidos(productosVendidos);
		
		ventaGuardada = this.ventaService.save(venta, 1L);
	}
	
	@Test
	void testSaveVenta() {
		assertNotNull(ventaGuardada.getId());
		assertEquals(LocalDate.now(), ventaGuardada.getFechaDeVenta());
		assertEquals(10800D, ventaGuardada.getTotal());		
		assertFalse(ventaGuardada.getProductosVendidos().isEmpty());
		
		assertNotNull(ventaGuardada.getCliente());
		assertEquals(1L, ventaGuardada.getCliente().getId());
		
		ventaGuardada.getProductosVendidos().forEach(productoVendido -> {
			assertNotNull(productoVendido.getId());
		});
	}
	
	@Test
	void testFindAll() {
		List<Venta> ventas = this.ventaService.findAll();
		assertFalse(ventas.isEmpty());
	}
	
	@Test
	void testUpdateVenta() {
		Long idVentaActualizada = 1L;
		Venta ventaActualizada = new Venta();
		List<ProductoVendido> productosVendidos = new ArrayList<>();
		Producto producto = new Producto();
		
		ProductoVendido productoVendido2 = new ProductoVendido();
		productoVendido2.setCantidad(3);
		productoVendido2.setProducto(producto);
		productoVendido2.getProducto().setId(3L);
		productoVendido2.setPrecioEstablecido(2300F);
		
		productosVendidos.add(productoVendido2);
		
		ventaActualizada.setFechaDeVenta(LocalDate.now());
		ventaActualizada.setTotal(6900D);
		ventaActualizada.setProductosVendidos(productosVendidos);
		
		Venta ventaDespuesDeActualizar = this.ventaService.update(ventaActualizada, 2L, idVentaActualizada);
		
		assertNotNull(ventaDespuesDeActualizar.getId());
		assertEquals(LocalDate.now(), ventaDespuesDeActualizar.getFechaDeVenta());
		assertEquals(6900D, ventaDespuesDeActualizar.getTotal());		
		assertFalse(ventaDespuesDeActualizar.getProductosVendidos().isEmpty());
		
		assertNotNull(ventaDespuesDeActualizar.getCliente());
		assertEquals(2L, ventaDespuesDeActualizar.getCliente().getId());
		
		ventaDespuesDeActualizar.getProductosVendidos().forEach(productoVendido -> {
			assertNotNull(productoVendido.getId());
		});
	}
	
	@Test
	void testDeleteById() {
		List<Venta> ventas = this.ventaService.findAll();
		Long idVentaEliminada = ventas.get(ventas.size() - 1).getId();
		
		this.ventaService.deleteById(idVentaEliminada);
		
		Exception exception = assertThrows(RuntimeException.class, () -> this.ventaService.findById(idVentaEliminada));
		
		String expectedMessaga = "La venta con ese ID no existe: " + idVentaEliminada;
		String messageActual = exception.getMessage();
		
		assertTrue(messageActual.contains(expectedMessaga));		
	}
	
}
