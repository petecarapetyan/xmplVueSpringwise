package com.coderscampus.com.repository;

import com.coderscampus.com.domain.Frog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Frog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrogRepository extends JpaRepository<Frog, Long> {}
