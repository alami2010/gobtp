import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './horaire-travail.reducer';

export const HoraireTravailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const horaireTravailEntity = useAppSelector(state => state.horaireTravail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="horaireTravailDetailsHeading">Horaire Travail</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{horaireTravailEntity.id}</dd>
          <dt>
            <span id="debutMatin">Debut Matin</span>
          </dt>
          <dd>
            {horaireTravailEntity.debutMatin ? (
              <TextFormat value={horaireTravailEntity.debutMatin} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finMatin">Fin Matin</span>
          </dt>
          <dd>
            {horaireTravailEntity.finMatin ? (
              <TextFormat value={horaireTravailEntity.finMatin} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="debutSoir">Debut Soir</span>
          </dt>
          <dd>
            {horaireTravailEntity.debutSoir ? (
              <TextFormat value={horaireTravailEntity.debutSoir} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="finSoir">Fin Soir</span>
          </dt>
          <dd>
            {horaireTravailEntity.finSoir ? <TextFormat value={horaireTravailEntity.finSoir} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {horaireTravailEntity.date ? <TextFormat value={horaireTravailEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="jour">Jour</span>
          </dt>
          <dd>{horaireTravailEntity.jour}</dd>
          <dt>Chantier</dt>
          <dd>{horaireTravailEntity.chantier ? horaireTravailEntity.chantier.id : ''}</dd>
          <dt>Ouvrier</dt>
          <dd>{horaireTravailEntity.ouvrier ? horaireTravailEntity.ouvrier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/horaire-travail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/horaire-travail/${horaireTravailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HoraireTravailDetail;
