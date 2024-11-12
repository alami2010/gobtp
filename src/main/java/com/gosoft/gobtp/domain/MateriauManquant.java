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
 * A MateriauManquant.
 */
@Document(collection = "materiau_manquant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MateriauManquant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("quantity")
    private Integer quantity;

    @NotNull
    @Field("date")
    private Instant date;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public MateriauManquant id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public MateriauManquant name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public MateriauManquant quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getDate() {
        return this.date;
    }

    public MateriauManquant date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Chantier getChantier() {
        return this.chantier;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public MateriauManquant chantier(Chantier chantier) {
        this.setChantier(chantier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MateriauManquant)) {
            return false;
        }
        return getId() != null && getId().equals(((MateriauManquant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriauManquant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
