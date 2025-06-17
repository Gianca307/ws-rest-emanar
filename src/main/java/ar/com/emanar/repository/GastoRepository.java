package ar.com.emanar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Gasto;
import ar.com.emanar.domain.Proveedor;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long>{
	List<Gasto> findByProveedor(Proveedor proveedor);
}
