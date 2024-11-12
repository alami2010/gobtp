package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.Materiau} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MateriauDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private Instant date;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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
        if (!(o instanceof MateriauDTO)) {
            return false;
        }

        MateriauDTO materiauDTO = (MateriauDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, materiauDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriauDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", chantier=" + getChantier() +
            "}";
    }
}
