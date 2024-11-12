package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.Travail;
import com.gosoft.gobtp.repository.TravailRepository;
import com.gosoft.gobtp.service.TravailService;
import com.gosoft.gobtp.service.dto.TravailDTO;
import com.gosoft.gobtp.service.mapper.TravailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.Travail}.
 */
@Service
public class TravailServiceImpl implements TravailService {

    private static final Logger LOG = LoggerFactory.getLogger(TravailServiceImpl.class);

    private final TravailRepository travailRepository;

    private final TravailMapper travailMapper;

    public TravailServiceImpl(TravailRepository travailRepository, TravailMapper travailMapper) {
        this.travailRepository = travailRepository;
        this.travailMapper = travailMapper;
    }

    @Override
    public TravailDTO save(TravailDTO travailDTO) {
        LOG.debug("Request to save Travail : {}", travailDTO);
        Travail travail = travailMapper.toEntity(travailDTO);
        travail = travailRepository.save(travail);
        return travailMapper.toDto(travail);
    }

    @Override
    public TravailDTO update(TravailDTO travailDTO) {
        LOG.debug("Request to update Travail : {}", travailDTO);
        Travail travail = travailMapper.toEntity(travailDTO);
        travail = travailRepository.save(travail);
        return travailMapper.toDto(travail);
    }

    @Override
    public Optional<TravailDTO> partialUpdate(TravailDTO travailDTO) {
        LOG.debug("Request to partially update Travail : {}", travailDTO);

        return travailRepository
            .findById(travailDTO.getId())
            .map(existingTravail -> {
                travailMapper.partialUpdate(existingTravail, travailDTO);

                return existingTravail;
            })
            .map(travailRepository::save)
            .map(travailMapper::toDto);
    }

    @Override
    public Page<TravailDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Travails");
        return travailRepository.findAll(pageable).map(travailMapper::toDto);
    }

    @Override
    public Optional<TravailDTO> findOne(String id) {
        LOG.debug("Request to get Travail : {}", id);
        return travailRepository.findById(id).map(travailMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Travail : {}", id);
        travailRepository.deleteById(id);
    }
}
