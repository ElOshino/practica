package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Consulta.
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sintomas")
    private String sintomas;

    @Column(name = "signos")
    private String signos;

    @Column(name = "tiempo_sintomas")
    private String tiempoSintomas;

    @OneToOne(mappedBy = "consulta")
    @JsonIgnore
    private HistoriaUsuario historiaUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSintomas() {
        return sintomas;
    }

    public Consulta sintomas(String sintomas) {
        this.sintomas = sintomas;
        return this;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getSignos() {
        return signos;
    }

    public Consulta signos(String signos) {
        this.signos = signos;
        return this;
    }

    public void setSignos(String signos) {
        this.signos = signos;
    }

    public String getTiempoSintomas() {
        return tiempoSintomas;
    }

    public Consulta tiempoSintomas(String tiempoSintomas) {
        this.tiempoSintomas = tiempoSintomas;
        return this;
    }

    public void setTiempoSintomas(String tiempoSintomas) {
        this.tiempoSintomas = tiempoSintomas;
    }

    public HistoriaUsuario getHistoriaUsuario() {
        return historiaUsuario;
    }

    public Consulta historiaUsuario(HistoriaUsuario historiaUsuario) {
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
        if (!(o instanceof Consulta)) {
            return false;
        }
        return id != null && id.equals(((Consulta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Consulta{" +
            "id=" + getId() +
            ", sintomas='" + getSintomas() + "'" +
            ", signos='" + getSignos() + "'" +
            ", tiempoSintomas='" + getTiempoSintomas() + "'" +
            "}";
    }
}
