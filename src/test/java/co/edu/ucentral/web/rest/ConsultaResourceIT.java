package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.Consulta;
import co.edu.ucentral.repository.ConsultaRepository;

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
 * Integration tests for the {@link ConsultaResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ConsultaResourceIT {

    private static final String DEFAULT_SINTOMAS = "AAAAAAAAAA";
    private static final String UPDATED_SINTOMAS = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNOS = "AAAAAAAAAA";
    private static final String UPDATED_SIGNOS = "BBBBBBBBBB";

    private static final String DEFAULT_TIEMPO_SINTOMAS = "AAAAAAAAAA";
    private static final String UPDATED_TIEMPO_SINTOMAS = "BBBBBBBBBB";

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultaMockMvc;

    private Consulta consulta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createEntity(EntityManager em) {
        Consulta consulta = new Consulta()
            .sintomas(DEFAULT_SINTOMAS)
            .signos(DEFAULT_SIGNOS)
            .tiempoSintomas(DEFAULT_TIEMPO_SINTOMAS);
        return consulta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createUpdatedEntity(EntityManager em) {
        Consulta consulta = new Consulta()
            .sintomas(UPDATED_SINTOMAS)
            .signos(UPDATED_SIGNOS)
            .tiempoSintomas(UPDATED_TIEMPO_SINTOMAS);
        return consulta;
    }

    @BeforeEach
    public void initTest() {
        consulta = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsulta() throws Exception {
        int databaseSizeBeforeCreate = consultaRepository.findAll().size();

        // Create the Consulta
        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isCreated());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeCreate + 1);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getSintomas()).isEqualTo(DEFAULT_SINTOMAS);
        assertThat(testConsulta.getSignos()).isEqualTo(DEFAULT_SIGNOS);
        assertThat(testConsulta.getTiempoSintomas()).isEqualTo(DEFAULT_TIEMPO_SINTOMAS);
    }

    @Test
    @Transactional
    public void createConsultaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultaRepository.findAll().size();

        // Create the Consulta with an existing ID
        consulta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConsultas() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList
        restConsultaMockMvc.perform(get("/api/consultas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].sintomas").value(hasItem(DEFAULT_SINTOMAS)))
            .andExpect(jsonPath("$.[*].signos").value(hasItem(DEFAULT_SIGNOS)))
            .andExpect(jsonPath("$.[*].tiempoSintomas").value(hasItem(DEFAULT_TIEMPO_SINTOMAS)));
    }
    
    @Test
    @Transactional
    public void getConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get the consulta
        restConsultaMockMvc.perform(get("/api/consultas/{id}", consulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consulta.getId().intValue()))
            .andExpect(jsonPath("$.sintomas").value(DEFAULT_SINTOMAS))
            .andExpect(jsonPath("$.signos").value(DEFAULT_SIGNOS))
            .andExpect(jsonPath("$.tiempoSintomas").value(DEFAULT_TIEMPO_SINTOMAS));
    }

    @Test
    @Transactional
    public void getNonExistingConsulta() throws Exception {
        // Get the consulta
        restConsultaMockMvc.perform(get("/api/consultas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Update the consulta
        Consulta updatedConsulta = consultaRepository.findById(consulta.getId()).get();
        // Disconnect from session so that the updates on updatedConsulta are not directly saved in db
        em.detach(updatedConsulta);
        updatedConsulta
            .sintomas(UPDATED_SINTOMAS)
            .signos(UPDATED_SIGNOS)
            .tiempoSintomas(UPDATED_TIEMPO_SINTOMAS);

        restConsultaMockMvc.perform(put("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsulta)))
            .andExpect(status().isOk());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getSintomas()).isEqualTo(UPDATED_SINTOMAS);
        assertThat(testConsulta.getSignos()).isEqualTo(UPDATED_SIGNOS);
        assertThat(testConsulta.getTiempoSintomas()).isEqualTo(UPDATED_TIEMPO_SINTOMAS);
    }

    @Test
    @Transactional
    public void updateNonExistingConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Create the Consulta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultaMockMvc.perform(put("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeDelete = consultaRepository.findAll().size();

        // Delete the consulta
        restConsultaMockMvc.perform(delete("/api/consultas/{id}", consulta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
