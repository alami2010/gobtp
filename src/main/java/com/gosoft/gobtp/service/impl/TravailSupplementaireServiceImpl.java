package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.TravailSupplementaire;
import com.gosoft.gobtp.repository.TravailSupplementaireRepository;
import com.gosoft.gobtp.service.TravailSupplementaireService;
import com.gosoft.gobtp.service.dto.TravailSupplementaireDTO;
import com.gosoft.gobtp.service.mapper.TravailSupplementaireMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.TravailSupplementaire}.
 */
@Service
public class TravailSupplementaireServiceImpl implements TravailSupplementaireService {

    private static final Logger LOG = LoggerFactory.getLogger(TravailSupplementaireServiceImpl.class);

    private final TravailSupplementaireRepository travailSupplementaireRepository;

    private final TravailSupplementaireMapper travailSupplementaireMapper;

    public TravailSupplementaireServiceImpl(
        TravailSupplementaireRepository travailSupplementaireRepository,
        TravailSupplementaireMapper travailSupplementaireMapper
    ) {
        this.travailSupplementaireRepository = travailSupplementaireRepository;
        this.travailSupplementaireMapper = travailSupplementaireMapper;
    }

    @Override
    public TravailSupplementaireDTO save(TravailSupplementaireDTO travailSupplementaireDTO) {
        LOG.debug("Request to save TravailSupplementaire : {}", travailSupplementaireDTO);
        TravailSupplementaire travailSupplementaire = travailSupplementaireMapper.toEntity(travailSupplementaireDTO);
        travailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);
        return travailSupplementaireMapper.toDto(travailSupplementaire);
    }

    @Override
    public TravailSupplementaireDTO update(TravailSupplementaireDTO travailSupplementaireDTO) {
        LOG.debug("Request to update TravailSupplementaire : {}", travailSupplementaireDTO);
        TravailSupplementaire travailSupplementaire = travailSupplementaireMapper.toEntity(travailSupplementaireDTO);
        travailSupplementaire = travailSupplementaireRepository.save(travailSupplementaire);
        return travailSupplementaireMapper.toDto(travailSupplementaire);
    }

    @Override
    public Optional<TravailSupplementaireDTO> partialUpdate(TravailSupplementaireDTO travailSupplementaireDTO) {
        LOG.debug("Request to partially update TravailSupplementaire : {}", travailSupplementaireDTO);

        return travailSupplementaireRepository
            .findById(travailSupplementaireDTO.getId())
            .map(existingTravailSupplementaire -> {
                travailSupplementaireMapper.partialUpdate(existingTravailSupplementaire, travailSupplementaireDTO);

                return existingTravailSupplementaire;
            })
            .map(travailSupplementaireRepository::save)
            .map(travailSupplementaireMapper::toDto);
    }

    @Override
    public Page<TravailSupplementaireDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TravailSupplementaires");
        return travailSupplementaireRepository.findAll(pageable).map(travailSupplementaireMapper::toDto);
    }

    @Override
    public Optional<TravailSupplementaireDTO> findOne(String id) {
        LOG.debug("Request to get TravailSupplementaire : {}", id);
        return travailSupplementaireRepository.findById(id).map(travailSupplementaireMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete TravailSupplementaire : {}", id);
        travailSupplementaireRepository.deleteById(id);
    }
}
