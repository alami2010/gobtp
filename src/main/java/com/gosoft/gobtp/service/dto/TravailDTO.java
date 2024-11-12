package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.Travail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TravailDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String label;

    private ChantierDTO chantier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        if (!(o instanceof TravailDTO)) {
            return false;
        }

        TravailDTO travailDTO = (TravailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, travailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravailDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            ", chantier=" + getChantier() +
            "}";
    }
}
