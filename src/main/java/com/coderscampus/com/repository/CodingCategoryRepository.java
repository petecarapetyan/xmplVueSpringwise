package com.coderscampus.com.repository;

import com.coderscampus.com.domain.CodingCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CodingCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodingCategoryRepository extends JpaRepository<CodingCategory, Long> {}
