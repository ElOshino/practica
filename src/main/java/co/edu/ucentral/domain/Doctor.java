package co.edu.ucentral.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doctor.
 */
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "cedula")
    private Integer cedula;

    @OneToMany(mappedBy = "doctor")
    private Set<Paciente> pacientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Doctor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Doctor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getCedula() {
        return cedula;
    }

    public Doctor cedula(Integer cedula) {
        this.cedula = cedula;
        return this;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Doctor pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Doctor addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setDoctor(this);
        return this;
    }

    public Doctor removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setDoctor(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctor)) {
            return false;
        }
        return id != null && id.equals(((Doctor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Doctor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", cedula=" + getCedula() +
            "}";
    }
}
