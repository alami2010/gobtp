package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.Chantier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChantierDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String adresse;

    private String desc;

    @NotNull
    private String status;

    @NotNull
    private Instant date;

    private Set<OuvrierDTO> ouvriers = new HashSet<>();

    private ChefChantierDTO chefChantier;

    private ClientDTO client;

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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<OuvrierDTO> getOuvriers() {
        return ouvriers;
    }

    public void setOuvriers(Set<OuvrierDTO> ouvriers) {
        this.ouvriers = ouvriers;
    }

    public ChefChantierDTO getChefChantier() {
        return chefChantier;
    }

    public void setChefChantier(ChefChantierDTO chefChantier) {
        this.chefChantier = chefChantier;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChantierDTO)) {
            return false;
        }

        ChantierDTO chantierDTO = (ChantierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chantierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChantierDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", desc='" + getDesc() + "'" +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            ", ouvriers=" + getOuvriers() +
            ", chefChantier=" + getChefChantier() +
            ", client=" + getClient() +
            "}";
    }
}
