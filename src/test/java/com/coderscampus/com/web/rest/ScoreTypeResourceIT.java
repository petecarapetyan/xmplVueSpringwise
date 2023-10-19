package com.coderscampus.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coderscampus.com.IntegrationTest;
import com.coderscampus.com.domain.ScoreType;
import com.coderscampus.com.repository.ScoreTypeRepository;
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
 * Integration tests for the {@link ScoreTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScoreTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/score-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScoreTypeRepository scoreTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScoreTypeMockMvc;

    private ScoreType scoreType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScoreType createEntity(EntityManager em) {
        ScoreType scoreType = new ScoreType().name(DEFAULT_NAME);
        return scoreType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScoreType createUpdatedEntity(EntityManager em) {
        ScoreType scoreType = new ScoreType().name(UPDATED_NAME);
        return scoreType;
    }

    @BeforeEach
    public void initTest() {
        scoreType = createEntity(em);
    }

    @Test
    @Transactional
    void createScoreType() throws Exception {
        int databaseSizeBeforeCreate = scoreTypeRepository.findAll().size();
        // Create the ScoreType
        restScoreTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoreType)))
            .andExpect(status().isCreated());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ScoreType testScoreType = scoreTypeList.get(scoreTypeList.size() - 1);
        assertThat(testScoreType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createScoreTypeWithExistingId() throws Exception {
        // Create the ScoreType with an existing ID
        scoreType.setId(1L);

        int databaseSizeBeforeCreate = scoreTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoreType)))
            .andExpect(status().isBadRequest());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scoreTypeRepository.findAll().size();
        // set the field null
        scoreType.setName(null);

        // Create the ScoreType, which fails.

        restScoreTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoreType)))
            .andExpect(status().isBadRequest());

        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllScoreTypes() throws Exception {
        // Initialize the database
        scoreTypeRepository.saveAndFlush(scoreType);

        // Get all the scoreTypeList
        restScoreTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scoreType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getScoreType() throws Exception {
        // Initialize the database
        scoreTypeRepository.saveAndFlush(scoreType);

        // Get the scoreType
        restScoreTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, scoreType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scoreType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingScoreType() throws Exception {
        // Get the scoreType
        restScoreTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScoreType() throws Exception {
        // Initialize the database
        scoreTypeRepository.saveAndFlush(scoreType);

        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();

        // Update the scoreType
        ScoreType updatedScoreType = scoreTypeRepository.findById(scoreType.getId()).get();
        // Disconnect from session so that the updates on updatedScoreType are not directly saved in db
        em.detach(updatedScoreType);
        updatedScoreType.name(UPDATED_NAME);

        restScoreTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedScoreType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedScoreType))
            )
            .andExpect(status().isOk());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
        ScoreType testScoreType = scoreTypeList.get(scoreTypeList.size() - 1);
        assertThat(testScoreType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingScoreType() throws Exception {
        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();
        scoreType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scoreType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scoreType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScoreType() throws Exception {
        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();
        scoreType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(scoreType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScoreType() throws Exception {
        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();
        scoreType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(scoreType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScoreTypeWithPatch() throws Exception {
        // Initialize the database
        scoreTypeRepository.saveAndFlush(scoreType);

        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();

        // Update the scoreType using partial update
        ScoreType partialUpdatedScoreType = new ScoreType();
        partialUpdatedScoreType.setId(scoreType.getId());

        restScoreTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScoreType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScoreType))
            )
            .andExpect(status().isOk());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
        ScoreType testScoreType = scoreTypeList.get(scoreTypeList.size() - 1);
        assertThat(testScoreType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateScoreTypeWithPatch() throws Exception {
        // Initialize the database
        scoreTypeRepository.saveAndFlush(scoreType);

        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();

        // Update the scoreType using partial update
        ScoreType partialUpdatedScoreType = new ScoreType();
        partialUpdatedScoreType.setId(scoreType.getId());

        partialUpdatedScoreType.name(UPDATED_NAME);

        restScoreTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScoreType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedScoreType))
            )
            .andExpect(status().isOk());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
        ScoreType testScoreType = scoreTypeList.get(scoreTypeList.size() - 1);
        assertThat(testScoreType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingScoreType() throws Exception {
        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();
        scoreType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scoreType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scoreType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScoreType() throws Exception {
        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();
        scoreType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(scoreType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScoreType() throws Exception {
        int databaseSizeBeforeUpdate = scoreTypeRepository.findAll().size();
        scoreType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScoreTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(scoreType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScoreType in the database
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScoreType() throws Exception {
        // Initialize the database
        scoreTypeRepository.saveAndFlush(scoreType);

        int databaseSizeBeforeDelete = scoreTypeRepository.findAll().size();

        // Delete the scoreType
        restScoreTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, scoreType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScoreType> scoreTypeList = scoreTypeRepository.findAll();
        assertThat(scoreTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
