package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.HistoriaUsuario;
import co.edu.ucentral.repository.HistoriaUsuarioRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HistoriaUsuarioResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class HistoriaUsuarioResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HistoriaUsuarioRepository historiaUsuarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoriaUsuarioMockMvc;

    private HistoriaUsuario historiaUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriaUsuario createEntity(EntityManager em) {
        HistoriaUsuario historiaUsuario = new HistoriaUsuario()
            .fecha(DEFAULT_FECHA);
        return historiaUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriaUsuario createUpdatedEntity(EntityManager em) {
        HistoriaUsuario historiaUsuario = new HistoriaUsuario()
            .fecha(UPDATED_FECHA);
        return historiaUsuario;
    }

    @BeforeEach
    public void initTest() {
        historiaUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistoriaUsuario() throws Exception {
        int databaseSizeBeforeCreate = historiaUsuarioRepository.findAll().size();

        // Create the HistoriaUsuario
        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuario)))
            .andExpect(status().isCreated());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriaUsuario testHistoriaUsuario = historiaUsuarioList.get(historiaUsuarioList.size() - 1);
        assertThat(testHistoriaUsuario.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createHistoriaUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historiaUsuarioRepository.findAll().size();

        // Create the HistoriaUsuario with an existing ID
        historiaUsuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriaUsuarioMockMvc.perform(post("/api/historia-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHistoriaUsuarios() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        // Get all the historiaUsuarioList
        restHistoriaUsuarioMockMvc.perform(get("/api/historia-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiaUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getHistoriaUsuario() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        // Get the historiaUsuario
        restHistoriaUsuarioMockMvc.perform(get("/api/historia-usuarios/{id}", historiaUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historiaUsuario.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHistoriaUsuario() throws Exception {
        // Get the historiaUsuario
        restHistoriaUsuarioMockMvc.perform(get("/api/historia-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistoriaUsuario() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        int databaseSizeBeforeUpdate = historiaUsuarioRepository.findAll().size();

        // Update the historiaUsuario
        HistoriaUsuario updatedHistoriaUsuario = historiaUsuarioRepository.findById(historiaUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriaUsuario are not directly saved in db
        em.detach(updatedHistoriaUsuario);
        updatedHistoriaUsuario
            .fecha(UPDATED_FECHA);

        restHistoriaUsuarioMockMvc.perform(put("/api/historia-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistoriaUsuario)))
            .andExpect(status().isOk());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeUpdate);
        HistoriaUsuario testHistoriaUsuario = historiaUsuarioList.get(historiaUsuarioList.size() - 1);
        assertThat(testHistoriaUsuario.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingHistoriaUsuario() throws Exception {
        int databaseSizeBeforeUpdate = historiaUsuarioRepository.findAll().size();

        // Create the HistoriaUsuario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriaUsuarioMockMvc.perform(put("/api/historia-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historiaUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the HistoriaUsuario in the database
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistoriaUsuario() throws Exception {
        // Initialize the database
        historiaUsuarioRepository.saveAndFlush(historiaUsuario);

        int databaseSizeBeforeDelete = historiaUsuarioRepository.findAll().size();

        // Delete the historiaUsuario
        restHistoriaUsuarioMockMvc.perform(delete("/api/historia-usuarios/{id}", historiaUsuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoriaUsuario> historiaUsuarioList = historiaUsuarioRepository.findAll();
        assertThat(historiaUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
