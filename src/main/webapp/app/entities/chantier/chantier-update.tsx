import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getOuvriers } from 'app/entities/ouvrier/ouvrier.reducer';
import { getEntities as getChefChantiers } from 'app/entities/chef-chantier/chef-chantier.reducer';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { createEntity, getEntity, reset, updateEntity } from './chantier.reducer';

export const ChantierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ouvriers = useAppSelector(state => state.ouvrier.entities);
  const chefChantiers = useAppSelector(state => state.chefChantier.entities);
  const clients = useAppSelector(state => state.client.entities);
  const chantierEntity = useAppSelector(state => state.chantier.entity);
  const loading = useAppSelector(state => state.chantier.loading);
  const updating = useAppSelector(state => state.chantier.updating);
  const updateSuccess = useAppSelector(state => state.chantier.updateSuccess);

  const handleClose = () => {
    navigate(`/chantier${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOuvriers({}));
    dispatch(getChefChantiers({}));
    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...chantierEntity,
      ...values,
      ouvriers: mapIdList(values.ouvriers),
      chefChantier: chefChantiers.find(it => it.id.toString() === values.chefChantier?.toString()),
      client: clients.find(it => it.id.toString() === values.client?.toString()),
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
          ...chantierEntity,
          date: convertDateTimeFromServer(chantierEntity.date),
          ouvriers: chantierEntity?.ouvriers?.map(e => e.id.toString()),
          chefChantier: chantierEntity?.chefChantier?.id,
          client: chantierEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gobtpApp.chantier.home.createOrEditLabel" data-cy="ChantierCreateUpdateHeading">
            Créer ou éditer un Chantier
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="chantier-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="chantier-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField
                label="Adresse"
                id="chantier-adresse"
                name="adresse"
                data-cy="adresse"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField label="Desc" id="chantier-desc" name="desc" data-cy="desc" type="text" />
              <ValidatedField
                label="Status"
                id="chantier-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField
                label="Date"
                id="chantier-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField label="Ouvriers" id="chantier-ouvriers" data-cy="ouvriers" type="select" multiple name="ouvriers">
                <option value="" key="0" />
                {ouvriers
                  ? ouvriers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="chantier-chefChantier" name="chefChantier" data-cy="chefChantier" label="Chef Chantier" type="select">
                <option value="" key="0" />
                {chefChantiers
                  ? chefChantiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="chantier-client" name="client" data-cy="client" label="Client" type="select">
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/chantier" replace color="info">
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

export default ChantierUpdate;
