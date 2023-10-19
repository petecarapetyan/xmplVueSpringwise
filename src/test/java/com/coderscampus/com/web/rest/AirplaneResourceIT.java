package com.coderscampus.com.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.coderscampus.com.IntegrationTest;
import com.coderscampus.com.domain.Airplane;
import com.coderscampus.com.repository.AirplaneRepository;
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
 * Integration tests for the {@link AirplaneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AirplaneResourceIT {

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/airplanes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAirplaneMockMvc;

    private Airplane airplane;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Airplane createEntity(EntityManager em) {
        Airplane airplane = new Airplane().model(DEFAULT_MODEL).make(DEFAULT_MAKE).color(DEFAULT_COLOR);
        return airplane;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Airplane createUpdatedEntity(EntityManager em) {
        Airplane airplane = new Airplane().model(UPDATED_MODEL).make(UPDATED_MAKE).color(UPDATED_COLOR);
        return airplane;
    }

    @BeforeEach
    public void initTest() {
        airplane = createEntity(em);
    }

    @Test
    @Transactional
    void createAirplane() throws Exception {
        int databaseSizeBeforeCreate = airplaneRepository.findAll().size();
        // Create the Airplane
        restAirplaneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isCreated());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeCreate + 1);
        Airplane testAirplane = airplaneList.get(airplaneList.size() - 1);
        assertThat(testAirplane.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testAirplane.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testAirplane.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    void createAirplaneWithExistingId() throws Exception {
        // Create the Airplane with an existing ID
        airplane.setId(1L);

        int databaseSizeBeforeCreate = airplaneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirplaneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAirplanes() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        // Get all the airplaneList
        restAirplaneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airplane.getId().intValue())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)));
    }

    @Test
    @Transactional
    void getAirplane() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        // Get the airplane
        restAirplaneMockMvc
            .perform(get(ENTITY_API_URL_ID, airplane.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(airplane.getId().intValue()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR));
    }

    @Test
    @Transactional
    void getNonExistingAirplane() throws Exception {
        // Get the airplane
        restAirplaneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAirplane() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();

        // Update the airplane
        Airplane updatedAirplane = airplaneRepository.findById(airplane.getId()).get();
        // Disconnect from session so that the updates on updatedAirplane are not directly saved in db
        em.detach(updatedAirplane);
        updatedAirplane.model(UPDATED_MODEL).make(UPDATED_MAKE).color(UPDATED_COLOR);

        restAirplaneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAirplane.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAirplane))
            )
            .andExpect(status().isOk());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
        Airplane testAirplane = airplaneList.get(airplaneList.size() - 1);
        assertThat(testAirplane.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAirplane.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testAirplane.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void putNonExistingAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();
        airplane.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirplaneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, airplane.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(airplane))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();
        airplane.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirplaneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(airplane))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();
        airplane.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirplaneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAirplaneWithPatch() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();

        // Update the airplane using partial update
        Airplane partialUpdatedAirplane = new Airplane();
        partialUpdatedAirplane.setId(airplane.getId());

        partialUpdatedAirplane.model(UPDATED_MODEL).make(UPDATED_MAKE).color(UPDATED_COLOR);

        restAirplaneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAirplane.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAirplane))
            )
            .andExpect(status().isOk());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
        Airplane testAirplane = airplaneList.get(airplaneList.size() - 1);
        assertThat(testAirplane.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAirplane.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testAirplane.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void fullUpdateAirplaneWithPatch() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();

        // Update the airplane using partial update
        Airplane partialUpdatedAirplane = new Airplane();
        partialUpdatedAirplane.setId(airplane.getId());

        partialUpdatedAirplane.model(UPDATED_MODEL).make(UPDATED_MAKE).color(UPDATED_COLOR);

        restAirplaneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAirplane.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAirplane))
            )
            .andExpect(status().isOk());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
        Airplane testAirplane = airplaneList.get(airplaneList.size() - 1);
        assertThat(testAirplane.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAirplane.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testAirplane.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    void patchNonExistingAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();
        airplane.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirplaneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, airplane.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(airplane))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();
        airplane.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirplaneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(airplane))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAirplane() throws Exception {
        int databaseSizeBeforeUpdate = airplaneRepository.findAll().size();
        airplane.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirplaneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(airplane)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Airplane in the database
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAirplane() throws Exception {
        // Initialize the database
        airplaneRepository.saveAndFlush(airplane);

        int databaseSizeBeforeDelete = airplaneRepository.findAll().size();

        // Delete the airplane
        restAirplaneMockMvc
            .perform(delete(ENTITY_API_URL_ID, airplane.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Airplane> airplaneList = airplaneRepository.findAll();
        assertThat(airplaneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
