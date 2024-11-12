package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.Materiau;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.MateriauDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Materiau} and its DTO {@link MateriauDTO}.
 */
@Mapper(componentModel = "spring")
public interface MateriauMapper extends EntityMapper<MateriauDTO, Materiau> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    MateriauDTO toDto(Materiau s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);
}
