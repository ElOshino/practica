package co.edu.ucentral.repository;

import co.edu.ucentral.domain.Medicamentos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Medicamentos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicamentosRepository extends JpaRepository<Medicamentos, Long> {
}
