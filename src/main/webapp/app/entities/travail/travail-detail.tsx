import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './travail.reducer';

export const TravailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const travailEntity = useAppSelector(state => state.travail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="travailDetailsHeading">Travail</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{travailEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{travailEntity.name}</dd>
          <dt>
            <span id="label">Label</span>
          </dt>
          <dd>{travailEntity.label}</dd>
          <dt>Chantier</dt>
          <dd>{travailEntity.chantier ? travailEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/travail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/travail/${travailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TravailDetail;
