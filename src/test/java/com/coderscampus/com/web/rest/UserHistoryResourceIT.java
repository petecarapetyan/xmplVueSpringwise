package com.coderscampus.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coderscampus.com.IntegrationTest;
import com.coderscampus.com.domain.UserHistory;
import com.coderscampus.com.repository.UserHistoryRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link UserHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserHistoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISSUE = "AAAAAAAAAA";
    private static final String UPDATED_ISSUE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/user-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserHistoryMockMvc;

    private UserHistory userHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserHistory createEntity(EntityManager em) {
        UserHistory userHistory = new UserHistory().name(DEFAULT_NAME).issue(DEFAULT_ISSUE).issueDate(DEFAULT_ISSUE_DATE);
        return userHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserHistory createUpdatedEntity(EntityManager em) {
        UserHistory userHistory = new UserHistory().name(UPDATED_NAME).issue(UPDATED_ISSUE).issueDate(UPDATED_ISSUE_DATE);
        return userHistory;
    }

    @BeforeEach
    public void initTest() {
        userHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createUserHistory() throws Exception {
        int databaseSizeBeforeCreate = userHistoryRepository.findAll().size();
        // Create the UserHistory
        restUserHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userHistory)))
            .andExpect(status().isCreated());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        UserHistory testUserHistory = userHistoryList.get(userHistoryList.size() - 1);
        assertThat(testUserHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserHistory.getIssue()).isEqualTo(DEFAULT_ISSUE);
        assertThat(testUserHistory.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
    }

    @Test
    @Transactional
    void createUserHistoryWithExistingId() throws Exception {
        // Create the UserHistory with an existing ID
        userHistory.setId(1L);

        int databaseSizeBeforeCreate = userHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userHistory)))
            .andExpect(status().isBadRequest());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserHistories() throws Exception {
        // Initialize the database
        userHistoryRepository.saveAndFlush(userHistory);

        // Get all the userHistoryList
        restUserHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].issue").value(hasItem(DEFAULT_ISSUE)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())));
    }

    @Test
    @Transactional
    void getUserHistory() throws Exception {
        // Initialize the database
        userHistoryRepository.saveAndFlush(userHistory);

        // Get the userHistory
        restUserHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, userHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userHistory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.issue").value(DEFAULT_ISSUE))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserHistory() throws Exception {
        // Get the userHistory
        restUserHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserHistory() throws Exception {
        // Initialize the database
        userHistoryRepository.saveAndFlush(userHistory);

        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();

        // Update the userHistory
        UserHistory updatedUserHistory = userHistoryRepository.findById(userHistory.getId()).get();
        // Disconnect from session so that the updates on updatedUserHistory are not directly saved in db
        em.detach(updatedUserHistory);
        updatedUserHistory.name(UPDATED_NAME).issue(UPDATED_ISSUE).issueDate(UPDATED_ISSUE_DATE);

        restUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserHistory))
            )
            .andExpect(status().isOk());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
        UserHistory testUserHistory = userHistoryList.get(userHistoryList.size() - 1);
        assertThat(testUserHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserHistory.getIssue()).isEqualTo(UPDATED_ISSUE);
        assertThat(testUserHistory.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();
        userHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userHistory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();
        userHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();
        userHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userHistory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserHistoryWithPatch() throws Exception {
        // Initialize the database
        userHistoryRepository.saveAndFlush(userHistory);

        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();

        // Update the userHistory using partial update
        UserHistory partialUpdatedUserHistory = new UserHistory();
        partialUpdatedUserHistory.setId(userHistory.getId());

        partialUpdatedUserHistory.name(UPDATED_NAME).issue(UPDATED_ISSUE).issueDate(UPDATED_ISSUE_DATE);

        restUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserHistory))
            )
            .andExpect(status().isOk());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
        UserHistory testUserHistory = userHistoryList.get(userHistoryList.size() - 1);
        assertThat(testUserHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserHistory.getIssue()).isEqualTo(UPDATED_ISSUE);
        assertThat(testUserHistory.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUserHistoryWithPatch() throws Exception {
        // Initialize the database
        userHistoryRepository.saveAndFlush(userHistory);

        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();

        // Update the userHistory using partial update
        UserHistory partialUpdatedUserHistory = new UserHistory();
        partialUpdatedUserHistory.setId(userHistory.getId());

        partialUpdatedUserHistory.name(UPDATED_NAME).issue(UPDATED_ISSUE).issueDate(UPDATED_ISSUE_DATE);

        restUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserHistory))
            )
            .andExpect(status().isOk());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
        UserHistory testUserHistory = userHistoryList.get(userHistoryList.size() - 1);
        assertThat(testUserHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserHistory.getIssue()).isEqualTo(UPDATED_ISSUE);
        assertThat(testUserHistory.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();
        userHistory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();
        userHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userHistory))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = userHistoryRepository.findAll().size();
        userHistory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userHistory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserHistory in the database
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserHistory() throws Exception {
        // Initialize the database
        userHistoryRepository.saveAndFlush(userHistory);

        int databaseSizeBeforeDelete = userHistoryRepository.findAll().size();

        // Delete the userHistory
        restUserHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, userHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserHistory> userHistoryList = userHistoryRepository.findAll();
        assertThat(userHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
