package com.gosoft.gobtp.service.impl;

import com.gosoft.gobtp.domain.PhotoTravail;
import com.gosoft.gobtp.repository.PhotoTravailRepository;
import com.gosoft.gobtp.service.PhotoTravailService;
import com.gosoft.gobtp.service.dto.PhotoTravailDTO;
import com.gosoft.gobtp.service.mapper.PhotoTravailMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.gosoft.gobtp.domain.PhotoTravail}.
 */
@Service
public class PhotoTravailServiceImpl implements PhotoTravailService {

    private static final Logger LOG = LoggerFactory.getLogger(PhotoTravailServiceImpl.class);

    private final PhotoTravailRepository photoTravailRepository;

    private final PhotoTravailMapper photoTravailMapper;

    public PhotoTravailServiceImpl(PhotoTravailRepository photoTravailRepository, PhotoTravailMapper photoTravailMapper) {
        this.photoTravailRepository = photoTravailRepository;
        this.photoTravailMapper = photoTravailMapper;
    }

    @Override
    public PhotoTravailDTO save(PhotoTravailDTO photoTravailDTO) {
        LOG.debug("Request to save PhotoTravail : {}", photoTravailDTO);
        PhotoTravail photoTravail = photoTravailMapper.toEntity(photoTravailDTO);
        photoTravail = photoTravailRepository.save(photoTravail);
        return photoTravailMapper.toDto(photoTravail);
    }

    @Override
    public PhotoTravailDTO update(PhotoTravailDTO photoTravailDTO) {
        LOG.debug("Request to update PhotoTravail : {}", photoTravailDTO);
        PhotoTravail photoTravail = photoTravailMapper.toEntity(photoTravailDTO);
        photoTravail = photoTravailRepository.save(photoTravail);
        return photoTravailMapper.toDto(photoTravail);
    }

    @Override
    public Optional<PhotoTravailDTO> partialUpdate(PhotoTravailDTO photoTravailDTO) {
        LOG.debug("Request to partially update PhotoTravail : {}", photoTravailDTO);

        return photoTravailRepository
            .findById(photoTravailDTO.getId())
            .map(existingPhotoTravail -> {
                photoTravailMapper.partialUpdate(existingPhotoTravail, photoTravailDTO);

                return existingPhotoTravail;
            })
            .map(photoTravailRepository::save)
            .map(photoTravailMapper::toDto);
    }

    @Override
    public Page<PhotoTravailDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PhotoTravails");
        return photoTravailRepository.findAll(pageable).map(photoTravailMapper::toDto);
    }

    @Override
    public Optional<PhotoTravailDTO> findOne(String id) {
        LOG.debug("Request to get PhotoTravail : {}", id);
        return photoTravailRepository.findById(id).map(photoTravailMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete PhotoTravail : {}", id);
        photoTravailRepository.deleteById(id);
    }
}
