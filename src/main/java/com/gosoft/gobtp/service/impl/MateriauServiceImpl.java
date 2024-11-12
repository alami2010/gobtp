package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.Materiau;
import com.gosoft.gobtp.repository.MateriauRepository;
import com.gosoft.gobtp.service.MateriauService;
import com.gosoft.gobtp.service.dto.MateriauDTO;
import com.gosoft.gobtp.service.mapper.MateriauMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.Materiau}.
 */
@Service
public class MateriauServiceImpl implements MateriauService {

    private static final Logger LOG = LoggerFactory.getLogger(MateriauServiceImpl.class);

    private final MateriauRepository materiauRepository;

    private final MateriauMapper materiauMapper;

    public MateriauServiceImpl(MateriauRepository materiauRepository, MateriauMapper materiauMapper) {
        this.materiauRepository = materiauRepository;
        this.materiauMapper = materiauMapper;
    }

    @Override
    public MateriauDTO save(MateriauDTO materiauDTO) {
        LOG.debug("Request to save Materiau : {}", materiauDTO);
        Materiau materiau = materiauMapper.toEntity(materiauDTO);
        materiau = materiauRepository.save(materiau);
        return materiauMapper.toDto(materiau);
    }

    @Override
    public MateriauDTO update(MateriauDTO materiauDTO) {
        LOG.debug("Request to update Materiau : {}", materiauDTO);
        Materiau materiau = materiauMapper.toEntity(materiauDTO);
        materiau = materiauRepository.save(materiau);
        return materiauMapper.toDto(materiau);
    }

    @Override
    public Optional<MateriauDTO> partialUpdate(MateriauDTO materiauDTO) {
        LOG.debug("Request to partially update Materiau : {}", materiauDTO);

        return materiauRepository
            .findById(materiauDTO.getId())
            .map(existingMateriau -> {
                materiauMapper.partialUpdate(existingMateriau, materiauDTO);

                return existingMateriau;
            })
            .map(materiauRepository::save)
            .map(materiauMapper::toDto);
    }

    @Override
    public Page<MateriauDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Materiaus");
        return materiauRepository.findAll(pageable).map(materiauMapper::toDto);
    }

    @Override
    public Optional<MateriauDTO> findOne(String id) {
        LOG.debug("Request to get Materiau : {}", id);
        return materiauRepository.findById(id).map(materiauMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Materiau : {}", id);
        materiauRepository.deleteById(id);
    }
}
