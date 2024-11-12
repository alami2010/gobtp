package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Client;
import com.gosoft.gobtp.domain.User;
import com.gosoft.gobtp.service.dto.ClientDTO;
import com.gosoft.gobtp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userLogin")
    ClientDTO toDto(Client s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
