package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.Travail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Travail entity.
 */
@Repository
public interface TravailRepository extends MongoRepository<Travail, String> {}
