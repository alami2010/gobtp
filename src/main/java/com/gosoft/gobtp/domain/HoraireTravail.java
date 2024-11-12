package com.gosoft.gobtp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A HoraireTravail.
 */
@Document(collection = "horaire_travail")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HoraireTravail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("debut_matin")
    private Instant debutMatin;

    @Field("fin_matin")
    private Instant finMatin;

    @Field("debut_soir")
    private Instant debutSoir;

    @Field("fin_soir")
    private Instant finSoir;

    @NotNull
    @Field("date")
    private Instant date;

    @NotNull
    @Field("jour")
    private String jour;

    @DBRef
    @Field("chantier")
    @JsonIgnoreProperties(
        value = {
            "materiauxes",
            "travauxes",
            "plans",
            "travauxSupplementaires",
            "documentsFinanciers",
            "photosTravails",
            "horairesTravails",
            "materiauManquants",
            "ouvriers",
            "chefChantier",
            "client",
        },
        allowSetters = true
    )
    private Chantier chantier;

    @DBRef
    @Field("ouvrier")
    @JsonIgnoreProperties(value = { "internalUser", "ouvriers", "chantiers" }, allowSetters = true)
    private Ouvrier ouvrier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public HoraireTravail id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDebutMatin() {
        return this.debutMatin;
    }

    public HoraireTravail debutMatin(Instant debutMatin) {
        this.setDebutMatin(debutMatin);
        return this;
    }

    public void setDebutMatin(Instant debutMatin) {
        this.debutMatin = debutMatin;
    }

    public Instant getFinMatin() {
        return this.finMatin;
    }

    public HoraireTravail finMatin(Instant finMatin) {
        this.setFinMatin(finMatin);
        return this;
    }

    public void setFinMatin(Instant finMatin) {
        this.finMatin = finMatin;
    }

    public Instant getDebutSoir() {
        return this.debutSoir;
    }

    public HoraireTravail debutSoir(Instant debutSoir) {
        this.setDebutSoir(debutSoir);
        return this;
    }

    public void setDebutSoir(Instant debutSoir) {
        this.debutSoir = debutSoir;
    }

    public Instant getFinSoir() {
        return this.finSoir;
    }

    public HoraireTravail finSoir(Instant finSoir) {
        this.setFinSoir(finSoir);
        return this;
    }

    public void setFinSoir(Instant finSoir) {
        this.finSoir = finSoir;
    }

    public Instant getDate() {
        return this.date;
    }

    public HoraireTravail date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getJour() {
        return this.jour;
    }

    public HoraireTravail jour(String jour) {
        this.setJour(jour);
        return this;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public Chantier getChantier() {
        return this.chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public HoraireTravail chantier(Chantier chantier) {
        this.setChantier(chantier);
        return this;
    }

    public Ouvrier getOuvrier() {
        return this.ouvrier;
    }

    public void setOuvrier(Ouvrier ouvrier) {
        this.ouvrier = ouvrier;
    }

    public HoraireTravail ouvrier(Ouvrier ouvrier) {
        this.setOuvrier(ouvrier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoraireTravail)) {
            return false;
        }
        return getId() != null && getId().equals(((HoraireTravail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoraireTravail{" +
            "id=" + getId() +
            ", debutMatin='" + getDebutMatin() + "'" +
            ", finMatin='" + getFinMatin() + "'" +
            ", debutSoir='" + getDebutSoir() + "'" +
            ", finSoir='" + getFinSoir() + "'" +
            ", date='" + getDate() + "'" +
            ", jour='" + getJour() + "'" +
            "}";
    }
}
