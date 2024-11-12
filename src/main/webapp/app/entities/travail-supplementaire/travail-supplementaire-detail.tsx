import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './travail-supplementaire.reducer';

export const TravailSupplementaireDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const travailSupplementaireEntity = useAppSelector(state => state.travailSupplementaire.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="travailSupplementaireDetailsHeading">Travail Supplementaire</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{travailSupplementaireEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{travailSupplementaireEntity.name}</dd>
          <dt>
            <span id="label">Label</span>
          </dt>
          <dd>{travailSupplementaireEntity.label}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {travailSupplementaireEntity.date ? (
              <TextFormat value={travailSupplementaireEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Chantier</dt>
          <dd>{travailSupplementaireEntity.chantier ? travailSupplementaireEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/travail-supplementaire" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/travail-supplementaire/${travailSupplementaireEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TravailSupplementaireDetail;
