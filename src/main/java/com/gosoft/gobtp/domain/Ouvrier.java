package com.gosoft.gobtp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gosoft.gobtp.domain.enumeration.TypeOuvrier;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Ouvrier.
 */
@Document(collection = "ouvrier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ouvrier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("type")
    private TypeOuvrier type;

    @DBRef
    @Field("internalUser")
    private User internalUser;

    @DBRef
    @Field("ouvrier")
    @JsonIgnoreProperties(value = { "chantier", "ouvrier" }, allowSetters = true)
    private Set<HoraireTravail> ouvriers = new HashSet<>();

    @DBRef
    @Field("chantiers")
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
    private Set<Chantier> chantiers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Ouvrier id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ouvrier name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOuvrier getType() {
        return this.type;
    }

    public Ouvrier type(TypeOuvrier type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeOuvrier type) {
        this.type = type;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Ouvrier internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<HoraireTravail> getOuvriers() {
        return this.ouvriers;
    }

    public void setOuvriers(Set<HoraireTravail> horaireTravails) {
        if (this.ouvriers != null) {
            this.ouvriers.forEach(i -> i.setOuvrier(null));
        }
        if (horaireTravails != null) {
            horaireTravails.forEach(i -> i.setOuvrier(this));
        }
        this.ouvriers = horaireTravails;
    }

    public Ouvrier ouvriers(Set<HoraireTravail> horaireTravails) {
        this.setOuvriers(horaireTravails);
        return this;
    }

    public Ouvrier addOuvrier(HoraireTravail horaireTravail) {
        this.ouvriers.add(horaireTravail);
        horaireTravail.setOuvrier(this);
        return this;
    }

    public Ouvrier removeOuvrier(HoraireTravail horaireTravail) {
        this.ouvriers.remove(horaireTravail);
        horaireTravail.setOuvrier(null);
        return this;
    }

    public Set<Chantier> getChantiers() {
        return this.chantiers;
    }

    public void setChantiers(Set<Chantier> chantiers) {
        if (this.chantiers != null) {
            this.chantiers.forEach(i -> i.removeOuvriers(this));
        }
        if (chantiers != null) {
            chantiers.forEach(i -> i.addOuvriers(this));
        }
        this.chantiers = chantiers;
    }

    public Ouvrier chantiers(Set<Chantier> chantiers) {
        this.setChantiers(chantiers);
        return this;
    }

    public Ouvrier addChantiers(Chantier chantier) {
        this.chantiers.add(chantier);
        chantier.getOuvriers().add(this);
        return this;
    }

    public Ouvrier removeChantiers(Chantier chantier) {
        this.chantiers.remove(chantier);
        chantier.getOuvriers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ouvrier)) {
            return false;
        }
        return getId() != null && getId().equals(((Ouvrier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ouvrier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
