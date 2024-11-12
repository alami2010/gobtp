package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.ChefChantier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ChefChantier entity.
 */
@Repository
public interface ChefChantierRepository extends MongoRepository<ChefChantier, String> {
    @Query("{}")
    Page<ChefChantier> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<ChefChantier> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<ChefChantier> findOneWithEagerRelationships(String id);
}
