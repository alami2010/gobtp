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
 * A Chantier.
 */
@Document(collection = "chantier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Chantier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("adresse")
    private String adresse;

    @Field("desc")
    private String desc;

    @NotNull
    @Field("status")
    private String status;

    @NotNull
    @Field("date")
    private Instant date;

    @DBRef
    @Field("materiaux")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<Materiau> materiauxes = new HashSet<>();

    @DBRef
    @Field("travaux")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<Travail> travauxes = new HashSet<>();

    @DBRef
    @Field("plans")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<Plan> plans = new HashSet<>();

    @DBRef
    @Field("travauxSupplementaires")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<TravailSupplementaire> travauxSupplementaires = new HashSet<>();

    @DBRef
    @Field("documentsFinanciers")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<DocumentFinancier> documentsFinanciers = new HashSet<>();

    @DBRef
    @Field("photosTravail")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<PhotoTravail> photosTravails = new HashSet<>();

    @DBRef
    @Field("horairesTravail")
    @JsonIgnoreProperties(value = { "chantier", "ouvrier" }, allowSetters = true)
    private Set<HoraireTravail> horairesTravails = new HashSet<>();

    @DBRef
    @Field("materiauManquant")
    @JsonIgnoreProperties(value = { "chantier" }, allowSetters = true)
    private Set<MateriauManquant> materiauManquants = new HashSet<>();

    @DBRef
    @Field("ouvriers")
    @JsonIgnoreProperties(value = { "internalUser", "ouvriers", "chantiers" }, allowSetters = true)
    private Set<Ouvrier> ouvriers = new HashSet<>();

    @DBRef
    @Field("chefChantier")
    @JsonIgnoreProperties(value = { "internalUser", "chantiers" }, allowSetters = true)
    private ChefChantier chefChantier;

    @DBRef
    @Field("client")
    @JsonIgnoreProperties(value = { "internalUser", "chantiers" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Chantier id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Chantier name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Chantier adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDesc() {
        return this.desc;
    }

    public Chantier desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return this.status;
    }

    public Chantier status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDate() {
        return this.date;
    }

    public Chantier date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Set<Materiau> getMateriauxes() {
        return this.materiauxes;
    }

    public void setMateriauxes(Set<Materiau> materiaus) {
        if (this.materiauxes != null) {
            this.materiauxes.forEach(i -> i.setChantier(null));
        }
        if (materiaus != null) {
            materiaus.forEach(i -> i.setChantier(this));
        }
        this.materiauxes = materiaus;
    }

    public Chantier materiauxes(Set<Materiau> materiaus) {
        this.setMateriauxes(materiaus);
        return this;
    }

    public Chantier addMateriaux(Materiau materiau) {
        this.materiauxes.add(materiau);
        materiau.setChantier(this);
        return this;
    }

    public Chantier removeMateriaux(Materiau materiau) {
        this.materiauxes.remove(materiau);
        materiau.setChantier(null);
        return this;
    }

    public Set<Travail> getTravauxes() {
        return this.travauxes;
    }

    public void setTravauxes(Set<Travail> travails) {
        if (this.travauxes != null) {
            this.travauxes.forEach(i -> i.setChantier(null));
        }
        if (travails != null) {
            travails.forEach(i -> i.setChantier(this));
        }
        this.travauxes = travails;
    }

    public Chantier travauxes(Set<Travail> travails) {
        this.setTravauxes(travails);
        return this;
    }

    public Chantier addTravaux(Travail travail) {
        this.travauxes.add(travail);
        travail.setChantier(this);
        return this;
    }

    public Chantier removeTravaux(Travail travail) {
        this.travauxes.remove(travail);
        travail.setChantier(null);
        return this;
    }

    public Set<Plan> getPlans() {
        return this.plans;
    }

    public void setPlans(Set<Plan> plans) {
        if (this.plans != null) {
            this.plans.forEach(i -> i.setChantier(null));
        }
        if (plans != null) {
            plans.forEach(i -> i.setChantier(this));
        }
        this.plans = plans;
    }

    public Chantier plans(Set<Plan> plans) {
        this.setPlans(plans);
        return this;
    }

    public Chantier addPlans(Plan plan) {
        this.plans.add(plan);
        plan.setChantier(this);
        return this;
    }

    public Chantier removePlans(Plan plan) {
        this.plans.remove(plan);
        plan.setChantier(null);
        return this;
    }

    public Set<TravailSupplementaire> getTravauxSupplementaires() {
        return this.travauxSupplementaires;
    }

    public void setTravauxSupplementaires(Set<TravailSupplementaire> travailSupplementaires) {
        if (this.travauxSupplementaires != null) {
            this.travauxSupplementaires.forEach(i -> i.setChantier(null));
        }
        if (travailSupplementaires != null) {
            travailSupplementaires.forEach(i -> i.setChantier(this));
        }
        this.travauxSupplementaires = travailSupplementaires;
    }

    public Chantier travauxSupplementaires(Set<TravailSupplementaire> travailSupplementaires) {
        this.setTravauxSupplementaires(travailSupplementaires);
        return this;
    }

    public Chantier addTravauxSupplementaires(TravailSupplementaire travailSupplementaire) {
        this.travauxSupplementaires.add(travailSupplementaire);
        travailSupplementaire.setChantier(this);
        return this;
    }

    public Chantier removeTravauxSupplementaires(TravailSupplementaire travailSupplementaire) {
        this.travauxSupplementaires.remove(travailSupplementaire);
        travailSupplementaire.setChantier(null);
        return this;
    }

    public Set<DocumentFinancier> getDocumentsFinanciers() {
        return this.documentsFinanciers;
    }

    public void setDocumentsFinanciers(Set<DocumentFinancier> documentFinanciers) {
        if (this.documentsFinanciers != null) {
            this.documentsFinanciers.forEach(i -> i.setChantier(null));
        }
        if (documentFinanciers != null) {
            documentFinanciers.forEach(i -> i.setChantier(this));
        }
        this.documentsFinanciers = documentFinanciers;
    }

    public Chantier documentsFinanciers(Set<DocumentFinancier> documentFinanciers) {
        this.setDocumentsFinanciers(documentFinanciers);
        return this;
    }

    public Chantier addDocumentsFinanciers(DocumentFinancier documentFinancier) {
        this.documentsFinanciers.add(documentFinancier);
        documentFinancier.setChantier(this);
        return this;
    }

    public Chantier removeDocumentsFinanciers(DocumentFinancier documentFinancier) {
        this.documentsFinanciers.remove(documentFinancier);
        documentFinancier.setChantier(null);
        return this;
    }

    public Set<PhotoTravail> getPhotosTravails() {
        return this.photosTravails;
    }

    public void setPhotosTravails(Set<PhotoTravail> photoTravails) {
        if (this.photosTravails != null) {
            this.photosTravails.forEach(i -> i.setChantier(null));
        }
        if (photoTravails != null) {
            photoTravails.forEach(i -> i.setChantier(this));
        }
        this.photosTravails = photoTravails;
    }

    public Chantier photosTravails(Set<PhotoTravail> photoTravails) {
        this.setPhotosTravails(photoTravails);
        return this;
    }

    public Chantier addPhotosTravail(PhotoTravail photoTravail) {
        this.photosTravails.add(photoTravail);
        photoTravail.setChantier(this);
        return this;
    }

    public Chantier removePhotosTravail(PhotoTravail photoTravail) {
        this.photosTravails.remove(photoTravail);
        photoTravail.setChantier(null);
        return this;
    }

    public Set<HoraireTravail> getHorairesTravails() {
        return this.horairesTravails;
    }

    public void setHorairesTravails(Set<HoraireTravail> horaireTravails) {
        if (this.horairesTravails != null) {
            this.horairesTravails.forEach(i -> i.setChantier(null));
        }
        if (horaireTravails != null) {
            horaireTravails.forEach(i -> i.setChantier(this));
        }
        this.horairesTravails = horaireTravails;
    }

    public Chantier horairesTravails(Set<HoraireTravail> horaireTravails) {
        this.setHorairesTravails(horaireTravails);
        return this;
    }

    public Chantier addHorairesTravail(HoraireTravail horaireTravail) {
        this.horairesTravails.add(horaireTravail);
        horaireTravail.setChantier(this);
        return this;
    }

    public Chantier removeHorairesTravail(HoraireTravail horaireTravail) {
        this.horairesTravails.remove(horaireTravail);
        horaireTravail.setChantier(null);
        return this;
    }

    public Set<MateriauManquant> getMateriauManquants() {
        return this.materiauManquants;
    }

    public void setMateriauManquants(Set<MateriauManquant> materiauManquants) {
        if (this.materiauManquants != null) {
            this.materiauManquants.forEach(i -> i.setChantier(null));
        }
        if (materiauManquants != null) {
            materiauManquants.forEach(i -> i.setChantier(this));
        }
        this.materiauManquants = materiauManquants;
    }

    public Chantier materiauManquants(Set<MateriauManquant> materiauManquants) {
        this.setMateriauManquants(materiauManquants);
        return this;
    }

    public Chantier addMateriauManquant(MateriauManquant materiauManquant) {
        this.materiauManquants.add(materiauManquant);
        materiauManquant.setChantier(this);
        return this;
    }

    public Chantier removeMateriauManquant(MateriauManquant materiauManquant) {
        this.materiauManquants.remove(materiauManquant);
        materiauManquant.setChantier(null);
        return this;
    }

    public Set<Ouvrier> getOuvriers() {
        return this.ouvriers;
    }

    public void setOuvriers(Set<Ouvrier> ouvriers) {
        this.ouvriers = ouvriers;
    }

    public Chantier ouvriers(Set<Ouvrier> ouvriers) {
        this.setOuvriers(ouvriers);
        return this;
    }

    public Chantier addOuvriers(Ouvrier ouvrier) {
        this.ouvriers.add(ouvrier);
        return this;
    }

    public Chantier removeOuvriers(Ouvrier ouvrier) {
        this.ouvriers.remove(ouvrier);
        return this;
    }

    public ChefChantier getChefChantier() {
        return this.chefChantier;
    }

    public void setChefChantier(ChefChantier chefChantier) {
        this.chefChantier = chefChantier;
    }

    public Chantier chefChantier(ChefChantier chefChantier) {
        this.setChefChantier(chefChantier);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Chantier client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chantier)) {
            return false;
        }
        return getId() != null && getId().equals(((Chantier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chantier{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", desc='" + getDesc() + "'" +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
