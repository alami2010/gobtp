package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.HoraireTravail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the HoraireTravail entity.
 */
@Repository
public interface HoraireTravailRepository extends MongoRepository<HoraireTravail, String> {}
