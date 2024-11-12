import chantier from 'app/entities/chantier/chantier.reducer';
import chefChantier from 'app/entities/chef-chantier/chef-chantier.reducer';
import client from 'app/entities/client/client.reducer';
import documentFinancier from 'app/entities/document-financier/document-financier.reducer';
import horaireTravail from 'app/entities/horaire-travail/horaire-travail.reducer';
import materiau from 'app/entities/materiau/materiau.reducer';
import materiauManquant from 'app/entities/materiau-manquant/materiau-manquant.reducer';
import ouvrier from 'app/entities/ouvrier/ouvrier.reducer';
import photoTravail from 'app/entities/photo-travail/photo-travail.reducer';
import plan from 'app/entities/plan/plan.reducer';
import travail from 'app/entities/travail/travail.reducer';
import travailSupplementaire from 'app/entities/travail-supplementaire/travail-supplementaire.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  chantier,
  chefChantier,
  client,
  documentFinancier,
  horaireTravail,
  materiau,
  materiauManquant,
  ouvrier,
  photoTravail,
  plan,
  travail,
  travailSupplementaire,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
