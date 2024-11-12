import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan.reducer';

export const PlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planEntity = useAppSelector(state => state.plan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planDetailsHeading">Plan</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{planEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{planEntity.name}</dd>
          <dt>
            <span id="file">File</span>
          </dt>
          <dd>{planEntity.file}</dd>
          <dt>Chantier</dt>
          <dd>{planEntity.chantier ? planEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan/${planEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanDetail;
