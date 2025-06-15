package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.ProductoComprado;

@Repository
public interface ProductoCompradoRepository extends JpaRepository<ProductoComprado, Long>{

}
