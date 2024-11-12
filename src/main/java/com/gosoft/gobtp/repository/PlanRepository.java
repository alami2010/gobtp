package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Plan entity.
 */
@Repository
public interface PlanRepository extends MongoRepository<Plan, String> {}
