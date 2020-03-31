package co.edu.ucentral.web.rest;

import co.edu.ucentral.domain.SignosVitales;
import co.edu.ucentral.repository.SignosVitalesRepository;
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
 * REST controller for managing {@link co.edu.ucentral.domain.SignosVitales}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SignosVitalesResource {

    private final Logger log = LoggerFactory.getLogger(SignosVitalesResource.class);

    private static final String ENTITY_NAME = "signosVitales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignosVitalesRepository signosVitalesRepository;

    public SignosVitalesResource(SignosVitalesRepository signosVitalesRepository) {
        this.signosVitalesRepository = signosVitalesRepository;
    }

    /**
     * {@code POST  /signos-vitales} : Create a new signosVitales.
     *
     * @param signosVitales the signosVitales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signosVitales, or with status {@code 400 (Bad Request)} if the signosVitales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signos-vitales")
    public ResponseEntity<SignosVitales> createSignosVitales(@RequestBody SignosVitales signosVitales) throws URISyntaxException {
        log.debug("REST request to save SignosVitales : {}", signosVitales);
        if (signosVitales.getId() != null) {
            throw new BadRequestAlertException("A new signosVitales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignosVitales result = signosVitalesRepository.save(signosVitales);
        return ResponseEntity.created(new URI("/api/signos-vitales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /signos-vitales} : Updates an existing signosVitales.
     *
     * @param signosVitales the signosVitales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signosVitales,
     * or with status {@code 400 (Bad Request)} if the signosVitales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signosVitales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signos-vitales")
    public ResponseEntity<SignosVitales> updateSignosVitales(@RequestBody SignosVitales signosVitales) throws URISyntaxException {
        log.debug("REST request to update SignosVitales : {}", signosVitales);
        if (signosVitales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignosVitales result = signosVitalesRepository.save(signosVitales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signosVitales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /signos-vitales} : get all the signosVitales.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signosVitales in body.
     */
    @GetMapping("/signos-vitales")
    public List<SignosVitales> getAllSignosVitales(@RequestParam(required = false) String filter) {
        if ("historiausuario-is-null".equals(filter)) {
            log.debug("REST request to get all SignosVitaless where historiaUsuario is null");
            return StreamSupport
                .stream(signosVitalesRepository.findAll().spliterator(), false)
                .filter(signosVitales -> signosVitales.getHistoriaUsuario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all SignosVitales");
        return signosVitalesRepository.findAll();
    }

    /**
     * {@code GET  /signos-vitales/:id} : get the "id" signosVitales.
     *
     * @param id the id of the signosVitales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signosVitales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signos-vitales/{id}")
    public ResponseEntity<SignosVitales> getSignosVitales(@PathVariable Long id) {
        log.debug("REST request to get SignosVitales : {}", id);
        Optional<SignosVitales> signosVitales = signosVitalesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(signosVitales);
    }

    /**
     * {@code DELETE  /signos-vitales/:id} : delete the "id" signosVitales.
     *
     * @param id the id of the signosVitales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signos-vitales/{id}")
    public ResponseEntity<Void> deleteSignosVitales(@PathVariable Long id) {
        log.debug("REST request to delete SignosVitales : {}", id);
        signosVitalesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
