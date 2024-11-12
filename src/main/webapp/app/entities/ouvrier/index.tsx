import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ouvrier from './ouvrier';
import OuvrierDetail from './ouvrier-detail';
import OuvrierUpdate from './ouvrier-update';
import OuvrierDeleteDialog from './ouvrier-delete-dialog';

const OuvrierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ouvrier />} />
    <Route path="new" element={<OuvrierUpdate />} />
    <Route path=":id">
      <Route index element={<OuvrierDetail />} />
      <Route path="edit" element={<OuvrierUpdate />} />
      <Route path="delete" element={<OuvrierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OuvrierRoutes;
