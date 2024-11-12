import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { TypeOuvrier } from 'app/shared/model/enumerations/type-ouvrier.model';
import { createEntity, getEntity, reset, updateEntity } from './ouvrier.reducer';

export const OuvrierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const ouvrierEntity = useAppSelector(state => state.ouvrier.entity);
  const loading = useAppSelector(state => state.ouvrier.loading);
  const updating = useAppSelector(state => state.ouvrier.updating);
  const updateSuccess = useAppSelector(state => state.ouvrier.updateSuccess);
  const typeOuvrierValues = Object.keys(TypeOuvrier);

  const handleClose = () => {
    navigate(`/ouvrier${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ouvrierEntity,
      ...values,
      internalUser: users.find(it => it.id.toString() === values.internalUser?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'Ouvrier',
          ...ouvrierEntity,
          internalUser: ouvrierEntity?.internalUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gobtpApp.ouvrier.home.createOrEditLabel" data-cy="OuvrierCreateUpdateHeading">
            Créer ou éditer un Ouvrier
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="ouvrier-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="ouvrier-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField label="Type" id="ouvrier-type" name="type" data-cy="type" type="select">
                {typeOuvrierValues.map(typeOuvrier => (
                  <option value={typeOuvrier} key={typeOuvrier}>
                    {typeOuvrier}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="ouvrier-internalUser" name="internalUser" data-cy="internalUser" label="Internal User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/ouvrier" replace color="info">
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

export default OuvrierUpdate;
