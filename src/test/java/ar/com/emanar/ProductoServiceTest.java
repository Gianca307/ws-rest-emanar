package ar.com.emanar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.emanar.domain.Producto;
import ar.com.emanar.service.ProductoService;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductoServiceTest extends BaseTest{
	private final ProductoService productoService;
	private Producto productoGuardado;
	
	@BeforeEach
	void setup() {
		Producto producto = new Producto();
		
		producto.setMarca("Andes Origen");
		producto.setVariedad("Negra");
		producto.setCapacidad("473 cm3");
		producto.setCosto(1345.12f);
		producto.setPrecio(2100f);
		producto.setEan(5555555555555L);
		producto.setStock(12);
		producto.setDisponible(true);
		producto.setActivo(true);
		producto.setImgUrl("imagen producto");
		
		productoGuardado = this.productoService.save(producto, 1L);
	}
	
	@Test
	void testSaveProducto() {
		assertNotNull(productoGuardado.getId());
		assertEquals("Andes Origen", productoGuardado.getMarca());
		assertEquals("Negra", productoGuardado.getVariedad());
		assertEquals("473 cm3", productoGuardado.getCapacidad());
		assertEquals(1345.12f, productoGuardado.getCosto());
		assertEquals(2100f, productoGuardado.getPrecio());
		assertEquals(5555555555555L, productoGuardado.getEan());
		assertEquals(12, productoGuardado.getStock());
		assertTrue(productoGuardado.isDisponible());
		assertTrue(productoGuardado.isActivo());
		assertEquals("imagen producto", productoGuardado.getImgUrl());
		
		assertNotNull(productoGuardado.getCategoriaProducto());		
	}
	
	@Test
	void testFindAll() {
		assertFalse(this.productoService.findAll().isEmpty());
	}
	
	@Test
	void testUpdateProducto() {
		Long idProducto = 1L;
		
		Producto productoActualizado = new Producto();
		
		productoActualizado.setMarca("Heineken");
		productoActualizado.setVariedad("Rubia");
		productoActualizado.setCapacidad("473 cm3");
		productoActualizado.setCosto(2141.12f);
		productoActualizado.setPrecio(2900f);
		productoActualizado.setEan(6666666666666L);
		productoActualizado.setStock(24);
		productoActualizado.setDisponible(true);
		productoActualizado.setActivo(false);
		productoActualizado.setImgUrl("imagen producto heineken");
		
		Producto productoDespuesDeActualizar = this.productoService.update(productoActualizado, 2L, idProducto);
		
		assertNotNull(productoDespuesDeActualizar.getId());
		assertEquals("Heineken", productoDespuesDeActualizar.getMarca());
		assertEquals("Rubia", productoDespuesDeActualizar.getVariedad());
		assertEquals("473 cm3", productoDespuesDeActualizar.getCapacidad());
		assertEquals(2141.12f, productoDespuesDeActualizar.getCosto());
		assertEquals(2900f, productoDespuesDeActualizar.getPrecio());
		assertEquals(6666666666666L, productoDespuesDeActualizar.getEan());
		assertEquals(24, productoDespuesDeActualizar.getStock());
		assertTrue(productoDespuesDeActualizar.isDisponible());
		assertFalse(productoDespuesDeActualizar.isActivo());
		assertEquals("imagen producto heineken", productoDespuesDeActualizar.getImgUrl());
		
		assertNotNull(productoDespuesDeActualizar.getCategoriaProducto());
	}
	
	@Test
	void testDeleteById() {
		List<Producto> productos = this.productoService.findAll();
		assertFalse(productos.isEmpty());
		
		Long idProductoAEliminar = productos.get(productos.size()-1).getId();
		
		this.productoService.deleteById(idProductoAEliminar);
		
		Exception exception = assertThrows(RuntimeException.class, () -> this.productoService.findById(idProductoAEliminar));
		
		String messageActual = exception.getMessage();
		String expectedMessage = "El producto con ese ID no existe: " + idProductoAEliminar;
		
		assertTrue(messageActual.contains(expectedMessage));
	}
	
}
