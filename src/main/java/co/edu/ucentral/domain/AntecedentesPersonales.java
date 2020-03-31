package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AntecedentesPersonales.
 */
@Entity
@Table(name = "antecedentes_personales")
public class AntecedentesPersonales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cirugias")
    private String cirugias;

    @Column(name = "traumatismos")
    private String traumatismos;

    @Column(name = "transfusion_sanguinea")
    private String transfusionSanguinea;

    @Column(name = "alergias")
    private String alergias;

    @OneToOne(mappedBy = "antecedentesPersonales")
    @JsonIgnore
    private HistoriaUsuario historiaUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCirugias() {
        return cirugias;
    }

    public AntecedentesPersonales cirugias(String cirugias) {
        this.cirugias = cirugias;
        return this;
    }

    public void setCirugias(String cirugias) {
        this.cirugias = cirugias;
    }

    public String getTraumatismos() {
        return traumatismos;
    }

    public AntecedentesPersonales traumatismos(String traumatismos) {
        this.traumatismos = traumatismos;
        return this;
    }

    public void setTraumatismos(String traumatismos) {
        this.traumatismos = traumatismos;
    }

    public String getTransfusionSanguinea() {
        return transfusionSanguinea;
    }

    public AntecedentesPersonales transfusionSanguinea(String transfusionSanguinea) {
        this.transfusionSanguinea = transfusionSanguinea;
        return this;
    }

    public void setTransfusionSanguinea(String transfusionSanguinea) {
        this.transfusionSanguinea = transfusionSanguinea;
    }

    public String getAlergias() {
        return alergias;
    }

    public AntecedentesPersonales alergias(String alergias) {
        this.alergias = alergias;
        return this;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public HistoriaUsuario getHistoriaUsuario() {
        return historiaUsuario;
    }

    public AntecedentesPersonales historiaUsuario(HistoriaUsuario historiaUsuario) {
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
        if (!(o instanceof AntecedentesPersonales)) {
            return false;
        }
        return id != null && id.equals(((AntecedentesPersonales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AntecedentesPersonales{" +
            "id=" + getId() +
            ", cirugias='" + getCirugias() + "'" +
            ", traumatismos='" + getTraumatismos() + "'" +
            ", transfusionSanguinea='" + getTransfusionSanguinea() + "'" +
            ", alergias='" + getAlergias() + "'" +
            "}";
    }
}
