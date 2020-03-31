package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.SignosVitales;
import co.edu.ucentral.repository.SignosVitalesRepository;

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
 * Integration tests for the {@link SignosVitalesResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SignosVitalesResourceIT {

    private static final Integer DEFAULT_PRESION_ARTERIAL = 1;
    private static final Integer UPDATED_PRESION_ARTERIAL = 2;

    private static final Integer DEFAULT_RESPIRACION = 1;
    private static final Integer UPDATED_RESPIRACION = 2;

    private static final Integer DEFAULT_PULSO = 1;
    private static final Integer UPDATED_PULSO = 2;

    private static final Float DEFAULT_TEMPERATURA = 1F;
    private static final Float UPDATED_TEMPERATURA = 2F;

    @Autowired
    private SignosVitalesRepository signosVitalesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSignosVitalesMockMvc;

    private SignosVitales signosVitales;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignosVitales createEntity(EntityManager em) {
        SignosVitales signosVitales = new SignosVitales()
            .presionArterial(DEFAULT_PRESION_ARTERIAL)
            .respiracion(DEFAULT_RESPIRACION)
            .pulso(DEFAULT_PULSO)
            .temperatura(DEFAULT_TEMPERATURA);
        return signosVitales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignosVitales createUpdatedEntity(EntityManager em) {
        SignosVitales signosVitales = new SignosVitales()
            .presionArterial(UPDATED_PRESION_ARTERIAL)
            .respiracion(UPDATED_RESPIRACION)
            .pulso(UPDATED_PULSO)
            .temperatura(UPDATED_TEMPERATURA);
        return signosVitales;
    }

    @BeforeEach
    public void initTest() {
        signosVitales = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignosVitales() throws Exception {
        int databaseSizeBeforeCreate = signosVitalesRepository.findAll().size();

        // Create the SignosVitales
        restSignosVitalesMockMvc.perform(post("/api/signos-vitales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signosVitales)))
            .andExpect(status().isCreated());

        // Validate the SignosVitales in the database
        List<SignosVitales> signosVitalesList = signosVitalesRepository.findAll();
        assertThat(signosVitalesList).hasSize(databaseSizeBeforeCreate + 1);
        SignosVitales testSignosVitales = signosVitalesList.get(signosVitalesList.size() - 1);
        assertThat(testSignosVitales.getPresionArterial()).isEqualTo(DEFAULT_PRESION_ARTERIAL);
        assertThat(testSignosVitales.getRespiracion()).isEqualTo(DEFAULT_RESPIRACION);
        assertThat(testSignosVitales.getPulso()).isEqualTo(DEFAULT_PULSO);
        assertThat(testSignosVitales.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
    }

    @Test
    @Transactional
    public void createSignosVitalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signosVitalesRepository.findAll().size();

        // Create the SignosVitales with an existing ID
        signosVitales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignosVitalesMockMvc.perform(post("/api/signos-vitales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signosVitales)))
            .andExpect(status().isBadRequest());

        // Validate the SignosVitales in the database
        List<SignosVitales> signosVitalesList = signosVitalesRepository.findAll();
        assertThat(signosVitalesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSignosVitales() throws Exception {
        // Initialize the database
        signosVitalesRepository.saveAndFlush(signosVitales);

        // Get all the signosVitalesList
        restSignosVitalesMockMvc.perform(get("/api/signos-vitales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signosVitales.getId().intValue())))
            .andExpect(jsonPath("$.[*].presionArterial").value(hasItem(DEFAULT_PRESION_ARTERIAL)))
            .andExpect(jsonPath("$.[*].respiracion").value(hasItem(DEFAULT_RESPIRACION)))
            .andExpect(jsonPath("$.[*].pulso").value(hasItem(DEFAULT_PULSO)))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSignosVitales() throws Exception {
        // Initialize the database
        signosVitalesRepository.saveAndFlush(signosVitales);

        // Get the signosVitales
        restSignosVitalesMockMvc.perform(get("/api/signos-vitales/{id}", signosVitales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signosVitales.getId().intValue()))
            .andExpect(jsonPath("$.presionArterial").value(DEFAULT_PRESION_ARTERIAL))
            .andExpect(jsonPath("$.respiracion").value(DEFAULT_RESPIRACION))
            .andExpect(jsonPath("$.pulso").value(DEFAULT_PULSO))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSignosVitales() throws Exception {
        // Get the signosVitales
        restSignosVitalesMockMvc.perform(get("/api/signos-vitales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignosVitales() throws Exception {
        // Initialize the database
        signosVitalesRepository.saveAndFlush(signosVitales);

        int databaseSizeBeforeUpdate = signosVitalesRepository.findAll().size();

        // Update the signosVitales
        SignosVitales updatedSignosVitales = signosVitalesRepository.findById(signosVitales.getId()).get();
        // Disconnect from session so that the updates on updatedSignosVitales are not directly saved in db
        em.detach(updatedSignosVitales);
        updatedSignosVitales
            .presionArterial(UPDATED_PRESION_ARTERIAL)
            .respiracion(UPDATED_RESPIRACION)
            .pulso(UPDATED_PULSO)
            .temperatura(UPDATED_TEMPERATURA);

        restSignosVitalesMockMvc.perform(put("/api/signos-vitales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignosVitales)))
            .andExpect(status().isOk());

        // Validate the SignosVitales in the database
        List<SignosVitales> signosVitalesList = signosVitalesRepository.findAll();
        assertThat(signosVitalesList).hasSize(databaseSizeBeforeUpdate);
        SignosVitales testSignosVitales = signosVitalesList.get(signosVitalesList.size() - 1);
        assertThat(testSignosVitales.getPresionArterial()).isEqualTo(UPDATED_PRESION_ARTERIAL);
        assertThat(testSignosVitales.getRespiracion()).isEqualTo(UPDATED_RESPIRACION);
        assertThat(testSignosVitales.getPulso()).isEqualTo(UPDATED_PULSO);
        assertThat(testSignosVitales.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void updateNonExistingSignosVitales() throws Exception {
        int databaseSizeBeforeUpdate = signosVitalesRepository.findAll().size();

        // Create the SignosVitales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignosVitalesMockMvc.perform(put("/api/signos-vitales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signosVitales)))
            .andExpect(status().isBadRequest());

        // Validate the SignosVitales in the database
        List<SignosVitales> signosVitalesList = signosVitalesRepository.findAll();
        assertThat(signosVitalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSignosVitales() throws Exception {
        // Initialize the database
        signosVitalesRepository.saveAndFlush(signosVitales);

        int databaseSizeBeforeDelete = signosVitalesRepository.findAll().size();

        // Delete the signosVitales
        restSignosVitalesMockMvc.perform(delete("/api/signos-vitales/{id}", signosVitales.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SignosVitales> signosVitalesList = signosVitalesRepository.findAll();
        assertThat(signosVitalesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
