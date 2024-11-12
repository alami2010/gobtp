package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.MateriauManquant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MateriauManquantDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Integer quantity;

    @NotNull
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        if (!(o instanceof MateriauManquantDTO)) {
            return false;
        }

        MateriauManquantDTO materiauManquantDTO = (MateriauManquantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, materiauManquantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriauManquantDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", date='" + getDate() + "'" +
            ", chantier=" + getChantier() +
            "}";
    }
}
