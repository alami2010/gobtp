import React from 'react';
import { ValidatedField } from 'react-jhipster';
import { Alert, Button, Col, Form, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';
import { Link } from 'react-router-dom';
import { type FieldError, useForm } from 'react-hook-form';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: (username: string, password: string, rememberMe: boolean) => void;
  handleClose: () => void;
}

const LoginModal = (props: ILoginModalProps) => {
  const login = ({ username, password, rememberMe }) => {
    props.handleLogin(username, password, rememberMe);
  };

  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const { loginError, handleClose } = props;

  const handleLoginSubmit = e => {
    handleSubmit(login)(e);
  };

  return (
    <Modal isOpen={props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
      <Form onSubmit={handleLoginSubmit}>
        <ModalHeader id="login-title" data-cy="loginTitle" toggle={handleClose}>
          Authentification
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col md="12">
              {loginError ? (
                <Alert color="danger" data-cy="loginError">
                  <strong>Erreur d&apos;authentification !</strong> Veuillez vérifier vos identifiants de connexion.
                </Alert>
              ) : null}
            </Col>
            <Col md="12">
              <ValidatedField
                name="username"
                label="Nom d'utilisateur"
                placeholder="Votre nom d'utilisateur"
                required
                autoFocus
                data-cy="username"
                validate={{ required: 'Username cannot be empty!' }}
                register={register}
                error={errors.username as FieldError}
                isTouched={touchedFields.username}
              />
              <ValidatedField
                name="password"
                type="password"
                label="Mot de passe"
                placeholder="Votre mot de passe"
                required
                data-cy="password"
                validate={{ required: 'Password cannot be empty!' }}
                register={register}
                error={errors.password as FieldError}
                isTouched={touchedFields.password}
              />
              <ValidatedField name="rememberMe" type="checkbox" check label="Garder la session ouverte" value={true} register={register} />
            </Col>
          </Row>
          <div className="mt-1">&nbsp;</div>
          <Alert color="warning">
            <Link to="/account/reset/request" data-cy="forgetYourPasswordSelector">
              Avez-vous oublié votre mot de passe ?
            </Link>
          </Alert>
          <Alert color="warning">
            <span>Vous n&apos;avez pas encore de compte ?</span> <Link to="/account/register">Créer un compte</Link>
          </Alert>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={handleClose} tabIndex={1}>
            Annuler
          </Button>{' '}
          <Button color="primary" type="submit" data-cy="submit">
            Connexion
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
};

export default LoginModal;