package co.edu.ucentral.repository;

import co.edu.ucentral.domain.SignosVitales;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SignosVitales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignosVitalesRepository extends JpaRepository<SignosVitales, Long> {
}
