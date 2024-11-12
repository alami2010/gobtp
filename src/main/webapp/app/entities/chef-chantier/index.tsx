import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ChefChantier from './chef-chantier';
import ChefChantierDetail from './chef-chantier-detail';
import ChefChantierUpdate from './chef-chantier-update';
import ChefChantierDeleteDialog from './chef-chantier-delete-dialog';

const ChefChantierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ChefChantier />} />
    <Route path="new" element={<ChefChantierUpdate />} />
    <Route path=":id">
      <Route index element={<ChefChantierDetail />} />
      <Route path="edit" element={<ChefChantierUpdate />} />
      <Route path="delete" element={<ChefChantierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChefChantierRoutes;
