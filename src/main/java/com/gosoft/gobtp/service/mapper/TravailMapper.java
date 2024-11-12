package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.Travail;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.TravailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Travail} and its DTO {@link TravailDTO}.
 */
@Mapper(componentModel = "spring")
public interface TravailMapper extends EntityMapper<TravailDTO, Travail> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    TravailDTO toDto(Travail s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);
}
