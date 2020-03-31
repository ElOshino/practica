package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Medicamentos.
 */
@Entity
@Table(name = "medicamentos")
public class Medicamentos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "posologia")
    private String posologia;

    @Column(name = "tiempo_tomando")
    private String tiempoTomando;

    @OneToOne(mappedBy = "medicamento")
    @JsonIgnore
    private HistoriaUsuario historiaUsuario;

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

    public Medicamentos nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosologia() {
        return posologia;
    }

    public Medicamentos posologia(String posologia) {
        this.posologia = posologia;
        return this;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getTiempoTomando() {
        return tiempoTomando;
    }

    public Medicamentos tiempoTomando(String tiempoTomando) {
        this.tiempoTomando = tiempoTomando;
        return this;
    }

    public void setTiempoTomando(String tiempoTomando) {
        this.tiempoTomando = tiempoTomando;
    }

    public HistoriaUsuario getHistoriaUsuario() {
        return historiaUsuario;
    }

    public Medicamentos historiaUsuario(HistoriaUsuario historiaUsuario) {
        this.historiaUsuario = historiaUsuario;
        return this;
    }

    public void setHistoriaUsuario(HistoriaUsuario historiaUsuario) {
        this.historiaUsuario = historiaUsuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicamentos)) {
            return false;
        }
        return id != null && id.equals(((Medicamentos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Medicamentos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", posologia='" + getPosologia() + "'" +
            ", tiempoTomando='" + getTiempoTomando() + "'" +
            "}";
    }
}
