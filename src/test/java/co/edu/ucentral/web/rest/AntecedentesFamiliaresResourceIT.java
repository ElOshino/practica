package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.AntecedentesFamiliares;
import co.edu.ucentral.repository.AntecedentesFamiliaresRepository;

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
 * Integration tests for the {@link AntecedentesFamiliaresResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AntecedentesFamiliaresResourceIT {

    private static final String DEFAULT_MADRE = "AAAAAAAAAA";
    private static final String UPDATED_MADRE = "BBBBBBBBBB";

    private static final String DEFAULT_PADRE = "AAAAAAAAAA";
    private static final String UPDATED_PADRE = "BBBBBBBBBB";

    private static final String DEFAULT_ABUELO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_ABUELO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_ABUELO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_ABUELO_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_ABUELA_PATERNA = "AAAAAAAAAA";
    private static final String UPDATED_ABUELA_PATERNA = "BBBBBBBBBB";

    private static final String DEFAULT_ABUELA_MATERNA = "AAAAAAAAAA";
    private static final String UPDATED_ABUELA_MATERNA = "BBBBBBBBBB";

    @Autowired
    private AntecedentesFamiliaresRepository antecedentesFamiliaresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntecedentesFamiliaresMockMvc;

    private AntecedentesFamiliares antecedentesFamiliares;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntecedentesFamiliares createEntity(EntityManager em) {
        AntecedentesFamiliares antecedentesFamiliares = new AntecedentesFamiliares()
            .madre(DEFAULT_MADRE)
            .padre(DEFAULT_PADRE)
            .abueloPaterno(DEFAULT_ABUELO_PATERNO)
            .abueloMaterno(DEFAULT_ABUELO_MATERNO)
            .abuelaPaterna(DEFAULT_ABUELA_PATERNA)
            .abuelaMaterna(DEFAULT_ABUELA_MATERNA);
        return antecedentesFamiliares;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntecedentesFamiliares createUpdatedEntity(EntityManager em) {
        AntecedentesFamiliares antecedentesFamiliares = new AntecedentesFamiliares()
            .madre(UPDATED_MADRE)
            .padre(UPDATED_PADRE)
            .abueloPaterno(UPDATED_ABUELO_PATERNO)
            .abueloMaterno(UPDATED_ABUELO_MATERNO)
            .abuelaPaterna(UPDATED_ABUELA_PATERNA)
            .abuelaMaterna(UPDATED_ABUELA_MATERNA);
        return antecedentesFamiliares;
    }

    @BeforeEach
    public void initTest() {
        antecedentesFamiliares = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntecedentesFamiliares() throws Exception {
        int databaseSizeBeforeCreate = antecedentesFamiliaresRepository.findAll().size();

        // Create the AntecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(post("/api/antecedentes-familiares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliares)))
            .andExpect(status().isCreated());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeCreate + 1);
        AntecedentesFamiliares testAntecedentesFamiliares = antecedentesFamiliaresList.get(antecedentesFamiliaresList.size() - 1);
        assertThat(testAntecedentesFamiliares.getMadre()).isEqualTo(DEFAULT_MADRE);
        assertThat(testAntecedentesFamiliares.getPadre()).isEqualTo(DEFAULT_PADRE);
        assertThat(testAntecedentesFamiliares.getAbueloPaterno()).isEqualTo(DEFAULT_ABUELO_PATERNO);
        assertThat(testAntecedentesFamiliares.getAbueloMaterno()).isEqualTo(DEFAULT_ABUELO_MATERNO);
        assertThat(testAntecedentesFamiliares.getAbuelaPaterna()).isEqualTo(DEFAULT_ABUELA_PATERNA);
        assertThat(testAntecedentesFamiliares.getAbuelaMaterna()).isEqualTo(DEFAULT_ABUELA_MATERNA);
    }

    @Test
    @Transactional
    public void createAntecedentesFamiliaresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antecedentesFamiliaresRepository.findAll().size();

        // Create the AntecedentesFamiliares with an existing ID
        antecedentesFamiliares.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntecedentesFamiliaresMockMvc.perform(post("/api/antecedentes-familiares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliares)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        // Get all the antecedentesFamiliaresList
        restAntecedentesFamiliaresMockMvc.perform(get("/api/antecedentes-familiares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antecedentesFamiliares.getId().intValue())))
            .andExpect(jsonPath("$.[*].madre").value(hasItem(DEFAULT_MADRE)))
            .andExpect(jsonPath("$.[*].padre").value(hasItem(DEFAULT_PADRE)))
            .andExpect(jsonPath("$.[*].abueloPaterno").value(hasItem(DEFAULT_ABUELO_PATERNO)))
            .andExpect(jsonPath("$.[*].abueloMaterno").value(hasItem(DEFAULT_ABUELO_MATERNO)))
            .andExpect(jsonPath("$.[*].abuelaPaterna").value(hasItem(DEFAULT_ABUELA_PATERNA)))
            .andExpect(jsonPath("$.[*].abuelaMaterna").value(hasItem(DEFAULT_ABUELA_MATERNA)));
    }
    
    @Test
    @Transactional
    public void getAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        // Get the antecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(get("/api/antecedentes-familiares/{id}", antecedentesFamiliares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antecedentesFamiliares.getId().intValue()))
            .andExpect(jsonPath("$.madre").value(DEFAULT_MADRE))
            .andExpect(jsonPath("$.padre").value(DEFAULT_PADRE))
            .andExpect(jsonPath("$.abueloPaterno").value(DEFAULT_ABUELO_PATERNO))
            .andExpect(jsonPath("$.abueloMaterno").value(DEFAULT_ABUELO_MATERNO))
            .andExpect(jsonPath("$.abuelaPaterna").value(DEFAULT_ABUELA_PATERNA))
            .andExpect(jsonPath("$.abuelaMaterna").value(DEFAULT_ABUELA_MATERNA));
    }

    @Test
    @Transactional
    public void getNonExistingAntecedentesFamiliares() throws Exception {
        // Get the antecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(get("/api/antecedentes-familiares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        int databaseSizeBeforeUpdate = antecedentesFamiliaresRepository.findAll().size();

        // Update the antecedentesFamiliares
        AntecedentesFamiliares updatedAntecedentesFamiliares = antecedentesFamiliaresRepository.findById(antecedentesFamiliares.getId()).get();
        // Disconnect from session so that the updates on updatedAntecedentesFamiliares are not directly saved in db
        em.detach(updatedAntecedentesFamiliares);
        updatedAntecedentesFamiliares
            .madre(UPDATED_MADRE)
            .padre(UPDATED_PADRE)
            .abueloPaterno(UPDATED_ABUELO_PATERNO)
            .abueloMaterno(UPDATED_ABUELO_MATERNO)
            .abuelaPaterna(UPDATED_ABUELA_PATERNA)
            .abuelaMaterna(UPDATED_ABUELA_MATERNA);

        restAntecedentesFamiliaresMockMvc.perform(put("/api/antecedentes-familiares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAntecedentesFamiliares)))
            .andExpect(status().isOk());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeUpdate);
        AntecedentesFamiliares testAntecedentesFamiliares = antecedentesFamiliaresList.get(antecedentesFamiliaresList.size() - 1);
        assertThat(testAntecedentesFamiliares.getMadre()).isEqualTo(UPDATED_MADRE);
        assertThat(testAntecedentesFamiliares.getPadre()).isEqualTo(UPDATED_PADRE);
        assertThat(testAntecedentesFamiliares.getAbueloPaterno()).isEqualTo(UPDATED_ABUELO_PATERNO);
        assertThat(testAntecedentesFamiliares.getAbueloMaterno()).isEqualTo(UPDATED_ABUELO_MATERNO);
        assertThat(testAntecedentesFamiliares.getAbuelaPaterna()).isEqualTo(UPDATED_ABUELA_PATERNA);
        assertThat(testAntecedentesFamiliares.getAbuelaMaterna()).isEqualTo(UPDATED_ABUELA_MATERNA);
    }

    @Test
    @Transactional
    public void updateNonExistingAntecedentesFamiliares() throws Exception {
        int databaseSizeBeforeUpdate = antecedentesFamiliaresRepository.findAll().size();

        // Create the AntecedentesFamiliares

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntecedentesFamiliaresMockMvc.perform(put("/api/antecedentes-familiares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesFamiliares)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesFamiliares in the database
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAntecedentesFamiliares() throws Exception {
        // Initialize the database
        antecedentesFamiliaresRepository.saveAndFlush(antecedentesFamiliares);

        int databaseSizeBeforeDelete = antecedentesFamiliaresRepository.findAll().size();

        // Delete the antecedentesFamiliares
        restAntecedentesFamiliaresMockMvc.perform(delete("/api/antecedentes-familiares/{id}", antecedentesFamiliares.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AntecedentesFamiliares> antecedentesFamiliaresList = antecedentesFamiliaresRepository.findAll();
        assertThat(antecedentesFamiliaresList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
