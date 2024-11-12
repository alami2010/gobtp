package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.Chantier;
import com.gosoft.gobtp.repository.ChantierRepository;
import com.gosoft.gobtp.service.ChantierService;
import com.gosoft.gobtp.service.dto.ChantierDTO;
import com.gosoft.gobtp.service.mapper.ChantierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.Chantier}.
 */
@Service
public class ChantierServiceImpl implements ChantierService {

    private static final Logger LOG = LoggerFactory.getLogger(ChantierServiceImpl.class);

    private final ChantierRepository chantierRepository;

    private final ChantierMapper chantierMapper;

    public ChantierServiceImpl(ChantierRepository chantierRepository, ChantierMapper chantierMapper) {
        this.chantierRepository = chantierRepository;
        this.chantierMapper = chantierMapper;
    }

    @Override
    public ChantierDTO save(ChantierDTO chantierDTO) {
        LOG.debug("Request to save Chantier : {}", chantierDTO);
        Chantier chantier = chantierMapper.toEntity(chantierDTO);
        chantier = chantierRepository.save(chantier);
        return chantierMapper.toDto(chantier);
    }

    @Override
    public ChantierDTO update(ChantierDTO chantierDTO) {
        LOG.debug("Request to update Chantier : {}", chantierDTO);
        Chantier chantier = chantierMapper.toEntity(chantierDTO);
        chantier = chantierRepository.save(chantier);
        return chantierMapper.toDto(chantier);
    }

    @Override
    public Optional<ChantierDTO> partialUpdate(ChantierDTO chantierDTO) {
        LOG.debug("Request to partially update Chantier : {}", chantierDTO);

        return chantierRepository
            .findById(chantierDTO.getId())
            .map(existingChantier -> {
                chantierMapper.partialUpdate(existingChantier, chantierDTO);

                return existingChantier;
            })
            .map(chantierRepository::save)
            .map(chantierMapper::toDto);
    }

    @Override
    public Page<ChantierDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Chantiers");
        return chantierRepository.findAll(pageable).map(chantierMapper::toDto);
    }

    public Page<ChantierDTO> findAllWithEagerRelationships(Pageable pageable) {
        return chantierRepository.findAllWithEagerRelationships(pageable).map(chantierMapper::toDto);
    }

    @Override
    public Optional<ChantierDTO> findOne(String id) {
        LOG.debug("Request to get Chantier : {}", id);
        return chantierRepository.findOneWithEagerRelationships(id).map(chantierMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Chantier : {}", id);
        chantierRepository.deleteById(id);
    }
}
