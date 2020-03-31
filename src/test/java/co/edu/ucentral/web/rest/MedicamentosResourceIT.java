package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.Medicamentos;
import co.edu.ucentral.repository.MedicamentosRepository;

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
 * Integration tests for the {@link MedicamentosResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MedicamentosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_POSOLOGIA = "AAAAAAAAAA";
    private static final String UPDATED_POSOLOGIA = "BBBBBBBBBB";

    private static final String DEFAULT_TIEMPO_TOMANDO = "AAAAAAAAAA";
    private static final String UPDATED_TIEMPO_TOMANDO = "BBBBBBBBBB";

    @Autowired
    private MedicamentosRepository medicamentosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicamentosMockMvc;

    private Medicamentos medicamentos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicamentos createEntity(EntityManager em) {
        Medicamentos medicamentos = new Medicamentos()
            .nombre(DEFAULT_NOMBRE)
            .posologia(DEFAULT_POSOLOGIA)
            .tiempoTomando(DEFAULT_TIEMPO_TOMANDO);
        return medicamentos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicamentos createUpdatedEntity(EntityManager em) {
        Medicamentos medicamentos = new Medicamentos()
            .nombre(UPDATED_NOMBRE)
            .posologia(UPDATED_POSOLOGIA)
            .tiempoTomando(UPDATED_TIEMPO_TOMANDO);
        return medicamentos;
    }

    @BeforeEach
    public void initTest() {
        medicamentos = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicamentos() throws Exception {
        int databaseSizeBeforeCreate = medicamentosRepository.findAll().size();

        // Create the Medicamentos
        restMedicamentosMockMvc.perform(post("/api/medicamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicamentos)))
            .andExpect(status().isCreated());

        // Validate the Medicamentos in the database
        List<Medicamentos> medicamentosList = medicamentosRepository.findAll();
        assertThat(medicamentosList).hasSize(databaseSizeBeforeCreate + 1);
        Medicamentos testMedicamentos = medicamentosList.get(medicamentosList.size() - 1);
        assertThat(testMedicamentos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMedicamentos.getPosologia()).isEqualTo(DEFAULT_POSOLOGIA);
        assertThat(testMedicamentos.getTiempoTomando()).isEqualTo(DEFAULT_TIEMPO_TOMANDO);
    }

    @Test
    @Transactional
    public void createMedicamentosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicamentosRepository.findAll().size();

        // Create the Medicamentos with an existing ID
        medicamentos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicamentosMockMvc.perform(post("/api/medicamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Medicamentos in the database
        List<Medicamentos> medicamentosList = medicamentosRepository.findAll();
        assertThat(medicamentosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicamentos() throws Exception {
        // Initialize the database
        medicamentosRepository.saveAndFlush(medicamentos);

        // Get all the medicamentosList
        restMedicamentosMockMvc.perform(get("/api/medicamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].posologia").value(hasItem(DEFAULT_POSOLOGIA)))
            .andExpect(jsonPath("$.[*].tiempoTomando").value(hasItem(DEFAULT_TIEMPO_TOMANDO)));
    }
    
    @Test
    @Transactional
    public void getMedicamentos() throws Exception {
        // Initialize the database
        medicamentosRepository.saveAndFlush(medicamentos);

        // Get the medicamentos
        restMedicamentosMockMvc.perform(get("/api/medicamentos/{id}", medicamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicamentos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.posologia").value(DEFAULT_POSOLOGIA))
            .andExpect(jsonPath("$.tiempoTomando").value(DEFAULT_TIEMPO_TOMANDO));
    }

    @Test
    @Transactional
    public void getNonExistingMedicamentos() throws Exception {
        // Get the medicamentos
        restMedicamentosMockMvc.perform(get("/api/medicamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicamentos() throws Exception {
        // Initialize the database
        medicamentosRepository.saveAndFlush(medicamentos);

        int databaseSizeBeforeUpdate = medicamentosRepository.findAll().size();

        // Update the medicamentos
        Medicamentos updatedMedicamentos = medicamentosRepository.findById(medicamentos.getId()).get();
        // Disconnect from session so that the updates on updatedMedicamentos are not directly saved in db
        em.detach(updatedMedicamentos);
        updatedMedicamentos
            .nombre(UPDATED_NOMBRE)
            .posologia(UPDATED_POSOLOGIA)
            .tiempoTomando(UPDATED_TIEMPO_TOMANDO);

        restMedicamentosMockMvc.perform(put("/api/medicamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicamentos)))
            .andExpect(status().isOk());

        // Validate the Medicamentos in the database
        List<Medicamentos> medicamentosList = medicamentosRepository.findAll();
        assertThat(medicamentosList).hasSize(databaseSizeBeforeUpdate);
        Medicamentos testMedicamentos = medicamentosList.get(medicamentosList.size() - 1);
        assertThat(testMedicamentos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMedicamentos.getPosologia()).isEqualTo(UPDATED_POSOLOGIA);
        assertThat(testMedicamentos.getTiempoTomando()).isEqualTo(UPDATED_TIEMPO_TOMANDO);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicamentos() throws Exception {
        int databaseSizeBeforeUpdate = medicamentosRepository.findAll().size();

        // Create the Medicamentos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicamentosMockMvc.perform(put("/api/medicamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Medicamentos in the database
        List<Medicamentos> medicamentosList = medicamentosRepository.findAll();
        assertThat(medicamentosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicamentos() throws Exception {
        // Initialize the database
        medicamentosRepository.saveAndFlush(medicamentos);

        int databaseSizeBeforeDelete = medicamentosRepository.findAll().size();

        // Delete the medicamentos
        restMedicamentosMockMvc.perform(delete("/api/medicamentos/{id}", medicamentos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicamentos> medicamentosList = medicamentosRepository.findAll();
        assertThat(medicamentosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
