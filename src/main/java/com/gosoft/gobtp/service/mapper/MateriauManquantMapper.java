package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.MateriauManquant;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.MateriauManquantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MateriauManquant} and its DTO {@link MateriauManquantDTO}.
 */
@Mapper(componentModel = "spring")
public interface MateriauManquantMapper extends EntityMapper<MateriauManquantDTO, MateriauManquant> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    MateriauManquantDTO toDto(MateriauManquant s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);
}
