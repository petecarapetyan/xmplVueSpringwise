package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.Airplane;
import com.coderscampus.com.repository.AirplaneRepository;
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
 * REST controller for managing {@link com.coderscampus.com.domain.Airplane}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AirplaneResource {

    private final Logger log = LoggerFactory.getLogger(AirplaneResource.class);

    private static final String ENTITY_NAME = "airplane";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AirplaneRepository airplaneRepository;

    public AirplaneResource(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    /**
     * {@code POST  /airplanes} : Create a new airplane.
     *
     * @param airplane the airplane to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new airplane, or with status {@code 400 (Bad Request)} if the airplane has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/airplanes")
    public ResponseEntity<Airplane> createAirplane(@RequestBody Airplane airplane) throws URISyntaxException {
        log.debug("REST request to save Airplane : {}", airplane);
        if (airplane.getId() != null) {
            throw new BadRequestAlertException("A new airplane cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Airplane result = airplaneRepository.save(airplane);
        return ResponseEntity
            .created(new URI("/api/airplanes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /airplanes/:id} : Updates an existing airplane.
     *
     * @param id the id of the airplane to save.
     * @param airplane the airplane to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated airplane,
     * or with status {@code 400 (Bad Request)} if the airplane is not valid,
     * or with status {@code 500 (Internal Server Error)} if the airplane couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/airplanes/{id}")
    public ResponseEntity<Airplane> updateAirplane(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Airplane airplane
    ) throws URISyntaxException {
        log.debug("REST request to update Airplane : {}, {}", id, airplane);
        if (airplane.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, airplane.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!airplaneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Airplane result = airplaneRepository.save(airplane);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, airplane.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /airplanes/:id} : Partial updates given fields of an existing airplane, field will ignore if it is null
     *
     * @param id the id of the airplane to save.
     * @param airplane the airplane to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated airplane,
     * or with status {@code 400 (Bad Request)} if the airplane is not valid,
     * or with status {@code 404 (Not Found)} if the airplane is not found,
     * or with status {@code 500 (Internal Server Error)} if the airplane couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/airplanes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Airplane> partialUpdateAirplane(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Airplane airplane
    ) throws URISyntaxException {
        log.debug("REST request to partial update Airplane partially : {}, {}", id, airplane);
        if (airplane.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, airplane.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!airplaneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Airplane> result = airplaneRepository
            .findById(airplane.getId())
            .map(existingAirplane -> {
                if (airplane.getModel() != null) {
                    existingAirplane.setModel(airplane.getModel());
                }
                if (airplane.getMake() != null) {
                    existingAirplane.setMake(airplane.getMake());
                }
                if (airplane.getColor() != null) {
                    existingAirplane.setColor(airplane.getColor());
                }

                return existingAirplane;
            })
            .map(airplaneRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, airplane.getId().toString())
        );
    }

    /**
     * {@code GET  /airplanes} : get all the airplanes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of airplanes in body.
     */
    @GetMapping("/airplanes")
    public List<Airplane> getAllAirplanes() {
        log.debug("REST request to get all Airplanes");
        return airplaneRepository.findAll();
    }

    /**
     * {@code GET  /airplanes/:id} : get the "id" airplane.
     *
     * @param id the id of the airplane to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the airplane, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/airplanes/{id}")
    public ResponseEntity<Airplane> getAirplane(@PathVariable Long id) {
        log.debug("REST request to get Airplane : {}", id);
        Optional<Airplane> airplane = airplaneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(airplane);
    }

    /**
     * {@code DELETE  /airplanes/:id} : delete the "id" airplane.
     *
     * @param id the id of the airplane to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/airplanes/{id}")
    public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
        log.debug("REST request to delete Airplane : {}", id);
        airplaneRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
