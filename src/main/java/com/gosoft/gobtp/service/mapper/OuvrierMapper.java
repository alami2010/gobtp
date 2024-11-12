package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Ouvrier;
import com.gosoft.gobtp.domain.User;
import com.gosoft.gobtp.service.dto.OuvrierDTO;
import com.gosoft.gobtp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ouvrier} and its DTO {@link OuvrierDTO}.
 */
@Mapper(componentModel = "spring")
public interface OuvrierMapper extends EntityMapper<OuvrierDTO, Ouvrier> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    OuvrierDTO toDto(Ouvrier s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
