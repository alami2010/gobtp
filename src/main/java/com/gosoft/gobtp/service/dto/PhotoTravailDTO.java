package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.PhotoTravail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoTravailDTO implements Serializable {

    private String id;

    private String description;

    @NotNull
    private Instant date;

    @NotNull
    private String photo;

    private ChantierDTO chantier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
        if (!(o instanceof PhotoTravailDTO)) {
            return false;
        }

        PhotoTravailDTO photoTravailDTO = (PhotoTravailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, photoTravailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoTravailDTO{" +
            "id='" + getId() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", chantier=" + getChantier() +
            "}";
    }
}
