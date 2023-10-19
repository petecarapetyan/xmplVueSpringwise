package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.UserHistory;
import com.coderscampus.com.repository.UserHistoryRepository;
import com.coderscampus.com.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.coderscampus.com.domain.UserHistory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserHistoryResource {

    private final Logger log = LoggerFactory.getLogger(UserHistoryResource.class);

    private static final String ENTITY_NAME = "userHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserHistoryRepository userHistoryRepository;

    public UserHistoryResource(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }

    /**
     * {@code POST  /user-histories} : Create a new userHistory.
     *
     * @param userHistory the userHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userHistory, or with status {@code 400 (Bad Request)} if the userHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-histories")
    public ResponseEntity<UserHistory> createUserHistory(@RequestBody UserHistory userHistory) throws URISyntaxException {
        log.debug("REST request to save UserHistory : {}", userHistory);
        if (userHistory.getId() != null) {
            throw new BadRequestAlertException("A new userHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserHistory result = userHistoryRepository.save(userHistory);
        return ResponseEntity
            .created(new URI("/api/user-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-histories/:id} : Updates an existing userHistory.
     *
     * @param id the id of the userHistory to save.
     * @param userHistory the userHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userHistory,
     * or with status {@code 400 (Bad Request)} if the userHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-histories/{id}")
    public ResponseEntity<UserHistory> updateUserHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserHistory userHistory
    ) throws URISyntaxException {
        log.debug("REST request to update UserHistory : {}, {}", id, userHistory);
        if (userHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserHistory result = userHistoryRepository.save(userHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-histories/:id} : Partial updates given fields of an existing userHistory, field will ignore if it is null
     *
     * @param id the id of the userHistory to save.
     * @param userHistory the userHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userHistory,
     * or with status {@code 400 (Bad Request)} if the userHistory is not valid,
     * or with status {@code 404 (Not Found)} if the userHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the userHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserHistory> partialUpdateUserHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserHistory userHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserHistory partially : {}, {}", id, userHistory);
        if (userHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserHistory> result = userHistoryRepository
            .findById(userHistory.getId())
            .map(existingUserHistory -> {
                if (userHistory.getName() != null) {
                    existingUserHistory.setName(userHistory.getName());
                }
                if (userHistory.getIssue() != null) {
                    existingUserHistory.setIssue(userHistory.getIssue());
                }
                if (userHistory.getIssueDate() != null) {
                    existingUserHistory.setIssueDate(userHistory.getIssueDate());
                }

                return existingUserHistory;
            })
            .map(userHistoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /user-histories} : get all the userHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userHistories in body.
     */
    @GetMapping("/user-histories")
    public List<UserHistory> getAllUserHistories() {
        log.debug("REST request to get all UserHistories");
        return userHistoryRepository.findAll();
    }

    /**
     * {@code GET  /user-histories/:id} : get the "id" userHistory.
     *
     * @param id the id of the userHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-histories/{id}")
    public ResponseEntity<UserHistory> getUserHistory(@PathVariable Long id) {
        log.debug("REST request to get UserHistory : {}", id);
        Optional<UserHistory> userHistory = userHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userHistory);
    }

    /**
     * {@code DELETE  /user-histories/:id} : delete the "id" userHistory.
     *
     * @param id the id of the userHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-histories/{id}")
    public ResponseEntity<Void> deleteUserHistory(@PathVariable Long id) {
        log.debug("REST request to delete UserHistory : {}", id);
        userHistoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
