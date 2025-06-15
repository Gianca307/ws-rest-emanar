package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.CategoriaProducto;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long>{
	
}
