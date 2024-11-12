import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import HoraireTravail from './horaire-travail';
import HoraireTravailDetail from './horaire-travail-detail';
import HoraireTravailUpdate from './horaire-travail-update';
import HoraireTravailDeleteDialog from './horaire-travail-delete-dialog';

const HoraireTravailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<HoraireTravail />} />
    <Route path="new" element={<HoraireTravailUpdate />} />
    <Route path=":id">
      <Route index element={<HoraireTravailDetail />} />
      <Route path="edit" element={<HoraireTravailUpdate />} />
      <Route path="delete" element={<HoraireTravailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HoraireTravailRoutes;
