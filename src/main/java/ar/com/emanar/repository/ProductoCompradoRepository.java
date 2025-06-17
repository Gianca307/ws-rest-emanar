package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Producto;
import ar.com.emanar.domain.ProductoComprado;
import java.util.List;


@Repository
public interface ProductoCompradoRepository extends JpaRepository<ProductoComprado, Long>{
	List<ProductoComprado> findByProducto(Producto producto);
}
