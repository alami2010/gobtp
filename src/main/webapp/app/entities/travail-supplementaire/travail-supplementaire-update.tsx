import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getChantiers } from 'app/entities/chantier/chantier.reducer';
import { createEntity, getEntity, reset, updateEntity } from './travail-supplementaire.reducer';

export const TravailSupplementaireUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chantiers = useAppSelector(state => state.chantier.entities);
  const travailSupplementaireEntity = useAppSelector(state => state.travailSupplementaire.entity);
  const loading = useAppSelector(state => state.travailSupplementaire.loading);
  const updating = useAppSelector(state => state.travailSupplementaire.updating);
  const updateSuccess = useAppSelector(state => state.travailSupplementaire.updateSuccess);

  const handleClose = () => {
    navigate(`/travail-supplementaire${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChantiers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...travailSupplementaireEntity,
      ...values,
      chantier: chantiers.find(it => it.id.toString() === values.chantier?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...travailSupplementaireEntity,
          date: convertDateTimeFromServer(travailSupplementaireEntity.date),
          chantier: travailSupplementaireEntity?.chantier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gobtpApp.travailSupplementaire.home.createOrEditLabel" data-cy="TravailSupplementaireCreateUpdateHeading">
            Créer ou éditer un Travail Supplementaire
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="travail-supplementaire-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Name"
                id="travail-supplementaire-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField label="Label" id="travail-supplementaire-label" name="label" data-cy="label" type="text" />
              <ValidatedField
                label="Date"
                id="travail-supplementaire-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="travail-supplementaire-chantier" name="chantier" data-cy="chantier" label="Chantier" type="select">
                <option value="" key="0" />
                {chantiers
                  ? chantiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/travail-supplementaire" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Retour</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Sauvegarder
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TravailSupplementaireUpdate;
