package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.ChefChantier;
import com.gosoft.gobtp.repository.ChefChantierRepository;
import com.gosoft.gobtp.repository.UserRepository;
import com.gosoft.gobtp.service.ChefChantierService;
import com.gosoft.gobtp.service.dto.ChefChantierDTO;
import com.gosoft.gobtp.service.mapper.ChefChantierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.ChefChantier}.
 */
@Service
public class ChefChantierServiceImpl implements ChefChantierService {

    private static final Logger LOG = LoggerFactory.getLogger(ChefChantierServiceImpl.class);

    private final ChefChantierRepository chefChantierRepository;

    private final ChefChantierMapper chefChantierMapper;

    private final UserRepository userRepository;

    public ChefChantierServiceImpl(
        ChefChantierRepository chefChantierRepository,
        ChefChantierMapper chefChantierMapper,
        UserRepository userRepository
    ) {
        this.chefChantierRepository = chefChantierRepository;
        this.chefChantierMapper = chefChantierMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ChefChantierDTO save(ChefChantierDTO chefChantierDTO) {
        LOG.debug("Request to save ChefChantier : {}", chefChantierDTO);
        ChefChantier chefChantier = chefChantierMapper.toEntity(chefChantierDTO);
        String userId = chefChantier.getInternalUser().getId();
        userRepository.findById(userId).ifPresent(chefChantier::internalUser);
        chefChantier = chefChantierRepository.save(chefChantier);
        return chefChantierMapper.toDto(chefChantier);
    }

    @Override
    public ChefChantierDTO update(ChefChantierDTO chefChantierDTO) {
        LOG.debug("Request to update ChefChantier : {}", chefChantierDTO);
        ChefChantier chefChantier = chefChantierMapper.toEntity(chefChantierDTO);
        chefChantier.setIsPersisted();
        chefChantier = chefChantierRepository.save(chefChantier);
        return chefChantierMapper.toDto(chefChantier);
    }

    @Override
    public Optional<ChefChantierDTO> partialUpdate(ChefChantierDTO chefChantierDTO) {
        LOG.debug("Request to partially update ChefChantier : {}", chefChantierDTO);

        return chefChantierRepository
            .findById(chefChantierDTO.getId())
            .map(existingChefChantier -> {
                chefChantierMapper.partialUpdate(existingChefChantier, chefChantierDTO);

                return existingChefChantier;
            })
            .map(chefChantierRepository::save)
            .map(chefChantierMapper::toDto);
    }

    @Override
    public Page<ChefChantierDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ChefChantiers");
        return chefChantierRepository.findAll(pageable).map(chefChantierMapper::toDto);
    }

    public Page<ChefChantierDTO> findAllWithEagerRelationships(Pageable pageable) {
        return chefChantierRepository.findAllWithEagerRelationships(pageable).map(chefChantierMapper::toDto);
    }

    @Override
    public Optional<ChefChantierDTO> findOne(String id) {
        LOG.debug("Request to get ChefChantier : {}", id);
        return chefChantierRepository.findOneWithEagerRelationships(id).map(chefChantierMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete ChefChantier : {}", id);
        chefChantierRepository.deleteById(id);
    }
}
