package com.gosoft.gobtp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.ChefChantier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChefChantierDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String phone;

    private UserDTO internalUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserDTO getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(UserDTO internalUser) {
        this.internalUser = internalUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChefChantierDTO)) {
            return false;
        }

        ChefChantierDTO chefChantierDTO = (ChefChantierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chefChantierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChefChantierDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
