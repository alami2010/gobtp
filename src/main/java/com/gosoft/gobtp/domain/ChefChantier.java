package com.gosoft.gobtp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ChefChantier.
 */
@Document(collection = "chef_chantier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChefChantier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("email")
    private String email;

    @Field("phone")
    private String phone;

    @DBRef
    @Field("internalUser")
    private User internalUser;

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

    public ChefChantier id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ChefChantier name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public ChefChantier email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public ChefChantier phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ChefChantier internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<Chantier> getChantiers() {
        return this.chantiers;
    }

    public void setChantiers(Set<Chantier> chantiers) {
        if (this.chantiers != null) {
            this.chantiers.forEach(i -> i.setChefChantier(null));
        }
        if (chantiers != null) {
            chantiers.forEach(i -> i.setChefChantier(this));
        }
        this.chantiers = chantiers;
    }

    public ChefChantier chantiers(Set<Chantier> chantiers) {
        this.setChantiers(chantiers);
        return this;
    }

    public ChefChantier addChantiers(Chantier chantier) {
        this.chantiers.add(chantier);
        chantier.setChefChantier(this);
        return this;
    }

    public ChefChantier removeChantiers(Chantier chantier) {
        this.chantiers.remove(chantier);
        chantier.setChefChantier(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChefChantier)) {
            return false;
        }
        return getId() != null && getId().equals(((ChefChantier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChefChantier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
