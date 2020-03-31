package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.AntecedentesPersonales;
import co.edu.ucentral.repository.AntecedentesPersonalesRepository;

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
 * Integration tests for the {@link AntecedentesPersonalesResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AntecedentesPersonalesResourceIT {

    private static final String DEFAULT_CIRUGIAS = "AAAAAAAAAA";
    private static final String UPDATED_CIRUGIAS = "BBBBBBBBBB";

    private static final String DEFAULT_TRAUMATISMOS = "AAAAAAAAAA";
    private static final String UPDATED_TRAUMATISMOS = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSFUSION_SANGUINEA = "AAAAAAAAAA";
    private static final String UPDATED_TRANSFUSION_SANGUINEA = "BBBBBBBBBB";

    private static final String DEFAULT_ALERGIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALERGIAS = "BBBBBBBBBB";

    @Autowired
    private AntecedentesPersonalesRepository antecedentesPersonalesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntecedentesPersonalesMockMvc;

    private AntecedentesPersonales antecedentesPersonales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntecedentesPersonales createEntity(EntityManager em) {
        AntecedentesPersonales antecedentesPersonales = new AntecedentesPersonales()
            .cirugias(DEFAULT_CIRUGIAS)
            .traumatismos(DEFAULT_TRAUMATISMOS)
            .transfusionSanguinea(DEFAULT_TRANSFUSION_SANGUINEA)
            .alergias(DEFAULT_ALERGIAS);
        return antecedentesPersonales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntecedentesPersonales createUpdatedEntity(EntityManager em) {
        AntecedentesPersonales antecedentesPersonales = new AntecedentesPersonales()
            .cirugias(UPDATED_CIRUGIAS)
            .traumatismos(UPDATED_TRAUMATISMOS)
            .transfusionSanguinea(UPDATED_TRANSFUSION_SANGUINEA)
            .alergias(UPDATED_ALERGIAS);
        return antecedentesPersonales;
    }

    @BeforeEach
    public void initTest() {
        antecedentesPersonales = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntecedentesPersonales() throws Exception {
        int databaseSizeBeforeCreate = antecedentesPersonalesRepository.findAll().size();

        // Create the AntecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(post("/api/antecedentes-personales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonales)))
            .andExpect(status().isCreated());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeCreate + 1);
        AntecedentesPersonales testAntecedentesPersonales = antecedentesPersonalesList.get(antecedentesPersonalesList.size() - 1);
        assertThat(testAntecedentesPersonales.getCirugias()).isEqualTo(DEFAULT_CIRUGIAS);
        assertThat(testAntecedentesPersonales.getTraumatismos()).isEqualTo(DEFAULT_TRAUMATISMOS);
        assertThat(testAntecedentesPersonales.getTransfusionSanguinea()).isEqualTo(DEFAULT_TRANSFUSION_SANGUINEA);
        assertThat(testAntecedentesPersonales.getAlergias()).isEqualTo(DEFAULT_ALERGIAS);
    }

    @Test
    @Transactional
    public void createAntecedentesPersonalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antecedentesPersonalesRepository.findAll().size();

        // Create the AntecedentesPersonales with an existing ID
        antecedentesPersonales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntecedentesPersonalesMockMvc.perform(post("/api/antecedentes-personales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonales)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        // Get all the antecedentesPersonalesList
        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antecedentesPersonales.getId().intValue())))
            .andExpect(jsonPath("$.[*].cirugias").value(hasItem(DEFAULT_CIRUGIAS)))
            .andExpect(jsonPath("$.[*].traumatismos").value(hasItem(DEFAULT_TRAUMATISMOS)))
            .andExpect(jsonPath("$.[*].transfusionSanguinea").value(hasItem(DEFAULT_TRANSFUSION_SANGUINEA)))
            .andExpect(jsonPath("$.[*].alergias").value(hasItem(DEFAULT_ALERGIAS)));
    }
    
    @Test
    @Transactional
    public void getAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        // Get the antecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales/{id}", antecedentesPersonales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antecedentesPersonales.getId().intValue()))
            .andExpect(jsonPath("$.cirugias").value(DEFAULT_CIRUGIAS))
            .andExpect(jsonPath("$.traumatismos").value(DEFAULT_TRAUMATISMOS))
            .andExpect(jsonPath("$.transfusionSanguinea").value(DEFAULT_TRANSFUSION_SANGUINEA))
            .andExpect(jsonPath("$.alergias").value(DEFAULT_ALERGIAS));
    }

    @Test
    @Transactional
    public void getNonExistingAntecedentesPersonales() throws Exception {
        // Get the antecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(get("/api/antecedentes-personales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        int databaseSizeBeforeUpdate = antecedentesPersonalesRepository.findAll().size();

        // Update the antecedentesPersonales
        AntecedentesPersonales updatedAntecedentesPersonales = antecedentesPersonalesRepository.findById(antecedentesPersonales.getId()).get();
        // Disconnect from session so that the updates on updatedAntecedentesPersonales are not directly saved in db
        em.detach(updatedAntecedentesPersonales);
        updatedAntecedentesPersonales
            .cirugias(UPDATED_CIRUGIAS)
            .traumatismos(UPDATED_TRAUMATISMOS)
            .transfusionSanguinea(UPDATED_TRANSFUSION_SANGUINEA)
            .alergias(UPDATED_ALERGIAS);

        restAntecedentesPersonalesMockMvc.perform(put("/api/antecedentes-personales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAntecedentesPersonales)))
            .andExpect(status().isOk());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeUpdate);
        AntecedentesPersonales testAntecedentesPersonales = antecedentesPersonalesList.get(antecedentesPersonalesList.size() - 1);
        assertThat(testAntecedentesPersonales.getCirugias()).isEqualTo(UPDATED_CIRUGIAS);
        assertThat(testAntecedentesPersonales.getTraumatismos()).isEqualTo(UPDATED_TRAUMATISMOS);
        assertThat(testAntecedentesPersonales.getTransfusionSanguinea()).isEqualTo(UPDATED_TRANSFUSION_SANGUINEA);
        assertThat(testAntecedentesPersonales.getAlergias()).isEqualTo(UPDATED_ALERGIAS);
    }

    @Test
    @Transactional
    public void updateNonExistingAntecedentesPersonales() throws Exception {
        int databaseSizeBeforeUpdate = antecedentesPersonalesRepository.findAll().size();

        // Create the AntecedentesPersonales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntecedentesPersonalesMockMvc.perform(put("/api/antecedentes-personales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antecedentesPersonales)))
            .andExpect(status().isBadRequest());

        // Validate the AntecedentesPersonales in the database
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAntecedentesPersonales() throws Exception {
        // Initialize the database
        antecedentesPersonalesRepository.saveAndFlush(antecedentesPersonales);

        int databaseSizeBeforeDelete = antecedentesPersonalesRepository.findAll().size();

        // Delete the antecedentesPersonales
        restAntecedentesPersonalesMockMvc.perform(delete("/api/antecedentes-personales/{id}", antecedentesPersonales.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AntecedentesPersonales> antecedentesPersonalesList = antecedentesPersonalesRepository.findAll();
        assertThat(antecedentesPersonalesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
