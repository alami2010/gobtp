package com.gosoft.gobtp.service.mapper;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.domain.ChefChantier;
import com.gosoft.gobtp.domain.Client;
import com.gosoft.gobtp.domain.Ouvrier;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.dto.ChefChantierDTO;
import com.gosoft.gobtp.service.dto.ClientDTO;
import com.gosoft.gobtp.service.dto.OuvrierDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chantier} and its DTO {@link ChantierDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChantierMapper extends EntityMapper<ChantierDTO, Chantier> {
    @Mapping(target = "ouvriers", source = "ouvriers", qualifiedByName = "ouvrierIdSet")
    @Mapping(target = "chefChantier", source = "chefChantier", qualifiedByName = "chefChantierId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    ChantierDTO toDto(Chantier s);

    @Mapping(target = "removeOuvriers", ignore = true)
    Chantier toEntity(ChantierDTO chantierDTO);

    @Named("ouvrierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OuvrierDTO toDtoOuvrierId(Ouvrier ouvrier);

    @Named("ouvrierIdSet")
    default Set<OuvrierDTO> toDtoOuvrierIdSet(Set<Ouvrier> ouvrier) {
        return ouvrier.stream().map(this::toDtoOuvrierId).collect(Collectors.toSet());
    }

    @Named("chefChantierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChefChantierDTO toDtoChefChantierId(ChefChantier chefChantier);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
