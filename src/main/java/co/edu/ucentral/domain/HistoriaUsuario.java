package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A HistoriaUsuario.
 */
@Entity
@Table(name = "historia_usuario")
public class HistoriaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @OneToOne
    @JoinColumn(unique = true)
    private Consulta consulta;

    @OneToOne
    @JoinColumn(unique = true)
    private AntecedentesPersonales antecedentesPersonales;

    @OneToOne
    @JoinColumn(unique = true)
    private AntecedentesFamiliares antecedentesFamiliares;

    @OneToOne
    @JoinColumn(unique = true)
    private Medicamentos medicamento;

    @OneToOne
    @JoinColumn(unique = true)
    private ExamenFisico examenFisico;

    @OneToOne(mappedBy = "historiaUsuario")
    @JsonIgnore
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public HistoriaUsuario fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public HistoriaUsuario consulta(Consulta consulta) {
        this.consulta = consulta;
        return this;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public AntecedentesPersonales getAntecedentesPersonales() {
        return antecedentesPersonales;
    }

    public HistoriaUsuario antecedentesPersonales(AntecedentesPersonales antecedentesPersonales) {
        this.antecedentesPersonales = antecedentesPersonales;
        return this;
    }

    public void setAntecedentesPersonales(AntecedentesPersonales antecedentesPersonales) {
        this.antecedentesPersonales = antecedentesPersonales;
    }

    public AntecedentesFamiliares getAntecedentesFamiliares() {
        return antecedentesFamiliares;
    }

    public HistoriaUsuario antecedentesFamiliares(AntecedentesFamiliares antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
        return this;
    }

    public void setAntecedentesFamiliares(AntecedentesFamiliares antecedentesFamiliares) {
        this.antecedentesFamiliares = antecedentesFamiliares;
    }

    public Medicamentos getMedicamento() {
        return medicamento;
    }

    public HistoriaUsuario medicamento(Medicamentos medicamentos) {
        this.medicamento = medicamentos;
        return this;
    }

    public void setMedicamento(Medicamentos medicamentos) {
        this.medicamento = medicamentos;
    }

    public ExamenFisico getExamenFisico() {
        return examenFisico;
    }

    public HistoriaUsuario examenFisico(ExamenFisico examenFisico) {
        this.examenFisico = examenFisico;
        return this;
    }

    public void setExamenFisico(ExamenFisico examenFisico) {
        this.examenFisico = examenFisico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public HistoriaUsuario paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoriaUsuario)) {
            return false;
        }
        return id != null && id.equals(((HistoriaUsuario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HistoriaUsuario{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
