package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.DocumentFinancier;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.DocumentFinancierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentFinancier} and its DTO {@link DocumentFinancierDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentFinancierMapper extends EntityMapper<DocumentFinancierDTO, DocumentFinancier> {
    @Mapping(target = "chantier", source = "chantier", qualifiedByName = "chantierId")
    DocumentFinancierDTO toDto(DocumentFinancier s);

    @Named("chantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChantierDTO toDtoChantierId(Chantier chantier);
}
