package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
