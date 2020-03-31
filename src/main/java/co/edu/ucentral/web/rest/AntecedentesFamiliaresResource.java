package co.edu.ucentral.web.rest;

import co.edu.ucentral.domain.AntecedentesFamiliares;
import co.edu.ucentral.repository.AntecedentesFamiliaresRepository;
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
 * REST controller for managing {@link co.edu.ucentral.domain.AntecedentesFamiliares}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AntecedentesFamiliaresResource {

    private final Logger log = LoggerFactory.getLogger(AntecedentesFamiliaresResource.class);

    private static final String ENTITY_NAME = "antecedentesFamiliares";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;

    public AntecedentesFamiliaresResource(AntecedentesFamiliaresRepository antecedentesFamiliaresRepository) {
        this.antecedentesFamiliaresRepository = antecedentesFamiliaresRepository;
    }

    /**
     * {@code POST  /antecedentes-familiares} : Create a new antecedentesFamiliares.
     *
     * @param antecedentesFamiliares the antecedentesFamiliares to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antecedentesFamiliares, or with status {@code 400 (Bad Request)} if the antecedentesFamiliares has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antecedentes-familiares")
    public ResponseEntity<AntecedentesFamiliares> createAntecedentesFamiliares(@RequestBody AntecedentesFamiliares antecedentesFamiliares) throws URISyntaxException {
        log.debug("REST request to save AntecedentesFamiliares : {}", antecedentesFamiliares);
        if (antecedentesFamiliares.getId() != null) {
            throw new BadRequestAlertException("A new antecedentesFamiliares cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntecedentesFamiliares result = antecedentesFamiliaresRepository.save(antecedentesFamiliares);
        return ResponseEntity.created(new URI("/api/antecedentes-familiares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antecedentes-familiares} : Updates an existing antecedentesFamiliares.
     *
     * @param antecedentesFamiliares the antecedentesFamiliares to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antecedentesFamiliares,
     * or with status {@code 400 (Bad Request)} if the antecedentesFamiliares is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antecedentesFamiliares couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antecedentes-familiares")
    public ResponseEntity<AntecedentesFamiliares> updateAntecedentesFamiliares(@RequestBody AntecedentesFamiliares antecedentesFamiliares) throws URISyntaxException {
        log.debug("REST request to update AntecedentesFamiliares : {}", antecedentesFamiliares);
        if (antecedentesFamiliares.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AntecedentesFamiliares result = antecedentesFamiliaresRepository.save(antecedentesFamiliares);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antecedentesFamiliares.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /antecedentes-familiares} : get all the antecedentesFamiliares.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antecedentesFamiliares in body.
     */
    @GetMapping("/antecedentes-familiares")
    public List<AntecedentesFamiliares> getAllAntecedentesFamiliares(@RequestParam(required = false) String filter) {
        if ("historiausuario-is-null".equals(filter)) {
            log.debug("REST request to get all AntecedentesFamiliaress where historiaUsuario is null");
            return StreamSupport
                .stream(antecedentesFamiliaresRepository.findAll().spliterator(), false)
                .filter(antecedentesFamiliares -> antecedentesFamiliares.getHistoriaUsuario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AntecedentesFamiliares");
        return antecedentesFamiliaresRepository.findAll();
    }

    /**
     * {@code GET  /antecedentes-familiares/:id} : get the "id" antecedentesFamiliares.
     *
     * @param id the id of the antecedentesFamiliares to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antecedentesFamiliares, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antecedentes-familiares/{id}")
    public ResponseEntity<AntecedentesFamiliares> getAntecedentesFamiliares(@PathVariable Long id) {
        log.debug("REST request to get AntecedentesFamiliares : {}", id);
        Optional<AntecedentesFamiliares> antecedentesFamiliares = antecedentesFamiliaresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(antecedentesFamiliares);
    }

    /**
     * {@code DELETE  /antecedentes-familiares/:id} : delete the "id" antecedentesFamiliares.
     *
     * @param id the id of the antecedentesFamiliares to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antecedentes-familiares/{id}")
    public ResponseEntity<Void> deleteAntecedentesFamiliares(@PathVariable Long id) {
        log.debug("REST request to delete AntecedentesFamiliares : {}", id);
        antecedentesFamiliaresRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
