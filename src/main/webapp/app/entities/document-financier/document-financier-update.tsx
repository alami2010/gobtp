import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getChantiers } from 'app/entities/chantier/chantier.reducer';
import { createEntity, getEntity, reset, updateEntity } from './document-financier.reducer';

export const DocumentFinancierUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chantiers = useAppSelector(state => state.chantier.entities);
  const documentFinancierEntity = useAppSelector(state => state.documentFinancier.entity);
  const loading = useAppSelector(state => state.documentFinancier.loading);
  const updating = useAppSelector(state => state.documentFinancier.updating);
  const updateSuccess = useAppSelector(state => state.documentFinancier.updateSuccess);

  const handleClose = () => {
    navigate(`/document-financier${location.search}`);
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
    const entity = {
      ...documentFinancierEntity,
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
      ? {}
      : {
          ...documentFinancierEntity,
          chantier: documentFinancierEntity?.chantier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gobtpApp.documentFinancier.home.createOrEditLabel" data-cy="DocumentFinancierCreateUpdateHeading">
            Créer ou éditer un Document Financier
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
                <ValidatedField name="id" required readOnly id="document-financier-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Nom"
                id="document-financier-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedBlobField
                label="Fichier"
                id="document-financier-fichier"
                name="fichier"
                data-cy="fichier"
                openActionLabel="Ouvrir"
                validate={{
                  required: { value: true, message: 'Ce champ est obligatoire.' },
                }}
              />
              <ValidatedField id="document-financier-chantier" name="chantier" data-cy="chantier" label="Chantier" type="select">
                <option value="" key="0" />
                {chantiers
                  ? chantiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document-financier" replace color="info">
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

export default DocumentFinancierUpdate;
