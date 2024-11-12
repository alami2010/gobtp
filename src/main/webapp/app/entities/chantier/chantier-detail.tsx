import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './chantier.reducer';

export const ChantierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const chantierEntity = useAppSelector(state => state.chantier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="chantierDetailsHeading">Chantier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{chantierEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{chantierEntity.name}</dd>
          <dt>
            <span id="adresse">Adresse</span>
          </dt>
          <dd>{chantierEntity.adresse}</dd>
          <dt>
            <span id="desc">Desc</span>
          </dt>
          <dd>{chantierEntity.desc}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{chantierEntity.status}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{chantierEntity.date ? <TextFormat value={chantierEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Ouvriers</dt>
          <dd>
            {chantierEntity.ouvriers
              ? chantierEntity.ouvriers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {chantierEntity.ouvriers && i === chantierEntity.ouvriers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Chef Chantier</dt>
          <dd>{chantierEntity.chefChantier ? chantierEntity.chefChantier.id : ''}</dd>
          <dt>Client</dt>
          <dd>{chantierEntity.client ? chantierEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/chantier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/chantier/${chantierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChantierDetail;
