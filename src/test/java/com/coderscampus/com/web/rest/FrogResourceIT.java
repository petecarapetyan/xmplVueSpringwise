package com.coderscampus.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coderscampus.com.IntegrationTest;
import com.coderscampus.com.domain.Frog;
import com.coderscampus.com.repository.FrogRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FrogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FrogResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_SPECIES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/frogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FrogRepository frogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrogMockMvc;

    private Frog frog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frog createEntity(EntityManager em) {
        Frog frog = new Frog().name(DEFAULT_NAME).age(DEFAULT_AGE).species(DEFAULT_SPECIES);
        return frog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frog createUpdatedEntity(EntityManager em) {
        Frog frog = new Frog().name(UPDATED_NAME).age(UPDATED_AGE).species(UPDATED_SPECIES);
        return frog;
    }

    @BeforeEach
    public void initTest() {
        frog = createEntity(em);
    }

    @Test
    @Transactional
    void createFrog() throws Exception {
        int databaseSizeBeforeCreate = frogRepository.findAll().size();
        // Create the Frog
        restFrogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frog)))
            .andExpect(status().isCreated());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeCreate + 1);
        Frog testFrog = frogList.get(frogList.size() - 1);
        assertThat(testFrog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFrog.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testFrog.getSpecies()).isEqualTo(DEFAULT_SPECIES);
    }

    @Test
    @Transactional
    void createFrogWithExistingId() throws Exception {
        // Create the Frog with an existing ID
        frog.setId(1L);

        int databaseSizeBeforeCreate = frogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frog)))
            .andExpect(status().isBadRequest());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFrogs() throws Exception {
        // Initialize the database
        frogRepository.saveAndFlush(frog);

        // Get all the frogList
        restFrogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES)));
    }

    @Test
    @Transactional
    void getFrog() throws Exception {
        // Initialize the database
        frogRepository.saveAndFlush(frog);

        // Get the frog
        restFrogMockMvc
            .perform(get(ENTITY_API_URL_ID, frog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES));
    }

    @Test
    @Transactional
    void getNonExistingFrog() throws Exception {
        // Get the frog
        restFrogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFrog() throws Exception {
        // Initialize the database
        frogRepository.saveAndFlush(frog);

        int databaseSizeBeforeUpdate = frogRepository.findAll().size();

        // Update the frog
        Frog updatedFrog = frogRepository.findById(frog.getId()).get();
        // Disconnect from session so that the updates on updatedFrog are not directly saved in db
        em.detach(updatedFrog);
        updatedFrog.name(UPDATED_NAME).age(UPDATED_AGE).species(UPDATED_SPECIES);

        restFrogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFrog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFrog))
            )
            .andExpect(status().isOk());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
        Frog testFrog = frogList.get(frogList.size() - 1);
        assertThat(testFrog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFrog.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testFrog.getSpecies()).isEqualTo(UPDATED_SPECIES);
    }

    @Test
    @Transactional
    void putNonExistingFrog() throws Exception {
        int databaseSizeBeforeUpdate = frogRepository.findAll().size();
        frog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frog))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrog() throws Exception {
        int databaseSizeBeforeUpdate = frogRepository.findAll().size();
        frog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frog))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrog() throws Exception {
        int databaseSizeBeforeUpdate = frogRepository.findAll().size();
        frog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFrogWithPatch() throws Exception {
        // Initialize the database
        frogRepository.saveAndFlush(frog);

        int databaseSizeBeforeUpdate = frogRepository.findAll().size();

        // Update the frog using partial update
        Frog partialUpdatedFrog = new Frog();
        partialUpdatedFrog.setId(frog.getId());

        partialUpdatedFrog.name(UPDATED_NAME).age(UPDATED_AGE);

        restFrogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrog))
            )
            .andExpect(status().isOk());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
        Frog testFrog = frogList.get(frogList.size() - 1);
        assertThat(testFrog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFrog.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testFrog.getSpecies()).isEqualTo(DEFAULT_SPECIES);
    }

    @Test
    @Transactional
    void fullUpdateFrogWithPatch() throws Exception {
        // Initialize the database
        frogRepository.saveAndFlush(frog);

        int databaseSizeBeforeUpdate = frogRepository.findAll().size();

        // Update the frog using partial update
        Frog partialUpdatedFrog = new Frog();
        partialUpdatedFrog.setId(frog.getId());

        partialUpdatedFrog.name(UPDATED_NAME).age(UPDATED_AGE).species(UPDATED_SPECIES);

        restFrogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrog))
            )
            .andExpect(status().isOk());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
        Frog testFrog = frogList.get(frogList.size() - 1);
        assertThat(testFrog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFrog.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testFrog.getSpecies()).isEqualTo(UPDATED_SPECIES);
    }

    @Test
    @Transactional
    void patchNonExistingFrog() throws Exception {
        int databaseSizeBeforeUpdate = frogRepository.findAll().size();
        frog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frog))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrog() throws Exception {
        int databaseSizeBeforeUpdate = frogRepository.findAll().size();
        frog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frog))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrog() throws Exception {
        int databaseSizeBeforeUpdate = frogRepository.findAll().size();
        frog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(frog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frog in the database
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrog() throws Exception {
        // Initialize the database
        frogRepository.saveAndFlush(frog);

        int databaseSizeBeforeDelete = frogRepository.findAll().size();

        // Delete the frog
        restFrogMockMvc
            .perform(delete(ENTITY_API_URL_ID, frog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Frog> frogList = frogRepository.findAll();
        assertThat(frogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
