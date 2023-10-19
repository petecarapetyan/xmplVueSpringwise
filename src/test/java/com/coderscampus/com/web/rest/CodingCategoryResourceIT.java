package com.coderscampus.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coderscampus.com.IntegrationTest;
import com.coderscampus.com.domain.CodingCategory;
import com.coderscampus.com.repository.CodingCategoryRepository;
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
 * Integration tests for the {@link CodingCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodingCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/coding-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CodingCategoryRepository codingCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodingCategoryMockMvc;

    private CodingCategory codingCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodingCategory createEntity(EntityManager em) {
        CodingCategory codingCategory = new CodingCategory().name(DEFAULT_NAME);
        return codingCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodingCategory createUpdatedEntity(EntityManager em) {
        CodingCategory codingCategory = new CodingCategory().name(UPDATED_NAME);
        return codingCategory;
    }

    @BeforeEach
    public void initTest() {
        codingCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createCodingCategory() throws Exception {
        int databaseSizeBeforeCreate = codingCategoryRepository.findAll().size();
        // Create the CodingCategory
        restCodingCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isCreated());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CodingCategory testCodingCategory = codingCategoryList.get(codingCategoryList.size() - 1);
        assertThat(testCodingCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCodingCategoryWithExistingId() throws Exception {
        // Create the CodingCategory with an existing ID
        codingCategory.setId(1L);

        int databaseSizeBeforeCreate = codingCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodingCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCodingCategories() throws Exception {
        // Initialize the database
        codingCategoryRepository.saveAndFlush(codingCategory);

        // Get all the codingCategoryList
        restCodingCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codingCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCodingCategory() throws Exception {
        // Initialize the database
        codingCategoryRepository.saveAndFlush(codingCategory);

        // Get the codingCategory
        restCodingCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, codingCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codingCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCodingCategory() throws Exception {
        // Get the codingCategory
        restCodingCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCodingCategory() throws Exception {
        // Initialize the database
        codingCategoryRepository.saveAndFlush(codingCategory);

        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();

        // Update the codingCategory
        CodingCategory updatedCodingCategory = codingCategoryRepository.findById(codingCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCodingCategory are not directly saved in db
        em.detach(updatedCodingCategory);
        updatedCodingCategory.name(UPDATED_NAME);

        restCodingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCodingCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCodingCategory))
            )
            .andExpect(status().isOk());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
        CodingCategory testCodingCategory = codingCategoryList.get(codingCategoryList.size() - 1);
        assertThat(testCodingCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCodingCategory() throws Exception {
        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();
        codingCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, codingCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCodingCategory() throws Exception {
        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();
        codingCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodingCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCodingCategory() throws Exception {
        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();
        codingCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodingCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codingCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCodingCategoryWithPatch() throws Exception {
        // Initialize the database
        codingCategoryRepository.saveAndFlush(codingCategory);

        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();

        // Update the codingCategory using partial update
        CodingCategory partialUpdatedCodingCategory = new CodingCategory();
        partialUpdatedCodingCategory.setId(codingCategory.getId());

        restCodingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodingCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodingCategory))
            )
            .andExpect(status().isOk());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
        CodingCategory testCodingCategory = codingCategoryList.get(codingCategoryList.size() - 1);
        assertThat(testCodingCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCodingCategoryWithPatch() throws Exception {
        // Initialize the database
        codingCategoryRepository.saveAndFlush(codingCategory);

        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();

        // Update the codingCategory using partial update
        CodingCategory partialUpdatedCodingCategory = new CodingCategory();
        partialUpdatedCodingCategory.setId(codingCategory.getId());

        partialUpdatedCodingCategory.name(UPDATED_NAME);

        restCodingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCodingCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodingCategory))
            )
            .andExpect(status().isOk());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
        CodingCategory testCodingCategory = codingCategoryList.get(codingCategoryList.size() - 1);
        assertThat(testCodingCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCodingCategory() throws Exception {
        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();
        codingCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, codingCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCodingCategory() throws Exception {
        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();
        codingCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCodingCategory() throws Exception {
        int databaseSizeBeforeUpdate = codingCategoryRepository.findAll().size();
        codingCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCodingCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(codingCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CodingCategory in the database
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCodingCategory() throws Exception {
        // Initialize the database
        codingCategoryRepository.saveAndFlush(codingCategory);

        int databaseSizeBeforeDelete = codingCategoryRepository.findAll().size();

        // Delete the codingCategory
        restCodingCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, codingCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodingCategory> codingCategoryList = codingCategoryRepository.findAll();
        assertThat(codingCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
