package co.edu.ucentral.web.rest;

import co.edu.ucentral.domain.AntecedentesPersonales;
import co.edu.ucentral.repository.AntecedentesPersonalesRepository;
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
 * REST controller for managing {@link co.edu.ucentral.domain.AntecedentesPersonales}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AntecedentesPersonalesResource {

    private final Logger log = LoggerFactory.getLogger(AntecedentesPersonalesResource.class);

    private static final String ENTITY_NAME = "antecedentesPersonales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntecedentesPersonalesRepository antecedentesPersonalesRepository;

    public AntecedentesPersonalesResource(AntecedentesPersonalesRepository antecedentesPersonalesRepository) {
        this.antecedentesPersonalesRepository = antecedentesPersonalesRepository;
    }

    /**
     * {@code POST  /antecedentes-personales} : Create a new antecedentesPersonales.
     *
     * @param antecedentesPersonales the antecedentesPersonales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antecedentesPersonales, or with status {@code 400 (Bad Request)} if the antecedentesPersonales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antecedentes-personales")
    public ResponseEntity<AntecedentesPersonales> createAntecedentesPersonales(@RequestBody AntecedentesPersonales antecedentesPersonales) throws URISyntaxException {
        log.debug("REST request to save AntecedentesPersonales : {}", antecedentesPersonales);
        if (antecedentesPersonales.getId() != null) {
            throw new BadRequestAlertException("A new antecedentesPersonales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntecedentesPersonales result = antecedentesPersonalesRepository.save(antecedentesPersonales);
        return ResponseEntity.created(new URI("/api/antecedentes-personales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antecedentes-personales} : Updates an existing antecedentesPersonales.
     *
     * @param antecedentesPersonales the antecedentesPersonales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antecedentesPersonales,
     * or with status {@code 400 (Bad Request)} if the antecedentesPersonales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antecedentesPersonales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antecedentes-personales")
    public ResponseEntity<AntecedentesPersonales> updateAntecedentesPersonales(@RequestBody AntecedentesPersonales antecedentesPersonales) throws URISyntaxException {
        log.debug("REST request to update AntecedentesPersonales : {}", antecedentesPersonales);
        if (antecedentesPersonales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AntecedentesPersonales result = antecedentesPersonalesRepository.save(antecedentesPersonales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antecedentesPersonales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /antecedentes-personales} : get all the antecedentesPersonales.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antecedentesPersonales in body.
     */
    @GetMapping("/antecedentes-personales")
    public List<AntecedentesPersonales> getAllAntecedentesPersonales(@RequestParam(required = false) String filter) {
        if ("historiausuario-is-null".equals(filter)) {
            log.debug("REST request to get all AntecedentesPersonaless where historiaUsuario is null");
            return StreamSupport
                .stream(antecedentesPersonalesRepository.findAll().spliterator(), false)
                .filter(antecedentesPersonales -> antecedentesPersonales.getHistoriaUsuario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AntecedentesPersonales");
        return antecedentesPersonalesRepository.findAll();
    }

    /**
     * {@code GET  /antecedentes-personales/:id} : get the "id" antecedentesPersonales.
     *
     * @param id the id of the antecedentesPersonales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antecedentesPersonales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antecedentes-personales/{id}")
    public ResponseEntity<AntecedentesPersonales> getAntecedentesPersonales(@PathVariable Long id) {
        log.debug("REST request to get AntecedentesPersonales : {}", id);
        Optional<AntecedentesPersonales> antecedentesPersonales = antecedentesPersonalesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(antecedentesPersonales);
    }

    /**
     * {@code DELETE  /antecedentes-personales/:id} : delete the "id" antecedentesPersonales.
     *
     * @param id the id of the antecedentesPersonales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antecedentes-personales/{id}")
    public ResponseEntity<Void> deleteAntecedentesPersonales(@PathVariable Long id) {
        log.debug("REST request to delete AntecedentesPersonales : {}", id);
        antecedentesPersonalesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
