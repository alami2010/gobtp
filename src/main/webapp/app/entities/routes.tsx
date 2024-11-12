import React from 'react';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { Route } from 'react-router-dom';
import Chantier from './chantier';
import ChefChantier from './chef-chantier';
import Client from './client';
import DocumentFinancier from './document-financier';
import HoraireTravail from './horaire-travail';
import Materiau from './materiau';
import MateriauManquant from './materiau-manquant';
import Ouvrier from './ouvrier';
import PhotoTravail from './photo-travail';
import Plan from './plan';
import Travail from './travail';
import TravailSupplementaire from './travail-supplementaire';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="chantier/*" element={<Chantier />} />
        <Route path="chef-chantier/*" element={<ChefChantier />} />
        <Route path="client/*" element={<Client />} />
        <Route path="document-financier/*" element={<DocumentFinancier />} />
        <Route path="horaire-travail/*" element={<HoraireTravail />} />
        <Route path="materiau/*" element={<Materiau />} />
        <Route path="materiau-manquant/*" element={<MateriauManquant />} />
        <Route path="ouvrier/*" element={<Ouvrier />} />
        <Route path="photo-travail/*" element={<PhotoTravail />} />
        <Route path="plan/*" element={<Plan />} />
        <Route path="travail/*" element={<Travail />} />
        <Route path="travail-supplementaire/*" element={<TravailSupplementaire />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
