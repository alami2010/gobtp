import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TravailSupplementaire from './travail-supplementaire';
import TravailSupplementaireDetail from './travail-supplementaire-detail';
import TravailSupplementaireUpdate from './travail-supplementaire-update';
import TravailSupplementaireDeleteDialog from './travail-supplementaire-delete-dialog';

const TravailSupplementaireRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TravailSupplementaire />} />
    <Route path="new" element={<TravailSupplementaireUpdate />} />
    <Route path=":id">
      <Route index element={<TravailSupplementaireDetail />} />
      <Route path="edit" element={<TravailSupplementaireUpdate />} />
      <Route path="delete" element={<TravailSupplementaireDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TravailSupplementaireRoutes;
