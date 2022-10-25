package org.smallchange.backend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smallchange.backend.IntegrationTest;
import org.smallchange.backend.domain.Preferences;
import org.smallchange.backend.repository.PreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PreferencesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferencesResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTMENT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_INVESTMENT_PURPOSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RISK_TOLERANCE = 1;
    private static final Integer UPDATED_RISK_TOLERANCE = 2;

    private static final String DEFAULT_INCOME_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTMENT_LENGTH = "AAAAAAAAAA";
    private static final String UPDATED_INVESTMENT_LENGTH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PreferencesRepository preferencesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferencesMockMvc;

    private Preferences preferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preferences createEntity(EntityManager em) {
        Preferences preferences = new Preferences()
            .userId(DEFAULT_USER_ID)
            .investmentPurpose(DEFAULT_INVESTMENT_PURPOSE)
            .riskTolerance(DEFAULT_RISK_TOLERANCE)
            .incomeCategory(DEFAULT_INCOME_CATEGORY)
            .investmentLength(DEFAULT_INVESTMENT_LENGTH);
        return preferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preferences createUpdatedEntity(EntityManager em) {
        Preferences preferences = new Preferences()
            .userId(UPDATED_USER_ID)
            .investmentPurpose(UPDATED_INVESTMENT_PURPOSE)
            .riskTolerance(UPDATED_RISK_TOLERANCE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .investmentLength(UPDATED_INVESTMENT_LENGTH);
        return preferences;
    }

    @BeforeEach
    public void initTest() {
        preferences = createEntity(em);
    }

    @Test
    @Transactional
    void createPreferences() throws Exception {
        int databaseSizeBeforeCreate = preferencesRepository.findAll().size();
        // Create the Preferences
        restPreferencesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferences)))
            .andExpect(status().isCreated());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeCreate + 1);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(DEFAULT_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(DEFAULT_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(DEFAULT_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(DEFAULT_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void createPreferencesWithExistingId() throws Exception {
        // Create the Preferences with an existing ID
        preferences.setId(1L);

        int databaseSizeBeforeCreate = preferencesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferencesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferences)))
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPreferences() throws Exception {
        // Initialize the database
        preferencesRepository.saveAndFlush(preferences);

        // Get all the preferencesList
        restPreferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].investmentPurpose").value(hasItem(DEFAULT_INVESTMENT_PURPOSE)))
            .andExpect(jsonPath("$.[*].riskTolerance").value(hasItem(DEFAULT_RISK_TOLERANCE)))
            .andExpect(jsonPath("$.[*].incomeCategory").value(hasItem(DEFAULT_INCOME_CATEGORY)))
            .andExpect(jsonPath("$.[*].investmentLength").value(hasItem(DEFAULT_INVESTMENT_LENGTH)));
    }

    @Test
    @Transactional
    void getPreferences() throws Exception {
        // Initialize the database
        preferencesRepository.saveAndFlush(preferences);

        // Get the preferences
        restPreferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, preferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferences.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.investmentPurpose").value(DEFAULT_INVESTMENT_PURPOSE))
            .andExpect(jsonPath("$.riskTolerance").value(DEFAULT_RISK_TOLERANCE))
            .andExpect(jsonPath("$.incomeCategory").value(DEFAULT_INCOME_CATEGORY))
            .andExpect(jsonPath("$.investmentLength").value(DEFAULT_INVESTMENT_LENGTH));
    }

    @Test
    @Transactional
    void getNonExistingPreferences() throws Exception {
        // Get the preferences
        restPreferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPreferences() throws Exception {
        // Initialize the database
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();

        // Update the preferences
        Preferences updatedPreferences = preferencesRepository.findById(preferences.getId()).get();
        // Disconnect from session so that the updates on updatedPreferences are not directly saved in db
        em.detach(updatedPreferences);
        updatedPreferences
            .userId(UPDATED_USER_ID)
            .investmentPurpose(UPDATED_INVESTMENT_PURPOSE)
            .riskTolerance(UPDATED_RISK_TOLERANCE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .investmentLength(UPDATED_INVESTMENT_LENGTH);

        restPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPreferences.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPreferences))
            )
            .andExpect(status().isOk());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(UPDATED_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(UPDATED_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(UPDATED_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(UPDATED_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void putNonExistingPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferences.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferences)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePreferencesWithPatch() throws Exception {
        // Initialize the database
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();

        // Update the preferences using partial update
        Preferences partialUpdatedPreferences = new Preferences();
        partialUpdatedPreferences.setId(preferences.getId());

        partialUpdatedPreferences.investmentPurpose(UPDATED_INVESTMENT_PURPOSE).investmentLength(UPDATED_INVESTMENT_LENGTH);

        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferences))
            )
            .andExpect(status().isOk());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(UPDATED_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(DEFAULT_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(DEFAULT_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(UPDATED_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void fullUpdatePreferencesWithPatch() throws Exception {
        // Initialize the database
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();

        // Update the preferences using partial update
        Preferences partialUpdatedPreferences = new Preferences();
        partialUpdatedPreferences.setId(preferences.getId());

        partialUpdatedPreferences
            .userId(UPDATED_USER_ID)
            .investmentPurpose(UPDATED_INVESTMENT_PURPOSE)
            .riskTolerance(UPDATED_RISK_TOLERANCE)
            .incomeCategory(UPDATED_INCOME_CATEGORY)
            .investmentLength(UPDATED_INVESTMENT_LENGTH);

        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferences))
            )
            .andExpect(status().isOk());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
        Preferences testPreferences = preferencesList.get(preferencesList.size() - 1);
        assertThat(testPreferences.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPreferences.getInvestmentPurpose()).isEqualTo(UPDATED_INVESTMENT_PURPOSE);
        assertThat(testPreferences.getRiskTolerance()).isEqualTo(UPDATED_RISK_TOLERANCE);
        assertThat(testPreferences.getIncomeCategory()).isEqualTo(UPDATED_INCOME_CATEGORY);
        assertThat(testPreferences.getInvestmentLength()).isEqualTo(UPDATED_INVESTMENT_LENGTH);
    }

    @Test
    @Transactional
    void patchNonExistingPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isBadRequest());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPreferences() throws Exception {
        int databaseSizeBeforeUpdate = preferencesRepository.findAll().size();
        preferences.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferencesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(preferences))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Preferences in the database
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePreferences() throws Exception {
        // Initialize the database
        preferencesRepository.saveAndFlush(preferences);

        int databaseSizeBeforeDelete = preferencesRepository.findAll().size();

        // Delete the preferences
        restPreferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferences.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Preferences> preferencesList = preferencesRepository.findAll();
        assertThat(preferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
