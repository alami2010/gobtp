package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.MateriauManquant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the MateriauManquant entity.
 */
@Repository
public interface MateriauManquantRepository extends MongoRepository<MateriauManquant, String> {}
