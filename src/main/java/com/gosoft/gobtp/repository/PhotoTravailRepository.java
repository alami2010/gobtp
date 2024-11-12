package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.PhotoTravail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PhotoTravail entity.
 */
@Repository
public interface PhotoTravailRepository extends MongoRepository<PhotoTravail, String> {}
