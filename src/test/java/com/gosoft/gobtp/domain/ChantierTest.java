package com.gosoft.gobtp.domain;

import static com.gosoft.gobtp.domain.ChantierTestSamples.*;
import static com.gosoft.gobtp.domain.ChefChantierTestSamples.*;
import static com.gosoft.gobtp.domain.ClientTestSamples.*;
import static com.gosoft.gobtp.domain.DocumentFinancierTestSamples.*;
import static com.gosoft.gobtp.domain.HoraireTravailTestSamples.*;
import static com.gosoft.gobtp.domain.MateriauManquantTestSamples.*;
import static com.gosoft.gobtp.domain.MateriauTestSamples.*;
import static com.gosoft.gobtp.domain.OuvrierTestSamples.*;
import static com.gosoft.gobtp.domain.PhotoTravailTestSamples.*;
import static com.gosoft.gobtp.domain.PlanTestSamples.*;
import static com.gosoft.gobtp.domain.TravailSupplementaireTestSamples.*;
import static com.gosoft.gobtp.domain.TravailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gosoft.gobtp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChantierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chantier.class);
        Chantier chantier1 = getChantierSample1();
        Chantier chantier2 = new Chantier();
        assertThat(chantier1).isNotEqualTo(chantier2);

        chantier2.setId(chantier1.getId());
        assertThat(chantier1).isEqualTo(chantier2);

        chantier2 = getChantierSample2();
        assertThat(chantier1).isNotEqualTo(chantier2);
    }

    @Test
    void materiauxTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        Materiau materiauBack = getMateriauRandomSampleGenerator();

        chantier.addMateriaux(materiauBack);
        assertThat(chantier.getMateriauxes()).containsOnly(materiauBack);
        assertThat(materiauBack.getChantier()).isEqualTo(chantier);

        chantier.removeMateriaux(materiauBack);
        assertThat(chantier.getMateriauxes()).doesNotContain(materiauBack);
        assertThat(materiauBack.getChantier()).isNull();

        chantier.materiauxes(new HashSet<>(Set.of(materiauBack)));
        assertThat(chantier.getMateriauxes()).containsOnly(materiauBack);
        assertThat(materiauBack.getChantier()).isEqualTo(chantier);

        chantier.setMateriauxes(new HashSet<>());
        assertThat(chantier.getMateriauxes()).doesNotContain(materiauBack);
        assertThat(materiauBack.getChantier()).isNull();
    }

    @Test
    void travauxTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        Travail travailBack = getTravailRandomSampleGenerator();

        chantier.addTravaux(travailBack);
        assertThat(chantier.getTravauxes()).containsOnly(travailBack);
        assertThat(travailBack.getChantier()).isEqualTo(chantier);

        chantier.removeTravaux(travailBack);
        assertThat(chantier.getTravauxes()).doesNotContain(travailBack);
        assertThat(travailBack.getChantier()).isNull();

        chantier.travauxes(new HashSet<>(Set.of(travailBack)));
        assertThat(chantier.getTravauxes()).containsOnly(travailBack);
        assertThat(travailBack.getChantier()).isEqualTo(chantier);

        chantier.setTravauxes(new HashSet<>());
        assertThat(chantier.getTravauxes()).doesNotContain(travailBack);
        assertThat(travailBack.getChantier()).isNull();
    }

    @Test
    void plansTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        chantier.addPlans(planBack);
        assertThat(chantier.getPlans()).containsOnly(planBack);
        assertThat(planBack.getChantier()).isEqualTo(chantier);

        chantier.removePlans(planBack);
        assertThat(chantier.getPlans()).doesNotContain(planBack);
        assertThat(planBack.getChantier()).isNull();

        chantier.plans(new HashSet<>(Set.of(planBack)));
        assertThat(chantier.getPlans()).containsOnly(planBack);
        assertThat(planBack.getChantier()).isEqualTo(chantier);

        chantier.setPlans(new HashSet<>());
        assertThat(chantier.getPlans()).doesNotContain(planBack);
        assertThat(planBack.getChantier()).isNull();
    }

    @Test
    void travauxSupplementairesTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        TravailSupplementaire travailSupplementaireBack = getTravailSupplementaireRandomSampleGenerator();

        chantier.addTravauxSupplementaires(travailSupplementaireBack);
        assertThat(chantier.getTravauxSupplementaires()).containsOnly(travailSupplementaireBack);
        assertThat(travailSupplementaireBack.getChantier()).isEqualTo(chantier);

        chantier.removeTravauxSupplementaires(travailSupplementaireBack);
        assertThat(chantier.getTravauxSupplementaires()).doesNotContain(travailSupplementaireBack);
        assertThat(travailSupplementaireBack.getChantier()).isNull();

        chantier.travauxSupplementaires(new HashSet<>(Set.of(travailSupplementaireBack)));
        assertThat(chantier.getTravauxSupplementaires()).containsOnly(travailSupplementaireBack);
        assertThat(travailSupplementaireBack.getChantier()).isEqualTo(chantier);

        chantier.setTravauxSupplementaires(new HashSet<>());
        assertThat(chantier.getTravauxSupplementaires()).doesNotContain(travailSupplementaireBack);
        assertThat(travailSupplementaireBack.getChantier()).isNull();
    }

    @Test
    void documentsFinanciersTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        DocumentFinancier documentFinancierBack = getDocumentFinancierRandomSampleGenerator();

        chantier.addDocumentsFinanciers(documentFinancierBack);
        assertThat(chantier.getDocumentsFinanciers()).containsOnly(documentFinancierBack);
        assertThat(documentFinancierBack.getChantier()).isEqualTo(chantier);

        chantier.removeDocumentsFinanciers(documentFinancierBack);
        assertThat(chantier.getDocumentsFinanciers()).doesNotContain(documentFinancierBack);
        assertThat(documentFinancierBack.getChantier()).isNull();

        chantier.documentsFinanciers(new HashSet<>(Set.of(documentFinancierBack)));
        assertThat(chantier.getDocumentsFinanciers()).containsOnly(documentFinancierBack);
        assertThat(documentFinancierBack.getChantier()).isEqualTo(chantier);

        chantier.setDocumentsFinanciers(new HashSet<>());
        assertThat(chantier.getDocumentsFinanciers()).doesNotContain(documentFinancierBack);
        assertThat(documentFinancierBack.getChantier()).isNull();
    }

    @Test
    void photosTravailTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        PhotoTravail photoTravailBack = getPhotoTravailRandomSampleGenerator();

        chantier.addPhotosTravail(photoTravailBack);
        assertThat(chantier.getPhotosTravails()).containsOnly(photoTravailBack);
        assertThat(photoTravailBack.getChantier()).isEqualTo(chantier);

        chantier.removePhotosTravail(photoTravailBack);
        assertThat(chantier.getPhotosTravails()).doesNotContain(photoTravailBack);
        assertThat(photoTravailBack.getChantier()).isNull();

        chantier.photosTravails(new HashSet<>(Set.of(photoTravailBack)));
        assertThat(chantier.getPhotosTravails()).containsOnly(photoTravailBack);
        assertThat(photoTravailBack.getChantier()).isEqualTo(chantier);

        chantier.setPhotosTravails(new HashSet<>());
        assertThat(chantier.getPhotosTravails()).doesNotContain(photoTravailBack);
        assertThat(photoTravailBack.getChantier()).isNull();
    }

    @Test
    void horairesTravailTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        HoraireTravail horaireTravailBack = getHoraireTravailRandomSampleGenerator();

        chantier.addHorairesTravail(horaireTravailBack);
        assertThat(chantier.getHorairesTravails()).containsOnly(horaireTravailBack);
        assertThat(horaireTravailBack.getChantier()).isEqualTo(chantier);

        chantier.removeHorairesTravail(horaireTravailBack);
        assertThat(chantier.getHorairesTravails()).doesNotContain(horaireTravailBack);
        assertThat(horaireTravailBack.getChantier()).isNull();

        chantier.horairesTravails(new HashSet<>(Set.of(horaireTravailBack)));
        assertThat(chantier.getHorairesTravails()).containsOnly(horaireTravailBack);
        assertThat(horaireTravailBack.getChantier()).isEqualTo(chantier);

        chantier.setHorairesTravails(new HashSet<>());
        assertThat(chantier.getHorairesTravails()).doesNotContain(horaireTravailBack);
        assertThat(horaireTravailBack.getChantier()).isNull();
    }

    @Test
    void materiauManquantTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        MateriauManquant materiauManquantBack = getMateriauManquantRandomSampleGenerator();

        chantier.addMateriauManquant(materiauManquantBack);
        assertThat(chantier.getMateriauManquants()).containsOnly(materiauManquantBack);
        assertThat(materiauManquantBack.getChantier()).isEqualTo(chantier);

        chantier.removeMateriauManquant(materiauManquantBack);
        assertThat(chantier.getMateriauManquants()).doesNotContain(materiauManquantBack);
        assertThat(materiauManquantBack.getChantier()).isNull();

        chantier.materiauManquants(new HashSet<>(Set.of(materiauManquantBack)));
        assertThat(chantier.getMateriauManquants()).containsOnly(materiauManquantBack);
        assertThat(materiauManquantBack.getChantier()).isEqualTo(chantier);

        chantier.setMateriauManquants(new HashSet<>());
        assertThat(chantier.getMateriauManquants()).doesNotContain(materiauManquantBack);
        assertThat(materiauManquantBack.getChantier()).isNull();
    }

    @Test
    void ouvriersTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        Ouvrier ouvrierBack = getOuvrierRandomSampleGenerator();

        chantier.addOuvriers(ouvrierBack);
        assertThat(chantier.getOuvriers()).containsOnly(ouvrierBack);

        chantier.removeOuvriers(ouvrierBack);
        assertThat(chantier.getOuvriers()).doesNotContain(ouvrierBack);

        chantier.ouvriers(new HashSet<>(Set.of(ouvrierBack)));
        assertThat(chantier.getOuvriers()).containsOnly(ouvrierBack);

        chantier.setOuvriers(new HashSet<>());
        assertThat(chantier.getOuvriers()).doesNotContain(ouvrierBack);
    }

    @Test
    void chefChantierTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        ChefChantier chefChantierBack = getChefChantierRandomSampleGenerator();

        chantier.setChefChantier(chefChantierBack);
        assertThat(chantier.getChefChantier()).isEqualTo(chefChantierBack);

        chantier.chefChantier(null);
        assertThat(chantier.getChefChantier()).isNull();
    }

    @Test
    void clientTest() {
        Chantier chantier = getChantierRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        chantier.setClient(clientBack);
        assertThat(chantier.getClient()).isEqualTo(clientBack);

        chantier.client(null);
        assertThat(chantier.getClient()).isNull();
    }
}
