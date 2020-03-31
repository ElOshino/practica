package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.ExamenFisico;
import co.edu.ucentral.repository.ExamenFisicoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExamenFisicoResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ExamenFisicoResourceIT {

    private static final Float DEFAULT_PESO = 1F;
    private static final Float UPDATED_PESO = 2F;

    private static final Float DEFAULT_TALLA = 1F;
    private static final Float UPDATED_TALLA = 2F;

    private static final Float DEFAULT_INDICE_MASA_CORPORAL = 1F;
    private static final Float UPDATED_INDICE_MASA_CORPORAL = 2F;

    @Autowired
    private ExamenFisicoRepository examenFisicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamenFisicoMockMvc;

    private ExamenFisico examenFisico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamenFisico createEntity(EntityManager em) {
        ExamenFisico examenFisico = new ExamenFisico()
            .peso(DEFAULT_PESO)
            .talla(DEFAULT_TALLA)
            .indiceMasaCorporal(DEFAULT_INDICE_MASA_CORPORAL);
        return examenFisico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamenFisico createUpdatedEntity(EntityManager em) {
        ExamenFisico examenFisico = new ExamenFisico()
            .peso(UPDATED_PESO)
            .talla(UPDATED_TALLA)
            .indiceMasaCorporal(UPDATED_INDICE_MASA_CORPORAL);
        return examenFisico;
    }

    @BeforeEach
    public void initTest() {
        examenFisico = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamenFisico() throws Exception {
        int databaseSizeBeforeCreate = examenFisicoRepository.findAll().size();

        // Create the ExamenFisico
        restExamenFisicoMockMvc.perform(post("/api/examen-fisicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examenFisico)))
            .andExpect(status().isCreated());

        // Validate the ExamenFisico in the database
        List<ExamenFisico> examenFisicoList = examenFisicoRepository.findAll();
        assertThat(examenFisicoList).hasSize(databaseSizeBeforeCreate + 1);
        ExamenFisico testExamenFisico = examenFisicoList.get(examenFisicoList.size() - 1);
        assertThat(testExamenFisico.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testExamenFisico.getTalla()).isEqualTo(DEFAULT_TALLA);
        assertThat(testExamenFisico.getIndiceMasaCorporal()).isEqualTo(DEFAULT_INDICE_MASA_CORPORAL);
    }

    @Test
    @Transactional
    public void createExamenFisicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examenFisicoRepository.findAll().size();

        // Create the ExamenFisico with an existing ID
        examenFisico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamenFisicoMockMvc.perform(post("/api/examen-fisicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examenFisico)))
            .andExpect(status().isBadRequest());

        // Validate the ExamenFisico in the database
        List<ExamenFisico> examenFisicoList = examenFisicoRepository.findAll();
        assertThat(examenFisicoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExamenFisicos() throws Exception {
        // Initialize the database
        examenFisicoRepository.saveAndFlush(examenFisico);

        // Get all the examenFisicoList
        restExamenFisicoMockMvc.perform(get("/api/examen-fisicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examenFisico.getId().intValue())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].talla").value(hasItem(DEFAULT_TALLA.doubleValue())))
            .andExpect(jsonPath("$.[*].indiceMasaCorporal").value(hasItem(DEFAULT_INDICE_MASA_CORPORAL.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getExamenFisico() throws Exception {
        // Initialize the database
        examenFisicoRepository.saveAndFlush(examenFisico);

        // Get the examenFisico
        restExamenFisicoMockMvc.perform(get("/api/examen-fisicos/{id}", examenFisico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examenFisico.getId().intValue()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.talla").value(DEFAULT_TALLA.doubleValue()))
            .andExpect(jsonPath("$.indiceMasaCorporal").value(DEFAULT_INDICE_MASA_CORPORAL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExamenFisico() throws Exception {
        // Get the examenFisico
        restExamenFisicoMockMvc.perform(get("/api/examen-fisicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamenFisico() throws Exception {
        // Initialize the database
        examenFisicoRepository.saveAndFlush(examenFisico);

        int databaseSizeBeforeUpdate = examenFisicoRepository.findAll().size();

        // Update the examenFisico
        ExamenFisico updatedExamenFisico = examenFisicoRepository.findById(examenFisico.getId()).get();
        // Disconnect from session so that the updates on updatedExamenFisico are not directly saved in db
        em.detach(updatedExamenFisico);
        updatedExamenFisico
            .peso(UPDATED_PESO)
            .talla(UPDATED_TALLA)
            .indiceMasaCorporal(UPDATED_INDICE_MASA_CORPORAL);

        restExamenFisicoMockMvc.perform(put("/api/examen-fisicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamenFisico)))
            .andExpect(status().isOk());

        // Validate the ExamenFisico in the database
        List<ExamenFisico> examenFisicoList = examenFisicoRepository.findAll();
        assertThat(examenFisicoList).hasSize(databaseSizeBeforeUpdate);
        ExamenFisico testExamenFisico = examenFisicoList.get(examenFisicoList.size() - 1);
        assertThat(testExamenFisico.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testExamenFisico.getTalla()).isEqualTo(UPDATED_TALLA);
        assertThat(testExamenFisico.getIndiceMasaCorporal()).isEqualTo(UPDATED_INDICE_MASA_CORPORAL);
    }

    @Test
    @Transactional
    public void updateNonExistingExamenFisico() throws Exception {
        int databaseSizeBeforeUpdate = examenFisicoRepository.findAll().size();

        // Create the ExamenFisico

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamenFisicoMockMvc.perform(put("/api/examen-fisicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examenFisico)))
            .andExpect(status().isBadRequest());

        // Validate the ExamenFisico in the database
        List<ExamenFisico> examenFisicoList = examenFisicoRepository.findAll();
        assertThat(examenFisicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamenFisico() throws Exception {
        // Initialize the database
        examenFisicoRepository.saveAndFlush(examenFisico);

        int databaseSizeBeforeDelete = examenFisicoRepository.findAll().size();

        // Delete the examenFisico
        restExamenFisicoMockMvc.perform(delete("/api/examen-fisicos/{id}", examenFisico.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamenFisico> examenFisicoList = examenFisicoRepository.findAll();
        assertThat(examenFisicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
