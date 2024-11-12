import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getChantiers } from 'app/entities/chantier/chantier.reducer';
import { getEntities as getOuvriers } from 'app/entities/ouvrier/ouvrier.reducer';
import { createEntity, getEntity, reset, updateEntity } from './horaire-travail.reducer';

export const HoraireTravailUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chantiers = useAppSelector(state => state.chantier.entities);
  const ouvriers = useAppSelector(state => state.ouvrier.entities);
  const horaireTravailEntity = useAppSelector(state => state.horaireTravail.entity);
  const loading = useAppSelector(state => state.horaireTravail.loading);
  const updating = useAppSelector(state => state.horaireTravail.updating);
  const updateSuccess = useAppSelector(state => state.horaireTravail.updateSuccess);

  const handleClose = () => {
    navigate(`/horaire-travail${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChantiers({}));
    dispatch(getOuvriers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.debutMatin = convertDateTimeToServer(values.debutMatin);
    values.finMatin = convertDateTimeToServer(values.finMatin);
    values.debutSoir = convertDateTimeToServer(values.debutSoir);
    values.finSoir = convertDateTimeToServer(values.finSoir);
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...horaireTravailEntity,
      ...values,
      chantier: chantiers.find(it => it.id.toString() === values.chantier?.toString()),
      ouvrier: ouvriers.find(it => it.id.toString() === values.ouvrier?.toString()),
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
          debutMatin: displayDefaultDateTime(),
          finMatin: displayDefaultDateTime(),
          debutSoir: displayDefaultDateTime(),
          finSoir: displayDefaultDateTime(),
          date: displayDefaultDateTime(),
        }
      : {
          ...horaireTravailEntity,
          debutMatin: convertDateTimeFromServer(horaireTravailEntity.debutMatin),
          finMatin: convertDateTimeFromServer(horaireTravailEntity.finMatin),
          debutSoir: convertDateTimeFromServer(horaireTravailEntity.debutSoir),
          finSoir: convertDateTimeFromServer(horaireTravailEntity.finSoir),
          date: convertDateTimeFromServer(horaireTravailEntity.date),
          chantier: horaireTravailEntity?.chantier?.id,
          ouvrier: horaireTravailEntity?.ouvrier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gobtpApp.horaireTravail.home.createOrEditLabel" data-cy="HoraireTravailCreateUpdateHeading">
            Créer ou éditer un Horaire Travail
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
                <ValidatedField name="id" required readOnly id="horaire-travail-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Debut Matin"
                id="horaire-travail-debutMatin"
                name="debutMatin"
                data-cy="debutMatin"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Fin Matin"
                id="horaire-travail-finMatin"
                name="finMatin"
                data-cy="finMatin"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Debut Soir"
                id="horaire-travail-debutSoir"
                name="debutSoir"
                data-cy="debutSoir"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Fin Soir"
                id="horaire-travail-finSoir"
                name="finSoir"
                data-cy="finSoir"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Date"
                id="horaire-travail-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField
                label="Jour"
                id="horaire-travail-jour"
                name="jour"
                data-cy="jour"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField id="horaire-travail-chantier" name="chantier" data-cy="chantier" label="Chantier" type="select">
                <option value="" key="0" />
                {chantiers
                  ? chantiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="horaire-travail-ouvrier" name="ouvrier" data-cy="ouvrier" label="Ouvrier" type="select">
                <option value="" key="0" />
                {ouvriers
                  ? ouvriers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/horaire-travail" replace color="info">
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

export default HoraireTravailUpdate;
