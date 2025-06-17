package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Cliente;
import ar.com.emanar.domain.Venta;
import java.util.List;


@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{
	List<Venta> findByCliente(Cliente cliente);
}
