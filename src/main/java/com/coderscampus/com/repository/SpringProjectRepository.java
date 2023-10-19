package com.coderscampus.com.repository;

import com.coderscampus.com.domain.SpringProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SpringProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpringProjectRepository extends JpaRepository<SpringProject, Long> {}
