package com.gosoft.gobtp.repository;

import com.gosoft.gobtp.domain.Ouvrier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Ouvrier entity.
 */
@Repository
public interface OuvrierRepository extends MongoRepository<Ouvrier, String> {
    @Query("{}")
    Page<Ouvrier> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Ouvrier> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Ouvrier> findOneWithEagerRelationships(String id);
}
