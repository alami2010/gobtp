package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.DocumentFinancier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentFinancierDTO implements Serializable {

    private String id;

    @NotNull
    private String nom;

    @NotNull
    private String file;

    private ChantierDTO chantier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ChantierDTO getChantier() {
        return chantier;
    }

    public void setChantier(ChantierDTO chantier) {
        this.chantier = chantier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentFinancierDTO)) {
            return false;
        }

        DocumentFinancierDTO documentFinancierDTO = (DocumentFinancierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentFinancierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentFinancierDTO{" +
            "id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", file='" + getFile() + "'" +
            ", chantier=" + getChantier() +
            "}";
    }
}
