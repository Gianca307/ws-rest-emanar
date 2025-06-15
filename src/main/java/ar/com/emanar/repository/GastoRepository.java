package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Gasto;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long>{

}
