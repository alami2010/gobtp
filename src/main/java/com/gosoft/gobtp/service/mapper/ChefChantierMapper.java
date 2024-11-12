package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.ChefChantier;
import com.gosoft.gobtp.domain.User;
import com.gosoft.gobtp.service.dto.ChefChantierDTO;
import com.gosoft.gobtp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChefChantier} and its DTO {@link ChefChantierDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChefChantierMapper extends EntityMapper<ChefChantierDTO, ChefChantier> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    ChefChantierDTO toDto(ChefChantier s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
