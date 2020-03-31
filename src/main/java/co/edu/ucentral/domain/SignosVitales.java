package co.edu.ucentral.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SignosVitales.
 */
@Entity
@Table(name = "signos_vitales")
public class SignosVitales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "presion_arterial")
    private Integer presionArterial;

    @Column(name = "respiracion")
    private Integer respiracion;

    @Column(name = "pulso")
    private Integer pulso;

    @Column(name = "temperatura")
    private Float temperatura;

    @OneToOne(mappedBy = "signosVitales")
    @JsonIgnore
    private ExamenFisico historiaUsuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPresionArterial() {
        return presionArterial;
    }

    public SignosVitales presionArterial(Integer presionArterial) {
        this.presionArterial = presionArterial;
        return this;
    }

    public void setPresionArterial(Integer presionArterial) {
        this.presionArterial = presionArterial;
    }

    public Integer getRespiracion() {
        return respiracion;
    }

    public SignosVitales respiracion(Integer respiracion) {
        this.respiracion = respiracion;
        return this;
    }

    public void setRespiracion(Integer respiracion) {
        this.respiracion = respiracion;
    }

    public Integer getPulso() {
        return pulso;
    }

    public SignosVitales pulso(Integer pulso) {
        this.pulso = pulso;
        return this;
    }

    public void setPulso(Integer pulso) {
        this.pulso = pulso;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public SignosVitales temperatura(Float temperatura) {
        this.temperatura = temperatura;
        return this;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public ExamenFisico getHistoriaUsuario() {
        return historiaUsuario;
    }

    public SignosVitales historiaUsuario(ExamenFisico examenFisico) {
        this.historiaUsuario = examenFisico;
        return this;
    }

    public void setHistoriaUsuario(ExamenFisico examenFisico) {
        this.historiaUsuario = examenFisico;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignosVitales)) {
            return false;
        }
        return id != null && id.equals(((SignosVitales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SignosVitales{" +
            "id=" + getId() +
            ", presionArterial=" + getPresionArterial() +
            ", respiracion=" + getRespiracion() +
            ", pulso=" + getPulso() +
            ", temperatura=" + getTemperatura() +
            "}";
    }
}
