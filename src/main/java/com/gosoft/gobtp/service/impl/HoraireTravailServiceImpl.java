package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.HoraireTravail;
import com.gosoft.gobtp.repository.HoraireTravailRepository;
import com.gosoft.gobtp.service.HoraireTravailService;
import com.gosoft.gobtp.service.dto.HoraireTravailDTO;
import com.gosoft.gobtp.service.mapper.HoraireTravailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.HoraireTravail}.
 */
@Service
public class HoraireTravailServiceImpl implements HoraireTravailService {

    private static final Logger LOG = LoggerFactory.getLogger(HoraireTravailServiceImpl.class);

    private final HoraireTravailRepository horaireTravailRepository;

    private final HoraireTravailMapper horaireTravailMapper;

    public HoraireTravailServiceImpl(HoraireTravailRepository horaireTravailRepository, HoraireTravailMapper horaireTravailMapper) {
        this.horaireTravailRepository = horaireTravailRepository;
        this.horaireTravailMapper = horaireTravailMapper;
    }

    @Override
    public HoraireTravailDTO save(HoraireTravailDTO horaireTravailDTO) {
        LOG.debug("Request to save HoraireTravail : {}", horaireTravailDTO);
        HoraireTravail horaireTravail = horaireTravailMapper.toEntity(horaireTravailDTO);
        horaireTravail = horaireTravailRepository.save(horaireTravail);
        return horaireTravailMapper.toDto(horaireTravail);
    }

    @Override
    public HoraireTravailDTO update(HoraireTravailDTO horaireTravailDTO) {
        LOG.debug("Request to update HoraireTravail : {}", horaireTravailDTO);
        HoraireTravail horaireTravail = horaireTravailMapper.toEntity(horaireTravailDTO);
        horaireTravail = horaireTravailRepository.save(horaireTravail);
        return horaireTravailMapper.toDto(horaireTravail);
    }

    @Override
    public Optional<HoraireTravailDTO> partialUpdate(HoraireTravailDTO horaireTravailDTO) {
        LOG.debug("Request to partially update HoraireTravail : {}", horaireTravailDTO);

        return horaireTravailRepository
            .findById(horaireTravailDTO.getId())
            .map(existingHoraireTravail -> {
                horaireTravailMapper.partialUpdate(existingHoraireTravail, horaireTravailDTO);

                return existingHoraireTravail;
            })
            .map(horaireTravailRepository::save)
            .map(horaireTravailMapper::toDto);
    }

    @Override
    public Page<HoraireTravailDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all HoraireTravails");
        return horaireTravailRepository.findAll(pageable).map(horaireTravailMapper::toDto);
    }

    @Override
    public Optional<HoraireTravailDTO> findOne(String id) {
        LOG.debug("Request to get HoraireTravail : {}", id);
        return horaireTravailRepository.findById(id).map(horaireTravailMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete HoraireTravail : {}", id);
        horaireTravailRepository.deleteById(id);
    }
}
