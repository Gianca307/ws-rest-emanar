package ar.com.emanar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.emanar.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
