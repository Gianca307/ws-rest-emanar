package ar.com.emanar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.emanar.domain.CategoriaProducto;
import ar.com.emanar.service.CategoriaProductoService;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DisplayName("Test Categoria Producto")
public class CategoriaProductoServiceTest {
	private final CategoriaProductoService categoriaProductoService;
	private CategoriaProducto categoriaProductoGuardado;
	
	@BeforeEach
	void setup() {
		CategoriaProducto categoriaProducto = new CategoriaProducto();
		categoriaProducto.setCategoriaProducto("Whisky");
		
		categoriaProductoGuardado = categoriaProductoService.save(categoriaProducto);
	}
	
	@Test
	void testSaveCategoriaProducto () {
		assertNotNull(categoriaProductoGuardado.getId());
		assertEquals("Whisky", categoriaProductoGuardado.getCategoriaProducto());
	}
	
	@Test
	void testFindAllCategoriaProducto () {
		assertFalse(categoriaProductoService.findAll().isEmpty());
	}
	
	@Test
	void testFindById() {
		CategoriaProducto categoriaProducto = this.categoriaProductoService.findById(categoriaProductoGuardado.getId());
		
		assertNotNull(categoriaProducto.getId());
	}
	
	@Test
	void testUpdateCategoriaProducto() {
		Long idCategoriaProducto = 1L;
		
		CategoriaProducto CategoriaProductoActualizada = new CategoriaProducto();
		CategoriaProductoActualizada.setCategoriaProducto("Agua");
		
		CategoriaProducto categoriaProductoDespuesDeActualizar = this.categoriaProductoService.update(CategoriaProductoActualizada, idCategoriaProducto);
		
		assertNotNull(categoriaProductoDespuesDeActualizar.getId());
		assertEquals("Agua", categoriaProductoDespuesDeActualizar.getCategoriaProducto());
	}
	
	@Test
	void testDeleteById() {
		List<CategoriaProducto> categoriasProductos = this.categoriaProductoService.findAll();
 		
		assertFalse(categoriasProductos.isEmpty());
		
		Long idCategoriaProductoAEliminar = categoriasProductos.get(categoriasProductos.size() - 1).getId();
				
		this.categoriaProductoService.deleteById(idCategoriaProductoAEliminar);
		
		Exception exception = assertThrows(RuntimeException.class, ()-> this.categoriaProductoService.findById(idCategoriaProductoAEliminar));
		
		String expectedMessage = "La categoria con ese ID no existe " + idCategoriaProductoAEliminar;
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
}
