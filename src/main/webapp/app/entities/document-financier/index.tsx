import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DocumentFinancier from './document-financier';
import DocumentFinancierDetail from './document-financier-detail';
import DocumentFinancierUpdate from './document-financier-update';
import DocumentFinancierDeleteDialog from './document-financier-delete-dialog';

const DocumentFinancierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DocumentFinancier />} />
    <Route path="new" element={<DocumentFinancierUpdate />} />
    <Route path=":id">
      <Route index element={<DocumentFinancierDetail />} />
      <Route path="edit" element={<DocumentFinancierUpdate />} />
      <Route path="delete" element={<DocumentFinancierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DocumentFinancierRoutes;
