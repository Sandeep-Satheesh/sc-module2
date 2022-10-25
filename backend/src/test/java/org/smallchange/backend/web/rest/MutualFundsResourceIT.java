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
import org.smallchange.backend.domain.MutualFunds;
import org.smallchange.backend.repository.MutualFundsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MutualFundsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MutualFundsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Float DEFAULT_CURRENT_PRICE = 1F;
    private static final Float UPDATED_CURRENT_PRICE = 2F;

    private static final String DEFAULT_MF_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MF_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_INTEREST_RATE = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_RATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mutual-funds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MutualFundsRepository mutualFundsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMutualFundsMockMvc;

    private MutualFunds mutualFunds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MutualFunds createEntity(EntityManager em) {
        MutualFunds mutualFunds = new MutualFunds()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .mfType(DEFAULT_MF_TYPE)
            .interestRate(DEFAULT_INTEREST_RATE);
        return mutualFunds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MutualFunds createUpdatedEntity(EntityManager em) {
        MutualFunds mutualFunds = new MutualFunds()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .mfType(UPDATED_MF_TYPE)
            .interestRate(UPDATED_INTEREST_RATE);
        return mutualFunds;
    }

    @BeforeEach
    public void initTest() {
        mutualFunds = createEntity(em);
    }

    @Test
    @Transactional
    void createMutualFunds() throws Exception {
        int databaseSizeBeforeCreate = mutualFundsRepository.findAll().size();
        // Create the MutualFunds
        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isCreated());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeCreate + 1);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMutualFunds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(DEFAULT_MF_TYPE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
    }

    @Test
    @Transactional
    void createMutualFundsWithExistingId() throws Exception {
        // Create the MutualFunds with an existing ID
        mutualFunds.setId(1L);

        int databaseSizeBeforeCreate = mutualFundsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMutualFundsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMutualFunds() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get all the mutualFundsList
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mutualFunds.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].mfType").value(hasItem(DEFAULT_MF_TYPE)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE)));
    }

    @Test
    @Transactional
    void getMutualFunds() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        // Get the mutualFunds
        restMutualFundsMockMvc
            .perform(get(ENTITY_API_URL_ID, mutualFunds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mutualFunds.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.mfType").value(DEFAULT_MF_TYPE))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE));
    }

    @Test
    @Transactional
    void getNonExistingMutualFunds() throws Exception {
        // Get the mutualFunds
        restMutualFundsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMutualFunds() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();

        // Update the mutualFunds
        MutualFunds updatedMutualFunds = mutualFundsRepository.findById(mutualFunds.getId()).get();
        // Disconnect from session so that the updates on updatedMutualFunds are not directly saved in db
        em.detach(updatedMutualFunds);
        updatedMutualFunds
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .mfType(UPDATED_MF_TYPE)
            .interestRate(UPDATED_INTEREST_RATE);

        restMutualFundsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMutualFunds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMutualFunds))
            )
            .andExpect(status().isOk());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMutualFunds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(UPDATED_MF_TYPE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void putNonExistingMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mutualFunds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mutualFunds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMutualFundsWithPatch() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();

        // Update the mutualFunds using partial update
        MutualFunds partialUpdatedMutualFunds = new MutualFunds();
        partialUpdatedMutualFunds.setId(mutualFunds.getId());

        partialUpdatedMutualFunds.currentPrice(UPDATED_CURRENT_PRICE).mfType(UPDATED_MF_TYPE);

        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMutualFunds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMutualFunds))
            )
            .andExpect(status().isOk());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMutualFunds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(UPDATED_MF_TYPE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
    }

    @Test
    @Transactional
    void fullUpdateMutualFundsWithPatch() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();

        // Update the mutualFunds using partial update
        MutualFunds partialUpdatedMutualFunds = new MutualFunds();
        partialUpdatedMutualFunds.setId(mutualFunds.getId());

        partialUpdatedMutualFunds
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .mfType(UPDATED_MF_TYPE)
            .interestRate(UPDATED_INTEREST_RATE);

        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMutualFunds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMutualFunds))
            )
            .andExpect(status().isOk());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
        MutualFunds testMutualFunds = mutualFundsList.get(mutualFundsList.size() - 1);
        assertThat(testMutualFunds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMutualFunds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMutualFunds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMutualFunds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testMutualFunds.getMfType()).isEqualTo(UPDATED_MF_TYPE);
        assertThat(testMutualFunds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void patchNonExistingMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mutualFunds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isBadRequest());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMutualFunds() throws Exception {
        int databaseSizeBeforeUpdate = mutualFundsRepository.findAll().size();
        mutualFunds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMutualFundsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mutualFunds))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MutualFunds in the database
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMutualFunds() throws Exception {
        // Initialize the database
        mutualFundsRepository.saveAndFlush(mutualFunds);

        int databaseSizeBeforeDelete = mutualFundsRepository.findAll().size();

        // Delete the mutualFunds
        restMutualFundsMockMvc
            .perform(delete(ENTITY_API_URL_ID, mutualFunds.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MutualFunds> mutualFundsList = mutualFundsRepository.findAll();
        assertThat(mutualFundsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
