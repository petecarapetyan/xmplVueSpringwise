package com.coderscampus.com.repository;

import com.coderscampus.com.domain.UserHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {}
