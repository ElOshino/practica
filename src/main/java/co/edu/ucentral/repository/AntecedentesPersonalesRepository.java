package co.edu.ucentral.repository;

import co.edu.ucentral.domain.AntecedentesPersonales;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AntecedentesPersonales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntecedentesPersonalesRepository extends JpaRepository<AntecedentesPersonales, Long> {
}
