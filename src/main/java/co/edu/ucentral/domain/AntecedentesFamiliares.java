package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AntecedentesFamiliares.
 */
@Entity
@Table(name = "antecedentes_familiares")
public class AntecedentesFamiliares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "madre")
    private String madre;

    @Column(name = "padre")
    private String padre;

    @Column(name = "abuelo_paterno")
    private String abueloPaterno;

    @Column(name = "abuelo_materno")
    private String abueloMaterno;

    @Column(name = "abuela_paterna")
    private String abuelaPaterna;

    @Column(name = "abuela_materna")
    private String abuelaMaterna;

    @OneToOne(mappedBy = "antecedentesFamiliares")
    @JsonIgnore
    private HistoriaUsuario historiaUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMadre() {
        return madre;
    }

    public AntecedentesFamiliares madre(String madre) {
        this.madre = madre;
        return this;
    }

    public void setMadre(String madre) {
        this.madre = madre;
    }

    public String getPadre() {
        return padre;
    }

    public AntecedentesFamiliares padre(String padre) {
        this.padre = padre;
        return this;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getAbueloPaterno() {
        return abueloPaterno;
    }

    public AntecedentesFamiliares abueloPaterno(String abueloPaterno) {
        this.abueloPaterno = abueloPaterno;
        return this;
    }

    public void setAbueloPaterno(String abueloPaterno) {
        this.abueloPaterno = abueloPaterno;
    }

    public String getAbueloMaterno() {
        return abueloMaterno;
    }

    public AntecedentesFamiliares abueloMaterno(String abueloMaterno) {
        this.abueloMaterno = abueloMaterno;
        return this;
    }

    public void setAbueloMaterno(String abueloMaterno) {
        this.abueloMaterno = abueloMaterno;
    }

    public String getAbuelaPaterna() {
        return abuelaPaterna;
    }

    public AntecedentesFamiliares abuelaPaterna(String abuelaPaterna) {
        this.abuelaPaterna = abuelaPaterna;
        return this;
    }

    public void setAbuelaPaterna(String abuelaPaterna) {
        this.abuelaPaterna = abuelaPaterna;
    }

    public String getAbuelaMaterna() {
        return abuelaMaterna;
    }

    public AntecedentesFamiliares abuelaMaterna(String abuelaMaterna) {
        this.abuelaMaterna = abuelaMaterna;
        return this;
    }

    public void setAbuelaMaterna(String abuelaMaterna) {
        this.abuelaMaterna = abuelaMaterna;
    }

    public HistoriaUsuario getHistoriaUsuario() {
        return historiaUsuario;
    }

    public AntecedentesFamiliares historiaUsuario(HistoriaUsuario historiaUsuario) {
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
        if (!(o instanceof AntecedentesFamiliares)) {
            return false;
        }
        return id != null && id.equals(((AntecedentesFamiliares) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AntecedentesFamiliares{" +
            "id=" + getId() +
            ", madre='" + getMadre() + "'" +
            ", padre='" + getPadre() + "'" +
            ", abueloPaterno='" + getAbueloPaterno() + "'" +
            ", abueloMaterno='" + getAbueloMaterno() + "'" +
            ", abuelaPaterna='" + getAbuelaPaterna() + "'" +
            ", abuelaMaterna='" + getAbuelaMaterna() + "'" +
            "}";
    }
}
