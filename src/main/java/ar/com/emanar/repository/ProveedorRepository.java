package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{

}
