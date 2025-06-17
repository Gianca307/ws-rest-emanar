package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Producto;
import java.util.List;
import ar.com.emanar.domain.CategoriaProducto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	List<Producto> findByCategoriaProducto(CategoriaProducto categoriaProducto);
}
