package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.PhotoTravail;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.PhotoTravailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PhotoTravail} and its DTO {@link PhotoTravailDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoTravailMapper extends EntityMapper<PhotoTravailDTO, PhotoTravail> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    PhotoTravailDTO toDto(PhotoTravail s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);
}
