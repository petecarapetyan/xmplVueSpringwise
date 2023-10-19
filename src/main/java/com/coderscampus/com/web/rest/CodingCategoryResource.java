package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.CodingCategory;
import com.coderscampus.com.repository.CodingCategoryRepository;
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
 * REST controller for managing {@link com.coderscampus.com.domain.CodingCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CodingCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CodingCategoryResource.class);

    private static final String ENTITY_NAME = "codingCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CodingCategoryRepository codingCategoryRepository;

    public CodingCategoryResource(CodingCategoryRepository codingCategoryRepository) {
        this.codingCategoryRepository = codingCategoryRepository;
    }

    /**
     * {@code POST  /coding-categories} : Create a new codingCategory.
     *
     * @param codingCategory the codingCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new codingCategory, or with status {@code 400 (Bad Request)} if the codingCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coding-categories")
    public ResponseEntity<CodingCategory> createCodingCategory(@RequestBody CodingCategory codingCategory) throws URISyntaxException {
        log.debug("REST request to save CodingCategory : {}", codingCategory);
        if (codingCategory.getId() != null) {
            throw new BadRequestAlertException("A new codingCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CodingCategory result = codingCategoryRepository.save(codingCategory);
        return ResponseEntity
            .created(new URI("/api/coding-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coding-categories/:id} : Updates an existing codingCategory.
     *
     * @param id the id of the codingCategory to save.
     * @param codingCategory the codingCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codingCategory,
     * or with status {@code 400 (Bad Request)} if the codingCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the codingCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coding-categories/{id}")
    public ResponseEntity<CodingCategory> updateCodingCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CodingCategory codingCategory
    ) throws URISyntaxException {
        log.debug("REST request to update CodingCategory : {}, {}", id, codingCategory);
        if (codingCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codingCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codingCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CodingCategory result = codingCategoryRepository.save(codingCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codingCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coding-categories/:id} : Partial updates given fields of an existing codingCategory, field will ignore if it is null
     *
     * @param id the id of the codingCategory to save.
     * @param codingCategory the codingCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated codingCategory,
     * or with status {@code 400 (Bad Request)} if the codingCategory is not valid,
     * or with status {@code 404 (Not Found)} if the codingCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the codingCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coding-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CodingCategory> partialUpdateCodingCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CodingCategory codingCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update CodingCategory partially : {}, {}", id, codingCategory);
        if (codingCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, codingCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!codingCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CodingCategory> result = codingCategoryRepository
            .findById(codingCategory.getId())
            .map(existingCodingCategory -> {
                if (codingCategory.getName() != null) {
                    existingCodingCategory.setName(codingCategory.getName());
                }

                return existingCodingCategory;
            })
            .map(codingCategoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, codingCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /coding-categories} : get all the codingCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of codingCategories in body.
     */
    @GetMapping("/coding-categories")
    public List<CodingCategory> getAllCodingCategories() {
        log.debug("REST request to get all CodingCategories");
        return codingCategoryRepository.findAll();
    }

    /**
     * {@code GET  /coding-categories/:id} : get the "id" codingCategory.
     *
     * @param id the id of the codingCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the codingCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coding-categories/{id}")
    public ResponseEntity<CodingCategory> getCodingCategory(@PathVariable Long id) {
        log.debug("REST request to get CodingCategory : {}", id);
        Optional<CodingCategory> codingCategory = codingCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(codingCategory);
    }

    /**
     * {@code DELETE  /coding-categories/:id} : delete the "id" codingCategory.
     *
     * @param id the id of the codingCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coding-categories/{id}")
    public ResponseEntity<Void> deleteCodingCategory(@PathVariable Long id) {
        log.debug("REST request to delete CodingCategory : {}", id);
        codingCategoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
