package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.Ouvrier;
import com.gosoft.gobtp.repository.OuvrierRepository;
import com.gosoft.gobtp.repository.UserRepository;
import com.gosoft.gobtp.service.OuvrierService;
import com.gosoft.gobtp.service.dto.OuvrierDTO;
import com.gosoft.gobtp.service.mapper.OuvrierMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.Ouvrier}.
 */
@Service
public class OuvrierServiceImpl implements OuvrierService {

    private static final Logger LOG = LoggerFactory.getLogger(OuvrierServiceImpl.class);

    private final OuvrierRepository ouvrierRepository;

    private final OuvrierMapper ouvrierMapper;

    private final UserRepository userRepository;

    public OuvrierServiceImpl(OuvrierRepository ouvrierRepository, OuvrierMapper ouvrierMapper, UserRepository userRepository) {
        this.ouvrierRepository = ouvrierRepository;
        this.ouvrierMapper = ouvrierMapper;
        this.userRepository = userRepository;
    }

    @Override
    public OuvrierDTO save(OuvrierDTO ouvrierDTO) {
        LOG.debug("Request to save Ouvrier : {}", ouvrierDTO);
        Ouvrier ouvrier = ouvrierMapper.toEntity(ouvrierDTO);
        String userId = ouvrier.getInternalUser().getId();
        userRepository.findById(userId).ifPresent(ouvrier::internalUser);
        ouvrier = ouvrierRepository.save(ouvrier);
        return ouvrierMapper.toDto(ouvrier);
    }

    @Override
    public OuvrierDTO update(OuvrierDTO ouvrierDTO) {
        LOG.debug("Request to update Ouvrier : {}", ouvrierDTO);
        Ouvrier ouvrier = ouvrierMapper.toEntity(ouvrierDTO);
        //ouvrier.setIsPersisted();
        ouvrier = ouvrierRepository.save(ouvrier);
        return ouvrierMapper.toDto(ouvrier);
    }

    @Override
    public Optional<OuvrierDTO> partialUpdate(OuvrierDTO ouvrierDTO) {
        LOG.debug("Request to partially update Ouvrier : {}", ouvrierDTO);

        return ouvrierRepository
            .findById(ouvrierDTO.getId())
            .map(existingOuvrier -> {
                ouvrierMapper.partialUpdate(existingOuvrier, ouvrierDTO);

                return existingOuvrier;
            })
            .map(ouvrierRepository::save)
            .map(ouvrierMapper::toDto);
    }

    @Override
    public Page<OuvrierDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Ouvriers");
        return ouvrierRepository.findAll(pageable).map(ouvrierMapper::toDto);
    }

    public Page<OuvrierDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ouvrierRepository.findAllWithEagerRelationships(pageable).map(ouvrierMapper::toDto);
    }

    @Override
    public Optional<OuvrierDTO> findOne(String id) {
        LOG.debug("Request to get Ouvrier : {}", id);
        return ouvrierRepository.findOneWithEagerRelationships(id).map(ouvrierMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Ouvrier : {}", id);
        ouvrierRepository.deleteById(id);
    }
}
