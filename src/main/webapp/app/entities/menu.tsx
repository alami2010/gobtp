import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/chantier">
        Chantier
      </MenuItem>
      <MenuItem icon="asterisk" to="/chef-chantier">
        Chef Chantier
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        Client
      </MenuItem>
      <MenuItem icon="asterisk" to="/document-financier">
        Document Financier
      </MenuItem>
      <MenuItem icon="asterisk" to="/horaire-travail">
        Horaire Travail
      </MenuItem>
      <MenuItem icon="asterisk" to="/materiau">
        Materiau
      </MenuItem>
      <MenuItem icon="asterisk" to="/materiau-manquant">
        Materiau Manquant
      </MenuItem>
      <MenuItem icon="asterisk" to="/ouvrier">
        Ouvrier
      </MenuItem>
      <MenuItem icon="asterisk" to="/photo-travail">
        Photo Travail
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan">
        Plan
      </MenuItem>
      <MenuItem icon="asterisk" to="/travail">
        Travail
      </MenuItem>
      <MenuItem icon="asterisk" to="/travail-supplementaire">
        Travail Supplementaire
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
