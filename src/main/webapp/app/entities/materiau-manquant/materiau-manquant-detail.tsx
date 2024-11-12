import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materiau-manquant.reducer';

export const MateriauManquantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materiauManquantEntity = useAppSelector(state => state.materiauManquant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materiauManquantDetailsHeading">Materiau Manquant</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{materiauManquantEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{materiauManquantEntity.name}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{materiauManquantEntity.quantity}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {materiauManquantEntity.date ? <TextFormat value={materiauManquantEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Chantier</dt>
          <dd>{materiauManquantEntity.chantier ? materiauManquantEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/materiau-manquant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materiau-manquant/${materiauManquantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MateriauManquantDetail;
