import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document-financier.reducer';

export const DocumentFinancierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentFinancierEntity = useAppSelector(state => state.documentFinancier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentFinancierDetailsHeading">Document Financier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{documentFinancierEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{documentFinancierEntity.nom}</dd>
          <dt>
            <span id="file">File</span>
          </dt>
          <dd>{documentFinancierEntity.file}</dd>
          <dt>Chantier</dt>
          <dd>{documentFinancierEntity.chantier ? documentFinancierEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/document-financier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document-financier/${documentFinancierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentFinancierDetail;
