package com.coderscampus.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coderscampus.com.IntegrationTest;
import com.coderscampus.com.domain.SpringProject;
import com.coderscampus.com.repository.SpringProjectRepository;
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
 * Integration tests for the {@link SpringProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpringProjectResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/spring-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpringProjectRepository springProjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpringProjectMockMvc;

    private SpringProject springProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpringProject createEntity(EntityManager em) {
        SpringProject springProject = new SpringProject()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imagePath(DEFAULT_IMAGE_PATH)
            .url(DEFAULT_URL);
        return springProject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpringProject createUpdatedEntity(EntityManager em) {
        SpringProject springProject = new SpringProject()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imagePath(UPDATED_IMAGE_PATH)
            .url(UPDATED_URL);
        return springProject;
    }

    @BeforeEach
    public void initTest() {
        springProject = createEntity(em);
    }

    @Test
    @Transactional
    void createSpringProject() throws Exception {
        int databaseSizeBeforeCreate = springProjectRepository.findAll().size();
        // Create the SpringProject
        restSpringProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(springProject)))
            .andExpect(status().isCreated());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeCreate + 1);
        SpringProject testSpringProject = springProjectList.get(springProjectList.size() - 1);
        assertThat(testSpringProject.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSpringProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSpringProject.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testSpringProject.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    void createSpringProjectWithExistingId() throws Exception {
        // Create the SpringProject with an existing ID
        springProject.setId(1L);

        int databaseSizeBeforeCreate = springProjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpringProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(springProject)))
            .andExpect(status().isBadRequest());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSpringProjects() throws Exception {
        // Initialize the database
        springProjectRepository.saveAndFlush(springProject);

        // Get all the springProjectList
        restSpringProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(springProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    void getSpringProject() throws Exception {
        // Initialize the database
        springProjectRepository.saveAndFlush(springProject);

        // Get the springProject
        restSpringProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, springProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(springProject.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    void getNonExistingSpringProject() throws Exception {
        // Get the springProject
        restSpringProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSpringProject() throws Exception {
        // Initialize the database
        springProjectRepository.saveAndFlush(springProject);

        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();

        // Update the springProject
        SpringProject updatedSpringProject = springProjectRepository.findById(springProject.getId()).get();
        // Disconnect from session so that the updates on updatedSpringProject are not directly saved in db
        em.detach(updatedSpringProject);
        updatedSpringProject.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).imagePath(UPDATED_IMAGE_PATH).url(UPDATED_URL);

        restSpringProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpringProject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpringProject))
            )
            .andExpect(status().isOk());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
        SpringProject testSpringProject = springProjectList.get(springProjectList.size() - 1);
        assertThat(testSpringProject.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSpringProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpringProject.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testSpringProject.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void putNonExistingSpringProject() throws Exception {
        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();
        springProject.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpringProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, springProject.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(springProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpringProject() throws Exception {
        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();
        springProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpringProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(springProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpringProject() throws Exception {
        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();
        springProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpringProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(springProject)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpringProjectWithPatch() throws Exception {
        // Initialize the database
        springProjectRepository.saveAndFlush(springProject);

        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();

        // Update the springProject using partial update
        SpringProject partialUpdatedSpringProject = new SpringProject();
        partialUpdatedSpringProject.setId(springProject.getId());

        partialUpdatedSpringProject.description(UPDATED_DESCRIPTION).imagePath(UPDATED_IMAGE_PATH).url(UPDATED_URL);

        restSpringProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpringProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpringProject))
            )
            .andExpect(status().isOk());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
        SpringProject testSpringProject = springProjectList.get(springProjectList.size() - 1);
        assertThat(testSpringProject.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSpringProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpringProject.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testSpringProject.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void fullUpdateSpringProjectWithPatch() throws Exception {
        // Initialize the database
        springProjectRepository.saveAndFlush(springProject);

        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();

        // Update the springProject using partial update
        SpringProject partialUpdatedSpringProject = new SpringProject();
        partialUpdatedSpringProject.setId(springProject.getId());

        partialUpdatedSpringProject.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).imagePath(UPDATED_IMAGE_PATH).url(UPDATED_URL);

        restSpringProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpringProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpringProject))
            )
            .andExpect(status().isOk());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
        SpringProject testSpringProject = springProjectList.get(springProjectList.size() - 1);
        assertThat(testSpringProject.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSpringProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpringProject.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testSpringProject.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    void patchNonExistingSpringProject() throws Exception {
        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();
        springProject.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpringProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, springProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(springProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpringProject() throws Exception {
        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();
        springProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpringProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(springProject))
            )
            .andExpect(status().isBadRequest());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpringProject() throws Exception {
        int databaseSizeBeforeUpdate = springProjectRepository.findAll().size();
        springProject.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpringProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(springProject))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SpringProject in the database
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpringProject() throws Exception {
        // Initialize the database
        springProjectRepository.saveAndFlush(springProject);

        int databaseSizeBeforeDelete = springProjectRepository.findAll().size();

        // Delete the springProject
        restSpringProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, springProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpringProject> springProjectList = springProjectRepository.findAll();
        assertThat(springProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
