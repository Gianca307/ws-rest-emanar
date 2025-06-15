package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.ProductoVendido;

@Repository
public interface ProductoVendidoRepository extends JpaRepository<ProductoVendido, Long>{

}
