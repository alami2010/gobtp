package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.Chantier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Chantier entity.
 */
@Repository
public interface ChantierRepository extends MongoRepository<Chantier, String> {
    @Query("{}")
    Page<Chantier> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Chantier> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Chantier> findOneWithEagerRelationships(String id);
}
