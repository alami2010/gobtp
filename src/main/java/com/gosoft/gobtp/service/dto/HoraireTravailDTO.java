package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.HoraireTravail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HoraireTravailDTO implements Serializable {

    private String id;

    private Instant debutMatin;

    private Instant finMatin;

    private Instant debutSoir;

    private Instant finSoir;

    @NotNull
    private Instant date;

    @NotNull
    private String jour;

    private ChantierDTO chantier;

    private OuvrierDTO ouvrier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDebutMatin() {
        return debutMatin;
    }

    public void setDebutMatin(Instant debutMatin) {
        this.debutMatin = debutMatin;
    }

    public Instant getFinMatin() {
        return finMatin;
    }

    public void setFinMatin(Instant finMatin) {
        this.finMatin = finMatin;
    }

    public Instant getDebutSoir() {
        return debutSoir;
    }

    public void setDebutSoir(Instant debutSoir) {
        this.debutSoir = debutSoir;
    }

    public Instant getFinSoir() {
        return finSoir;
    }

    public void setFinSoir(Instant finSoir) {
        this.finSoir = finSoir;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public ChantierDTO getChantier() {
        return chantier;
    }

    public void setChantier(ChantierDTO chantier) {
        this.chantier = chantier;
    }

    public OuvrierDTO getOuvrier() {
        return ouvrier;
    }

    public void setOuvrier(OuvrierDTO ouvrier) {
        this.ouvrier = ouvrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoraireTravailDTO)) {
            return false;
        }

        HoraireTravailDTO horaireTravailDTO = (HoraireTravailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, horaireTravailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HoraireTravailDTO{" +
            "id='" + getId() + "'" +
            ", debutMatin='" + getDebutMatin() + "'" +
            ", finMatin='" + getFinMatin() + "'" +
            ", debutSoir='" + getDebutSoir() + "'" +
            ", finSoir='" + getFinSoir() + "'" +
            ", date='" + getDate() + "'" +
            ", jour='" + getJour() + "'" +
            ", chantier=" + getChantier() +
            ", ouvrier=" + getOuvrier() +
            "}";
    }
}
