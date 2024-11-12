package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.DocumentFinancier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the DocumentFinancier entity.
 */
@Repository
public interface DocumentFinancierRepository extends MongoRepository<DocumentFinancier, String> {}
