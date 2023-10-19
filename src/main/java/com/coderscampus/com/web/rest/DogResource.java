package com.coderscampus.com.web.rest;

import com.coderscampus.com.domain.Dog;
import com.coderscampus.com.repository.DogRepository;
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
 * REST controller for managing {@link com.coderscampus.com.domain.Dog}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DogResource {

    private final Logger log = LoggerFactory.getLogger(DogResource.class);

    private static final String ENTITY_NAME = "dog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DogRepository dogRepository;

    public DogResource(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * {@code POST  /dogs} : Create a new dog.
     *
     * @param dog the dog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dog, or with status {@code 400 (Bad Request)} if the dog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dogs")
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) throws URISyntaxException {
        log.debug("REST request to save Dog : {}", dog);
        if (dog.getId() != null) {
            throw new BadRequestAlertException("A new dog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dog result = dogRepository.save(dog);
        return ResponseEntity
            .created(new URI("/api/dogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dogs/:id} : Updates an existing dog.
     *
     * @param id the id of the dog to save.
     * @param dog the dog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dog,
     * or with status {@code 400 (Bad Request)} if the dog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dogs/{id}")
    public ResponseEntity<Dog> updateDog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Dog dog)
        throws URISyntaxException {
        log.debug("REST request to update Dog : {}, {}", id, dog);
        if (dog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Dog result = dogRepository.save(dog);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dog.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dogs/:id} : Partial updates given fields of an existing dog, field will ignore if it is null
     *
     * @param id the id of the dog to save.
     * @param dog the dog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dog,
     * or with status {@code 400 (Bad Request)} if the dog is not valid,
     * or with status {@code 404 (Not Found)} if the dog is not found,
     * or with status {@code 500 (Internal Server Error)} if the dog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dogs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dog> partialUpdateDog(@PathVariable(value = "id", required = false) final Long id, @RequestBody Dog dog)
        throws URISyntaxException {
        log.debug("REST request to partial update Dog partially : {}, {}", id, dog);
        if (dog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dog.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dog> result = dogRepository
            .findById(dog.getId())
            .map(existingDog -> {
                if (dog.getName() != null) {
                    existingDog.setName(dog.getName());
                }
                if (dog.getAge() != null) {
                    existingDog.setAge(dog.getAge());
                }
                if (dog.getBreed() != null) {
                    existingDog.setBreed(dog.getBreed());
                }

                return existingDog;
            })
            .map(dogRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dog.getId().toString())
        );
    }

    /**
     * {@code GET  /dogs} : get all the dogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dogs in body.
     */
    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        log.debug("REST request to get all Dogs");
        return dogRepository.findAll();
    }

    /**
     * {@code GET  /dogs/:id} : get the "id" dog.
     *
     * @param id the id of the dog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dogs/{id}")
    public ResponseEntity<Dog> getDog(@PathVariable Long id) {
        log.debug("REST request to get Dog : {}", id);
        Optional<Dog> dog = dogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dog);
    }

    /**
     * {@code DELETE  /dogs/:id} : delete the "id" dog.
     *
     * @param id the id of the dog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        log.debug("REST request to delete Dog : {}", id);
        dogRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
