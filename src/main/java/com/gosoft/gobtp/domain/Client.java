package com.gosoft.gobtp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Client.
 */
@Document(collection = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("is_professional")
    private Boolean isProfessional;

    @NotNull
    @Field("date")
    private Instant date;

    @Field("adresse")
    private String adresse;

    @Field("info")
    private String info;

    @DBRef
    @Field("internalUser")
    private User internalUser;

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
    private Set<Chantier> chantiers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Client id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Client name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsProfessional() {
        return this.isProfessional;
    }

    public Client isProfessional(Boolean isProfessional) {
        this.setIsProfessional(isProfessional);
        return this;
    }

    public void setIsProfessional(Boolean isProfessional) {
        this.isProfessional = isProfessional;
    }

    public Instant getDate() {
        return this.date;
    }

    public Client date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Client adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInfo() {
        return this.info;
    }

    public Client info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Client internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<Chantier> getChantiers() {
        return this.chantiers;
    }

    public void setChantiers(Set<Chantier> chantiers) {
        if (this.chantiers != null) {
            this.chantiers.forEach(i -> i.setClient(null));
        }
        if (chantiers != null) {
            chantiers.forEach(i -> i.setClient(this));
        }
        this.chantiers = chantiers;
    }

    public Client chantiers(Set<Chantier> chantiers) {
        this.setChantiers(chantiers);
        return this;
    }

    public Client addChantier(Chantier chantier) {
        this.chantiers.add(chantier);
        chantier.setClient(this);
        return this;
    }

    public Client removeChantier(Chantier chantier) {
        this.chantiers.remove(chantier);
        chantier.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return getId() != null && getId().equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isProfessional='" + getIsProfessional() + "'" +
            ", date='" + getDate() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
