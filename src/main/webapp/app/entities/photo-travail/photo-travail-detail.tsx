import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, byteSize, openFile } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './photo-travail.reducer';

export const PhotoTravailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const photoTravailEntity = useAppSelector(state => state.photoTravail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="photoTravailDetailsHeading">Photo Travail</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{photoTravailEntity.id}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{photoTravailEntity.description}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{photoTravailEntity.date ? <TextFormat value={photoTravailEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="photo">Photo</span>
          </dt>
          <dd>
            {photoTravailEntity.photo ? (
              <div>
                {photoTravailEntity.photoContentType ? (
                  <a onClick={openFile(photoTravailEntity.photoContentType, photoTravailEntity.photo)}>
                    <img
                      src={`data:${photoTravailEntity.photoContentType};base64,${photoTravailEntity.photo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {photoTravailEntity.photoContentType}, {byteSize(photoTravailEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Chantier</dt>
          <dd>{photoTravailEntity.chantier ? photoTravailEntity.chantier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/photo-travail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/photo-travail/${photoTravailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PhotoTravailDetail;
