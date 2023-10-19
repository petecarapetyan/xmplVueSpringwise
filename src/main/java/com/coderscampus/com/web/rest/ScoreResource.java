package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.Score;
import com.coderscampus.com.repository.ScoreRepository;
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
 * REST controller for managing {@link com.coderscampus.com.domain.Score}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScoreResource {

    private final Logger log = LoggerFactory.getLogger(ScoreResource.class);

    private static final String ENTITY_NAME = "score";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoreRepository scoreRepository;

    public ScoreResource(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    /**
     * {@code POST  /scores} : Create a new score.
     *
     * @param score the score to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new score, or with status {@code 400 (Bad Request)} if the score has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scores")
    public ResponseEntity<Score> createScore(@Valid @RequestBody Score score) throws URISyntaxException {
        log.debug("REST request to save Score : {}", score);
        if (score.getId() != null) {
            throw new BadRequestAlertException("A new score cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Score result = scoreRepository.save(score);
        return ResponseEntity
            .created(new URI("/api/scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scores/:id} : Updates an existing score.
     *
     * @param id the id of the score to save.
     * @param score the score to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated score,
     * or with status {@code 400 (Bad Request)} if the score is not valid,
     * or with status {@code 500 (Internal Server Error)} if the score couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scores/{id}")
    public ResponseEntity<Score> updateScore(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Score score)
        throws URISyntaxException {
        log.debug("REST request to update Score : {}, {}", id, score);
        if (score.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, score.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Score result = scoreRepository.save(score);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, score.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scores/:id} : Partial updates given fields of an existing score, field will ignore if it is null
     *
     * @param id the id of the score to save.
     * @param score the score to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated score,
     * or with status {@code 400 (Bad Request)} if the score is not valid,
     * or with status {@code 404 (Not Found)} if the score is not found,
     * or with status {@code 500 (Internal Server Error)} if the score couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scores/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Score> partialUpdateScore(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Score score
    ) throws URISyntaxException {
        log.debug("REST request to partial update Score partially : {}, {}", id, score);
        if (score.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, score.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Score> result = scoreRepository
            .findById(score.getId())
            .map(existingScore -> {
                if (score.getPoints() != null) {
                    existingScore.setPoints(score.getPoints());
                }

                return existingScore;
            })
            .map(scoreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, score.getId().toString())
        );
    }

    /**
     * {@code GET  /scores} : get all the scores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scores in body.
     */
    @GetMapping("/scores")
    public List<Score> getAllScores() {
        log.debug("REST request to get all Scores");
        return scoreRepository.findAll();
    }

    /**
     * {@code GET  /scores/:id} : get the "id" score.
     *
     * @param id the id of the score to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the score, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scores/{id}")
    public ResponseEntity<Score> getScore(@PathVariable Long id) {
        log.debug("REST request to get Score : {}", id);
        Optional<Score> score = scoreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(score);
    }

    /**
     * {@code DELETE  /scores/:id} : delete the "id" score.
     *
     * @param id the id of the score to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scores/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        log.debug("REST request to delete Score : {}", id);
        scoreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
