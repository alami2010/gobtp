package com.gosoft.gobtp.service.dto;

import com.gosoft.gobtp.domain.enumeration.TypeOuvrier;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.gosoft.gobtp.domain.Ouvrier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OuvrierDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private TypeOuvrier type;

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

    public TypeOuvrier getType() {
        return type;
    }

    public void setType(TypeOuvrier type) {
        this.type = type;
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
        if (!(o instanceof OuvrierDTO)) {
            return false;
        }

        OuvrierDTO ouvrierDTO = (OuvrierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ouvrierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OuvrierDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", internalUser=" + getInternalUser() +
            "}";
    }
}
