import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Materiau from './materiau';
import MateriauDetail from './materiau-detail';
import MateriauUpdate from './materiau-update';
import MateriauDeleteDialog from './materiau-delete-dialog';

const MateriauRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Materiau />} />
    <Route path="new" element={<MateriauUpdate />} />
    <Route path=":id">
      <Route index element={<MateriauDetail />} />
      <Route path="edit" element={<MateriauUpdate />} />
      <Route path="delete" element={<MateriauDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MateriauRoutes;
