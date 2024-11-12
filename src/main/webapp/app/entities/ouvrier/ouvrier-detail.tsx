import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ouvrier.reducer';

export const OuvrierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ouvrierEntity = useAppSelector(state => state.ouvrier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ouvrierDetailsHeading">Ouvrier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ouvrierEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{ouvrierEntity.name}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{ouvrierEntity.type}</dd>
          <dt>Internal User</dt>
          <dd>{ouvrierEntity.internalUser ? ouvrierEntity.internalUser.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/ouvrier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ouvrier/${ouvrierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OuvrierDetail;
