package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ExamenFisico.
 */
@Entity
@Table(name = "examen_fisico")
public class ExamenFisico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "peso")
    private Float peso;

    @Column(name = "talla")
    private Float talla;

    @Column(name = "indice_masa_corporal")
    private Float indiceMasaCorporal;

    @OneToOne
    @JoinColumn(unique = true)
    private SignosVitales signosVitales;

    @OneToOne(mappedBy = "examenFisico")
    @JsonIgnore
    private HistoriaUsuario historiaUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPeso() {
        return peso;
    }

    public ExamenFisico peso(Float peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getTalla() {
        return talla;
    }

    public ExamenFisico talla(Float talla) {
        this.talla = talla;
        return this;
    }

    public void setTalla(Float talla) {
        this.talla = talla;
    }

    public Float getIndiceMasaCorporal() {
        return indiceMasaCorporal;
    }

    public ExamenFisico indiceMasaCorporal(Float indiceMasaCorporal) {
        this.indiceMasaCorporal = indiceMasaCorporal;
        return this;
    }

    public void setIndiceMasaCorporal(Float indiceMasaCorporal) {
        this.indiceMasaCorporal = indiceMasaCorporal;
    }

    public SignosVitales getSignosVitales() {
        return signosVitales;
    }

    public ExamenFisico signosVitales(SignosVitales signosVitales) {
        this.signosVitales = signosVitales;
        return this;
    }

    public void setSignosVitales(SignosVitales signosVitales) {
        this.signosVitales = signosVitales;
    }

    public HistoriaUsuario getHistoriaUsuario() {
        return historiaUsuario;
    }

    public ExamenFisico historiaUsuario(HistoriaUsuario historiaUsuario) {
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
        if (!(o instanceof ExamenFisico)) {
            return false;
        }
        return id != null && id.equals(((ExamenFisico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExamenFisico{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", talla=" + getTalla() +
            ", indiceMasaCorporal=" + getIndiceMasaCorporal() +
            "}";
    }
}
