import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client.reducer';

export const ClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">Client</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{clientEntity.name}</dd>
          <dt>
            <span id="isProfessional">Is Professional</span>
          </dt>
          <dd>{clientEntity.isProfessional ? 'true' : 'false'}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{clientEntity.date ? <TextFormat value={clientEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="adresse">Adresse</span>
          </dt>
          <dd>{clientEntity.adresse}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{clientEntity.info}</dd>
          <dt>Internal User</dt>
          <dd>{clientEntity.internalUser ? clientEntity.internalUser.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
