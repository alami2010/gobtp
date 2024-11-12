package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.HoraireTravail;
import com.gosoft.gobtp.domain.Ouvrier;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.HoraireTravailDTO;
import com.gosoft.gobtp.service.dto.OuvrierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HoraireTravail} and its DTO {@link HoraireTravailDTO}.
 */
@Mapper(componentModel = "spring")
public interface HoraireTravailMapper extends EntityMapper<HoraireTravailDTO, HoraireTravail> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    @Mapping(target = "ouvrier", source = "ouvrier", qualifiedByName = "ouvrierId")
    HoraireTravailDTO toDto(HoraireTravail s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);

    @Named("ouvrierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OuvrierDTO toDtoOuvrierId(Ouvrier ouvrier);
}
