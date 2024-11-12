package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.TravailSupplementaire;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.TravailSupplementaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravailSupplementaire} and its DTO {@link TravailSupplementaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface TravailSupplementaireMapper extends EntityMapper<TravailSupplementaireDTO, TravailSupplementaire> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    TravailSupplementaireDTO toDto(TravailSupplementaire s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);
}
