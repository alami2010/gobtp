import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PhotoTravail from './photo-travail';
import PhotoTravailDetail from './photo-travail-detail';
import PhotoTravailUpdate from './photo-travail-update';
import PhotoTravailDeleteDialog from './photo-travail-delete-dialog';

const PhotoTravailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PhotoTravail />} />
    <Route path="new" element={<PhotoTravailUpdate />} />
    <Route path=":id">
      <Route index element={<PhotoTravailDetail />} />
      <Route path="edit" element={<PhotoTravailUpdate />} />
      <Route path="delete" element={<PhotoTravailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PhotoTravailRoutes;
