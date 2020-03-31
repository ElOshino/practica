package co.edu.ucentral.web.rest;

import co.edu.ucentral.domain.HistoriaUsuario;
import co.edu.ucentral.repository.HistoriaUsuarioRepository;
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
 * REST controller for managing {@link co.edu.ucentral.domain.HistoriaUsuario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HistoriaUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(HistoriaUsuarioResource.class);

    private static final String ENTITY_NAME = "historiaUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriaUsuarioRepository historiaUsuarioRepository;

    public HistoriaUsuarioResource(HistoriaUsuarioRepository historiaUsuarioRepository) {
        this.historiaUsuarioRepository = historiaUsuarioRepository;
    }

    /**
     * {@code POST  /historia-usuarios} : Create a new historiaUsuario.
     *
     * @param historiaUsuario the historiaUsuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiaUsuario, or with status {@code 400 (Bad Request)} if the historiaUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historia-usuarios")
    public ResponseEntity<HistoriaUsuario> createHistoriaUsuario(@RequestBody HistoriaUsuario historiaUsuario) throws URISyntaxException {
        log.debug("REST request to save HistoriaUsuario : {}", historiaUsuario);
        if (historiaUsuario.getId() != null) {
            throw new BadRequestAlertException("A new historiaUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriaUsuario result = historiaUsuarioRepository.save(historiaUsuario);
        return ResponseEntity.created(new URI("/api/historia-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historia-usuarios} : Updates an existing historiaUsuario.
     *
     * @param historiaUsuario the historiaUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiaUsuario,
     * or with status {@code 400 (Bad Request)} if the historiaUsuario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiaUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historia-usuarios")
    public ResponseEntity<HistoriaUsuario> updateHistoriaUsuario(@RequestBody HistoriaUsuario historiaUsuario) throws URISyntaxException {
        log.debug("REST request to update HistoriaUsuario : {}", historiaUsuario);
        if (historiaUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HistoriaUsuario result = historiaUsuarioRepository.save(historiaUsuario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historiaUsuario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /historia-usuarios} : get all the historiaUsuarios.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiaUsuarios in body.
     */
    @GetMapping("/historia-usuarios")
    public List<HistoriaUsuario> getAllHistoriaUsuarios(@RequestParam(required = false) String filter) {
        if ("paciente-is-null".equals(filter)) {
            log.debug("REST request to get all HistoriaUsuarios where paciente is null");
            return StreamSupport
                .stream(historiaUsuarioRepository.findAll().spliterator(), false)
                .filter(historiaUsuario -> historiaUsuario.getPaciente() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all HistoriaUsuarios");
        return historiaUsuarioRepository.findAll();
    }

    /**
     * {@code GET  /historia-usuarios/:id} : get the "id" historiaUsuario.
     *
     * @param id the id of the historiaUsuario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiaUsuario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historia-usuarios/{id}")
    public ResponseEntity<HistoriaUsuario> getHistoriaUsuario(@PathVariable Long id) {
        log.debug("REST request to get HistoriaUsuario : {}", id);
        Optional<HistoriaUsuario> historiaUsuario = historiaUsuarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historiaUsuario);
    }

    /**
     * {@code DELETE  /historia-usuarios/:id} : delete the "id" historiaUsuario.
     *
     * @param id the id of the historiaUsuario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historia-usuarios/{id}")
    public ResponseEntity<Void> deleteHistoriaUsuario(@PathVariable Long id) {
        log.debug("REST request to delete HistoriaUsuario : {}", id);
        historiaUsuarioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
