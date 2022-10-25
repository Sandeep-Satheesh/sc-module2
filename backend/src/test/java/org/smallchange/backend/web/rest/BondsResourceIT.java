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
import org.smallchange.backend.domain.Bonds;
import org.smallchange.backend.repository.BondsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BondsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BondsResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Float DEFAULT_CURRENT_PRICE = 1F;
    private static final Float UPDATED_CURRENT_PRICE = 2F;

    private static final Float DEFAULT_INTEREST_RATE = 1F;
    private static final Float UPDATED_INTEREST_RATE = 2F;

    private static final Integer DEFAULT_DURATION_MONTHS = 1;
    private static final Integer UPDATED_DURATION_MONTHS = 2;

    private static final String DEFAULT_BOND_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BOND_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bonds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BondsRepository bondsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBondsMockMvc;

    private Bonds bonds;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonds createEntity(EntityManager em) {
        Bonds bonds = new Bonds()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .durationMonths(DEFAULT_DURATION_MONTHS)
            .bondType(DEFAULT_BOND_TYPE)
            .exchange(DEFAULT_EXCHANGE);
        return bonds;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonds createUpdatedEntity(EntityManager em) {
        Bonds bonds = new Bonds()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .bondType(UPDATED_BOND_TYPE)
            .exchange(UPDATED_EXCHANGE);
        return bonds;
    }

    @BeforeEach
    public void initTest() {
        bonds = createEntity(em);
    }

    @Test
    @Transactional
    void createBonds() throws Exception {
        int databaseSizeBeforeCreate = bondsRepository.findAll().size();
        // Create the Bonds
        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isCreated());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeCreate + 1);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBonds.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(DEFAULT_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(DEFAULT_BOND_TYPE);
        assertThat(testBonds.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createBondsWithExistingId() throws Exception {
        // Create the Bonds with an existing ID
        bonds.setId(1L);

        int databaseSizeBeforeCreate = bondsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBondsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBonds() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get all the bondsList
        restBondsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonds.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].durationMonths").value(hasItem(DEFAULT_DURATION_MONTHS)))
            .andExpect(jsonPath("$.[*].bondType").value(hasItem(DEFAULT_BOND_TYPE)))
            .andExpect(jsonPath("$.[*].exchange").value(hasItem(DEFAULT_EXCHANGE)));
    }

    @Test
    @Transactional
    void getBonds() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        // Get the bonds
        restBondsMockMvc
            .perform(get(ENTITY_API_URL_ID, bonds.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonds.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.durationMonths").value(DEFAULT_DURATION_MONTHS))
            .andExpect(jsonPath("$.bondType").value(DEFAULT_BOND_TYPE))
            .andExpect(jsonPath("$.exchange").value(DEFAULT_EXCHANGE));
    }

    @Test
    @Transactional
    void getNonExistingBonds() throws Exception {
        // Get the bonds
        restBondsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBonds() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();

        // Update the bonds
        Bonds updatedBonds = bondsRepository.findById(bonds.getId()).get();
        // Disconnect from session so that the updates on updatedBonds are not directly saved in db
        em.detach(updatedBonds);
        updatedBonds
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .bondType(UPDATED_BOND_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restBondsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBonds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBonds))
            )
            .andExpect(status().isOk());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBonds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(UPDATED_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(UPDATED_BOND_TYPE);
        assertThat(testBonds.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bonds.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBondsWithPatch() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();

        // Update the bonds using partial update
        Bonds partialUpdatedBonds = new Bonds();
        partialUpdatedBonds.setId(bonds.getId());

        partialUpdatedBonds.code(UPDATED_CODE).name(UPDATED_NAME).interestRate(UPDATED_INTEREST_RATE).bondType(UPDATED_BOND_TYPE);

        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonds))
            )
            .andExpect(status().isOk());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBonds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(DEFAULT_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(UPDATED_BOND_TYPE);
        assertThat(testBonds.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateBondsWithPatch() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();

        // Update the bonds using partial update
        Bonds partialUpdatedBonds = new Bonds();
        partialUpdatedBonds.setId(bonds.getId());

        partialUpdatedBonds
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .interestRate(UPDATED_INTEREST_RATE)
            .durationMonths(UPDATED_DURATION_MONTHS)
            .bondType(UPDATED_BOND_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBonds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBonds))
            )
            .andExpect(status().isOk());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
        Bonds testBonds = bondsList.get(bondsList.size() - 1);
        assertThat(testBonds.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBonds.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBonds.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBonds.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testBonds.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testBonds.getDurationMonths()).isEqualTo(UPDATED_DURATION_MONTHS);
        assertThat(testBonds.getBondType()).isEqualTo(UPDATED_BOND_TYPE);
        assertThat(testBonds.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bonds.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bonds))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBonds() throws Exception {
        int databaseSizeBeforeUpdate = bondsRepository.findAll().size();
        bonds.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBondsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bonds)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bonds in the database
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBonds() throws Exception {
        // Initialize the database
        bondsRepository.saveAndFlush(bonds);

        int databaseSizeBeforeDelete = bondsRepository.findAll().size();

        // Delete the bonds
        restBondsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bonds.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bonds> bondsList = bondsRepository.findAll();
        assertThat(bondsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
