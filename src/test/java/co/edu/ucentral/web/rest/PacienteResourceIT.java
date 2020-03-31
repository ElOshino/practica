package co.edu.ucentral.web.rest;

import co.edu.ucentral.PracticaApp;
import co.edu.ucentral.domain.Paciente;
import co.edu.ucentral.repository.PacienteRepository;

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
 * Integration tests for the {@link PacienteResource} REST controller.
 */
@SpringBootTest(classes = PracticaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PacienteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CEDULA = 1;
    private static final Integer UPDATED_CEDULA = 2;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SANGRE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SANGRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .cedula(DEFAULT_CEDULA)
            .direccion(DEFAULT_DIRECCION)
            .sexo(DEFAULT_SEXO)
            .tipoSangre(DEFAULT_TIPO_SANGRE)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
        return paciente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createUpdatedEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .cedula(UPDATED_CEDULA)
            .direccion(UPDATED_DIRECCION)
            .sexo(UPDATED_SEXO)
            .tipoSangre(UPDATED_TIPO_SANGRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        return paciente;
    }

    @BeforeEach
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPaciente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPaciente.getCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testPaciente.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPaciente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPaciente.getTipoSangre()).isEqualTo(DEFAULT_TIPO_SANGRE);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].tipoSangre").value(hasItem(DEFAULT_TIPO_SANGRE)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.tipoSangre").value(DEFAULT_TIPO_SANGRE))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .cedula(UPDATED_CEDULA)
            .direccion(UPDATED_DIRECCION)
            .sexo(UPDATED_SEXO)
            .tipoSangre(UPDATED_TIPO_SANGRE)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaciente)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPaciente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPaciente.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testPaciente.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPaciente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPaciente.getTipoSangre()).isEqualTo(UPDATED_TIPO_SANGRE);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Delete the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
