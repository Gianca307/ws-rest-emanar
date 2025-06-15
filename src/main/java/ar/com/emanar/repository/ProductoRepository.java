package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
