package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.ScoreType;
import com.coderscampus.com.repository.ScoreTypeRepository;
import com.coderscampus.com.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.coderscampus.com.domain.ScoreType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScoreTypeResource {

    private final Logger log = LoggerFactory.getLogger(ScoreTypeResource.class);

    private static final String ENTITY_NAME = "scoreType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoreTypeRepository scoreTypeRepository;

    public ScoreTypeResource(ScoreTypeRepository scoreTypeRepository) {
        this.scoreTypeRepository = scoreTypeRepository;
    }

    /**
     * {@code POST  /score-types} : Create a new scoreType.
     *
     * @param scoreType the scoreType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scoreType, or with status {@code 400 (Bad Request)} if the scoreType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/score-types")
    public ResponseEntity<ScoreType> createScoreType(@Valid @RequestBody ScoreType scoreType) throws URISyntaxException {
        log.debug("REST request to save ScoreType : {}", scoreType);
        if (scoreType.getId() != null) {
            throw new BadRequestAlertException("A new scoreType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScoreType result = scoreTypeRepository.save(scoreType);
        return ResponseEntity
            .created(new URI("/api/score-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /score-types/:id} : Updates an existing scoreType.
     *
     * @param id the id of the scoreType to save.
     * @param scoreType the scoreType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scoreType,
     * or with status {@code 400 (Bad Request)} if the scoreType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scoreType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/score-types/{id}")
    public ResponseEntity<ScoreType> updateScoreType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ScoreType scoreType
    ) throws URISyntaxException {
        log.debug("REST request to update ScoreType : {}, {}", id, scoreType);
        if (scoreType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scoreType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoreTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ScoreType result = scoreTypeRepository.save(scoreType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scoreType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /score-types/:id} : Partial updates given fields of an existing scoreType, field will ignore if it is null
     *
     * @param id the id of the scoreType to save.
     * @param scoreType the scoreType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scoreType,
     * or with status {@code 400 (Bad Request)} if the scoreType is not valid,
     * or with status {@code 404 (Not Found)} if the scoreType is not found,
     * or with status {@code 500 (Internal Server Error)} if the scoreType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/score-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScoreType> partialUpdateScoreType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ScoreType scoreType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScoreType partially : {}, {}", id, scoreType);
        if (scoreType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scoreType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoreTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScoreType> result = scoreTypeRepository
            .findById(scoreType.getId())
            .map(existingScoreType -> {
                if (scoreType.getName() != null) {
                    existingScoreType.setName(scoreType.getName());
                }

                return existingScoreType;
            })
            .map(scoreTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scoreType.getId().toString())
        );
    }

    /**
     * {@code GET  /score-types} : get all the scoreTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scoreTypes in body.
     */
    @GetMapping("/score-types")
    public List<ScoreType> getAllScoreTypes() {
        log.debug("REST request to get all ScoreTypes");
        return scoreTypeRepository.findAll();
    }

    /**
     * {@code GET  /score-types/:id} : get the "id" scoreType.
     *
     * @param id the id of the scoreType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scoreType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/score-types/{id}")
    public ResponseEntity<ScoreType> getScoreType(@PathVariable Long id) {
        log.debug("REST request to get ScoreType : {}", id);
        Optional<ScoreType> scoreType = scoreTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scoreType);
    }

    /**
     * {@code DELETE  /score-types/:id} : delete the "id" scoreType.
     *
     * @param id the id of the scoreType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/score-types/{id}")
    public ResponseEntity<Void> deleteScoreType(@PathVariable Long id) {
        log.debug("REST request to delete ScoreType : {}", id);
        scoreTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
