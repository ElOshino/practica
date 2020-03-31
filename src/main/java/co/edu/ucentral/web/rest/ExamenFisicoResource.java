package co.edu.ucentral.web.rest;

import co.edu.ucentral.domain.ExamenFisico;
import co.edu.ucentral.repository.ExamenFisicoRepository;
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
 * REST controller for managing {@link co.edu.ucentral.domain.ExamenFisico}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExamenFisicoResource {

    private final Logger log = LoggerFactory.getLogger(ExamenFisicoResource.class);

    private static final String ENTITY_NAME = "examenFisico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamenFisicoRepository examenFisicoRepository;

    public ExamenFisicoResource(ExamenFisicoRepository examenFisicoRepository) {
        this.examenFisicoRepository = examenFisicoRepository;
    }

    /**
     * {@code POST  /examen-fisicos} : Create a new examenFisico.
     *
     * @param examenFisico the examenFisico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examenFisico, or with status {@code 400 (Bad Request)} if the examenFisico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examen-fisicos")
    public ResponseEntity<ExamenFisico> createExamenFisico(@RequestBody ExamenFisico examenFisico) throws URISyntaxException {
        log.debug("REST request to save ExamenFisico : {}", examenFisico);
        if (examenFisico.getId() != null) {
            throw new BadRequestAlertException("A new examenFisico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamenFisico result = examenFisicoRepository.save(examenFisico);
        return ResponseEntity.created(new URI("/api/examen-fisicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examen-fisicos} : Updates an existing examenFisico.
     *
     * @param examenFisico the examenFisico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examenFisico,
     * or with status {@code 400 (Bad Request)} if the examenFisico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examenFisico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examen-fisicos")
    public ResponseEntity<ExamenFisico> updateExamenFisico(@RequestBody ExamenFisico examenFisico) throws URISyntaxException {
        log.debug("REST request to update ExamenFisico : {}", examenFisico);
        if (examenFisico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamenFisico result = examenFisicoRepository.save(examenFisico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examenFisico.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /examen-fisicos} : get all the examenFisicos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examenFisicos in body.
     */
    @GetMapping("/examen-fisicos")
    public List<ExamenFisico> getAllExamenFisicos(@RequestParam(required = false) String filter) {
        if ("historiausuario-is-null".equals(filter)) {
            log.debug("REST request to get all ExamenFisicos where historiaUsuario is null");
            return StreamSupport
                .stream(examenFisicoRepository.findAll().spliterator(), false)
                .filter(examenFisico -> examenFisico.getHistoriaUsuario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all ExamenFisicos");
        return examenFisicoRepository.findAll();
    }

    /**
     * {@code GET  /examen-fisicos/:id} : get the "id" examenFisico.
     *
     * @param id the id of the examenFisico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examenFisico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examen-fisicos/{id}")
    public ResponseEntity<ExamenFisico> getExamenFisico(@PathVariable Long id) {
        log.debug("REST request to get ExamenFisico : {}", id);
        Optional<ExamenFisico> examenFisico = examenFisicoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(examenFisico);
    }

    /**
     * {@code DELETE  /examen-fisicos/:id} : delete the "id" examenFisico.
     *
     * @param id the id of the examenFisico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examen-fisicos/{id}")
    public ResponseEntity<Void> deleteExamenFisico(@PathVariable Long id) {
        log.debug("REST request to delete ExamenFisico : {}", id);
        examenFisicoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
