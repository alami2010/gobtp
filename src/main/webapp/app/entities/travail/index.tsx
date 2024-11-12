import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Travail from './travail';
import TravailDetail from './travail-detail';
import TravailUpdate from './travail-update';
import TravailDeleteDialog from './travail-delete-dialog';

const TravailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Travail />} />
    <Route path="new" element={<TravailUpdate />} />
    <Route path=":id">
      <Route index element={<TravailDetail />} />
      <Route path="edit" element={<TravailUpdate />} />
      <Route path="delete" element={<TravailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TravailRoutes;
