package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.MateriauManquant;
import com.gosoft.gobtp.repository.MateriauManquantRepository;
import com.gosoft.gobtp.service.MateriauManquantService;
import com.gosoft.gobtp.service.dto.MateriauManquantDTO;
import com.gosoft.gobtp.service.mapper.MateriauManquantMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.MateriauManquant}.
 */
@Service
public class MateriauManquantServiceImpl implements MateriauManquantService {

    private static final Logger LOG = LoggerFactory.getLogger(MateriauManquantServiceImpl.class);

    private final MateriauManquantRepository materiauManquantRepository;

    private final MateriauManquantMapper materiauManquantMapper;

    public MateriauManquantServiceImpl(
        MateriauManquantRepository materiauManquantRepository,
        MateriauManquantMapper materiauManquantMapper
    ) {
        this.materiauManquantRepository = materiauManquantRepository;
        this.materiauManquantMapper = materiauManquantMapper;
    }

    @Override
    public MateriauManquantDTO save(MateriauManquantDTO materiauManquantDTO) {
        LOG.debug("Request to save MateriauManquant : {}", materiauManquantDTO);
        MateriauManquant materiauManquant = materiauManquantMapper.toEntity(materiauManquantDTO);
        materiauManquant = materiauManquantRepository.save(materiauManquant);
        return materiauManquantMapper.toDto(materiauManquant);
    }

    @Override
    public MateriauManquantDTO update(MateriauManquantDTO materiauManquantDTO) {
        LOG.debug("Request to update MateriauManquant : {}", materiauManquantDTO);
        MateriauManquant materiauManquant = materiauManquantMapper.toEntity(materiauManquantDTO);
        materiauManquant = materiauManquantRepository.save(materiauManquant);
        return materiauManquantMapper.toDto(materiauManquant);
    }

    @Override
    public Optional<MateriauManquantDTO> partialUpdate(MateriauManquantDTO materiauManquantDTO) {
        LOG.debug("Request to partially update MateriauManquant : {}", materiauManquantDTO);

        return materiauManquantRepository
            .findById(materiauManquantDTO.getId())
            .map(existingMateriauManquant -> {
                materiauManquantMapper.partialUpdate(existingMateriauManquant, materiauManquantDTO);

                return existingMateriauManquant;
            })
            .map(materiauManquantRepository::save)
            .map(materiauManquantMapper::toDto);
    }

    @Override
    public Page<MateriauManquantDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MateriauManquants");
        return materiauManquantRepository.findAll(pageable).map(materiauManquantMapper::toDto);
    }

    @Override
    public Optional<MateriauManquantDTO> findOne(String id) {
        LOG.debug("Request to get MateriauManquant : {}", id);
        return materiauManquantRepository.findById(id).map(materiauManquantMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete MateriauManquant : {}", id);
        materiauManquantRepository.deleteById(id);
    }
}
