package co.edu.ucentral.repository;

import co.edu.ucentral.domain.ExamenFisico;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExamenFisico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamenFisicoRepository extends JpaRepository<ExamenFisico, Long> {
}
