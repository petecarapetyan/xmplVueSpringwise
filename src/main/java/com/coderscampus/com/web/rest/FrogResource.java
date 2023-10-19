package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.Frog;
import com.coderscampus.com.repository.FrogRepository;
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
 * REST controller for managing {@link com.coderscampus.com.domain.Frog}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FrogResource {

    private final Logger log = LoggerFactory.getLogger(FrogResource.class);

    private static final String ENTITY_NAME = "frog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrogRepository frogRepository;

    public FrogResource(FrogRepository frogRepository) {
        this.frogRepository = frogRepository;
    }

    /**
     * {@code POST  /frogs} : Create a new frog.
     *
     * @param frog the frog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frog, or with status {@code 400 (Bad Request)} if the frog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frogs")
    public ResponseEntity<Frog> createFrog(@RequestBody Frog frog) throws URISyntaxException {
        log.debug("REST request to save Frog : {}", frog);
        if (frog.getId() != null) {
            throw new BadRequestAlertException("A new frog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Frog result = frogRepository.save(frog);
        return ResponseEntity
            .created(new URI("/api/frogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frogs/:id} : Updates an existing frog.
     *
     * @param id the id of the frog to save.
     * @param frog the frog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frog,
     * or with status {@code 400 (Bad Request)} if the frog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frogs/{id}")
    public ResponseEntity<Frog> updateFrog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Frog frog)
        throws URISyntaxException {
        log.debug("REST request to update Frog : {}, {}", id, frog);
        if (frog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Frog result = frogRepository.save(frog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /frogs/:id} : Partial updates given fields of an existing frog, field will ignore if it is null
     *
     * @param id the id of the frog to save.
     * @param frog the frog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frog,
     * or with status {@code 400 (Bad Request)} if the frog is not valid,
     * or with status {@code 404 (Not Found)} if the frog is not found,
     * or with status {@code 500 (Internal Server Error)} if the frog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/frogs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Frog> partialUpdateFrog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Frog frog)
        throws URISyntaxException {
        log.debug("REST request to partial update Frog partially : {}, {}", id, frog);
        if (frog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Frog> result = frogRepository
            .findById(frog.getId())
            .map(existingFrog -> {
                if (frog.getName() != null) {
                    existingFrog.setName(frog.getName());
                }
                if (frog.getAge() != null) {
                    existingFrog.setAge(frog.getAge());
                }
                if (frog.getSpecies() != null) {
                    existingFrog.setSpecies(frog.getSpecies());
                }

                return existingFrog;
            })
            .map(frogRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frog.getId().toString())
        );
    }

    /**
     * {@code GET  /frogs} : get all the frogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frogs in body.
     */
    @GetMapping("/frogs")
    public List<Frog> getAllFrogs() {
        log.debug("REST request to get all Frogs");
        return frogRepository.findAll();
    }

    /**
     * {@code GET  /frogs/:id} : get the "id" frog.
     *
     * @param id the id of the frog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frogs/{id}")
    public ResponseEntity<Frog> getFrog(@PathVariable Long id) {
        log.debug("REST request to get Frog : {}", id);
        Optional<Frog> frog = frogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(frog);
    }

    /**
     * {@code DELETE  /frogs/:id} : delete the "id" frog.
     *
     * @param id the id of the frog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frogs/{id}")
    public ResponseEntity<Void> deleteFrog(@PathVariable Long id) {
        log.debug("REST request to delete Frog : {}", id);
        frogRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
