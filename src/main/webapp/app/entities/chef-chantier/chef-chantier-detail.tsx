import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './chef-chantier.reducer';

export const ChefChantierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const chefChantierEntity = useAppSelector(state => state.chefChantier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="chefChantierDetailsHeading">Chef Chantier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{chefChantierEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{chefChantierEntity.name}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{chefChantierEntity.email}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{chefChantierEntity.phone}</dd>
          <dt>Internal User</dt>
          <dd>{chefChantierEntity.internalUser ? chefChantierEntity.internalUser.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/chef-chantier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/chef-chantier/${chefChantierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChefChantierDetail;
