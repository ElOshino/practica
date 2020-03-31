package co.edu.ucentral.web.rest;

import co.edu.ucentral.domain.Medicamentos;
import co.edu.ucentral.repository.MedicamentosRepository;
import co.edu.ucentral.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link co.edu.ucentral.domain.Medicamentos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MedicamentosResource {

    private final Logger log = LoggerFactory.getLogger(MedicamentosResource.class);

    private static final String ENTITY_NAME = "medicamentos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicamentosRepository medicamentosRepository;

    public MedicamentosResource(MedicamentosRepository medicamentosRepository) {
        this.medicamentosRepository = medicamentosRepository;
    }

    /**
     * {@code POST  /medicamentos} : Create a new medicamentos.
     *
     * @param medicamentos the medicamentos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicamentos, or with status {@code 400 (Bad Request)} if the medicamentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicamentos")
    public ResponseEntity<Medicamentos> createMedicamentos(@RequestBody Medicamentos medicamentos) throws URISyntaxException {
        log.debug("REST request to save Medicamentos : {}", medicamentos);
        if (medicamentos.getId() != null) {
            throw new BadRequestAlertException("A new medicamentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medicamentos result = medicamentosRepository.save(medicamentos);
        return ResponseEntity.created(new URI("/api/medicamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicamentos} : Updates an existing medicamentos.
     *
     * @param medicamentos the medicamentos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicamentos,
     * or with status {@code 400 (Bad Request)} if the medicamentos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicamentos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicamentos")
    public ResponseEntity<Medicamentos> updateMedicamentos(@RequestBody Medicamentos medicamentos) throws URISyntaxException {
        log.debug("REST request to update Medicamentos : {}", medicamentos);
        if (medicamentos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Medicamentos result = medicamentosRepository.save(medicamentos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicamentos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicamentos} : get all the medicamentos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicamentos in body.
     */
    @GetMapping("/medicamentos")
    public List<Medicamentos> getAllMedicamentos(@RequestParam(required = false) String filter) {
        if ("historiausuario-is-null".equals(filter)) {
            log.debug("REST request to get all Medicamentoss where historiaUsuario is null");
            return StreamSupport
                .stream(medicamentosRepository.findAll().spliterator(), false)
                .filter(medicamentos -> medicamentos.getHistoriaUsuario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Medicamentos");
        return medicamentosRepository.findAll();
    }

    /**
     * {@code GET  /medicamentos/:id} : get the "id" medicamentos.
     *
     * @param id the id of the medicamentos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicamentos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicamentos/{id}")
    public ResponseEntity<Medicamentos> getMedicamentos(@PathVariable Long id) {
        log.debug("REST request to get Medicamentos : {}", id);
        Optional<Medicamentos> medicamentos = medicamentosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medicamentos);
    }

    /**
     * {@code DELETE  /medicamentos/:id} : delete the "id" medicamentos.
     *
     * @param id the id of the medicamentos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicamentos/{id}")
    public ResponseEntity<Void> deleteMedicamentos(@PathVariable Long id) {
        log.debug("REST request to delete Medicamentos : {}", id);
        medicamentosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
