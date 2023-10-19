package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.SpringProject;
import com.coderscampus.com.repository.SpringProjectRepository;
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
 * REST controller for managing {@link com.coderscampus.com.domain.SpringProject}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SpringProjectResource {

    private final Logger log = LoggerFactory.getLogger(SpringProjectResource.class);

    private static final String ENTITY_NAME = "springProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpringProjectRepository springProjectRepository;

    public SpringProjectResource(SpringProjectRepository springProjectRepository) {
        this.springProjectRepository = springProjectRepository;
    }

    /**
     * {@code POST  /spring-projects} : Create a new springProject.
     *
     * @param springProject the springProject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new springProject, or with status {@code 400 (Bad Request)} if the springProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spring-projects")
    public ResponseEntity<SpringProject> createSpringProject(@RequestBody SpringProject springProject) throws URISyntaxException {
        log.debug("REST request to save SpringProject : {}", springProject);
        if (springProject.getId() != null) {
            throw new BadRequestAlertException("A new springProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpringProject result = springProjectRepository.save(springProject);
        return ResponseEntity
            .created(new URI("/api/spring-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spring-projects/:id} : Updates an existing springProject.
     *
     * @param id the id of the springProject to save.
     * @param springProject the springProject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated springProject,
     * or with status {@code 400 (Bad Request)} if the springProject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the springProject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spring-projects/{id}")
    public ResponseEntity<SpringProject> updateSpringProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpringProject springProject
    ) throws URISyntaxException {
        log.debug("REST request to update SpringProject : {}, {}", id, springProject);
        if (springProject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, springProject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!springProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpringProject result = springProjectRepository.save(springProject);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, springProject.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /spring-projects/:id} : Partial updates given fields of an existing springProject, field will ignore if it is null
     *
     * @param id the id of the springProject to save.
     * @param springProject the springProject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated springProject,
     * or with status {@code 400 (Bad Request)} if the springProject is not valid,
     * or with status {@code 404 (Not Found)} if the springProject is not found,
     * or with status {@code 500 (Internal Server Error)} if the springProject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/spring-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpringProject> partialUpdateSpringProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpringProject springProject
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpringProject partially : {}, {}", id, springProject);
        if (springProject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, springProject.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!springProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpringProject> result = springProjectRepository
            .findById(springProject.getId())
            .map(existingSpringProject -> {
                if (springProject.getTitle() != null) {
                    existingSpringProject.setTitle(springProject.getTitle());
                }
                if (springProject.getDescription() != null) {
                    existingSpringProject.setDescription(springProject.getDescription());
                }
                if (springProject.getImagePath() != null) {
                    existingSpringProject.setImagePath(springProject.getImagePath());
                }
                if (springProject.getUrl() != null) {
                    existingSpringProject.setUrl(springProject.getUrl());
                }

                return existingSpringProject;
            })
            .map(springProjectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, springProject.getId().toString())
        );
    }

    /**
     * {@code GET  /spring-projects} : get all the springProjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of springProjects in body.
     */
    @GetMapping("/spring-projects")
    public List<SpringProject> getAllSpringProjects() {
        log.debug("REST request to get all SpringProjects");
        return springProjectRepository.findAll();
    }

    /**
     * {@code GET  /spring-projects/:id} : get the "id" springProject.
     *
     * @param id the id of the springProject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the springProject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spring-projects/{id}")
    public ResponseEntity<SpringProject> getSpringProject(@PathVariable Long id) {
        log.debug("REST request to get SpringProject : {}", id);
        Optional<SpringProject> springProject = springProjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(springProject);
    }

    /**
     * {@code DELETE  /spring-projects/:id} : delete the "id" springProject.
     *
     * @param id the id of the springProject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spring-projects/{id}")
    public ResponseEntity<Void> deleteSpringProject(@PathVariable Long id) {
        log.debug("REST request to delete SpringProject : {}", id);
        springProjectRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
