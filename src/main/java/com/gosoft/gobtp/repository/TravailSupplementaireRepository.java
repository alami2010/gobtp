package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.TravailSupplementaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TravailSupplementaire entity.
 */
@Repository
public interface TravailSupplementaireRepository extends MongoRepository<TravailSupplementaire, String> {}
