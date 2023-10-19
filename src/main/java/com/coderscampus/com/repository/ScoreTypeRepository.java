package com.coderscampus.com.repository;

import com.coderscampus.com.domain.ScoreType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ScoreType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoreTypeRepository extends JpaRepository<ScoreType, Long> {}
