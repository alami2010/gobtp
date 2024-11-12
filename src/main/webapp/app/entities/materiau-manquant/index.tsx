import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MateriauManquant from './materiau-manquant';
import MateriauManquantDetail from './materiau-manquant-detail';
import MateriauManquantUpdate from './materiau-manquant-update';
import MateriauManquantDeleteDialog from './materiau-manquant-delete-dialog';

const MateriauManquantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MateriauManquant />} />
    <Route path="new" element={<MateriauManquantUpdate />} />
    <Route path=":id">
      <Route index element={<MateriauManquantDetail />} />
      <Route path="edit" element={<MateriauManquantUpdate />} />
      <Route path="delete" element={<MateriauManquantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MateriauManquantRoutes;
