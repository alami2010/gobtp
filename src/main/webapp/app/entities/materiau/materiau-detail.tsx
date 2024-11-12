import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materiau.reducer';

export const MateriauDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materiauEntity = useAppSelector(state => state.materiau.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materiauDetailsHeading">Materiau</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{materiauEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{materiauEntity.name}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{materiauEntity.date ? <TextFormat value={materiauEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Chantier</dt>
          <dd>{materiauEntity.chantier ? materiauEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/materiau" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materiau/${materiauEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MateriauDetail;
