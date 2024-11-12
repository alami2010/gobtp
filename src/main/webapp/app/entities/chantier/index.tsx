import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Chantier from './chantier';
import ChantierDetail from './chantier-detail';
import ChantierUpdate from './chantier-update';
import ChantierDeleteDialog from './chantier-delete-dialog';

const ChantierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Chantier />} />
    <Route path="new" element={<ChantierUpdate />} />
    <Route path=":id">
      <Route index element={<ChantierDetail />} />
      <Route path="edit" element={<ChantierUpdate />} />
      <Route path="delete" element={<ChantierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChantierRoutes;
