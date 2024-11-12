package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.Materiau;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Materiau entity.
 */
@Repository
public interface MateriauRepository extends MongoRepository<Materiau, String> {}
