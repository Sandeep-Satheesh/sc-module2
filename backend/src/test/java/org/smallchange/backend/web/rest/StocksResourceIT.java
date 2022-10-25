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
import org.smallchange.backend.domain.Stocks;
import org.smallchange.backend.repository.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StocksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StocksResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Float DEFAULT_CURRENT_PRICE = 1F;
    private static final Float UPDATED_CURRENT_PRICE = 2F;

    private static final String DEFAULT_STOCK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStocksMockMvc;

    private Stocks stocks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stocks createEntity(EntityManager em) {
        Stocks stocks = new Stocks()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .currentPrice(DEFAULT_CURRENT_PRICE)
            .stockType(DEFAULT_STOCK_TYPE)
            .exchange(DEFAULT_EXCHANGE);
        return stocks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stocks createUpdatedEntity(EntityManager em) {
        Stocks stocks = new Stocks()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchange(UPDATED_EXCHANGE);
        return stocks;
    }

    @BeforeEach
    public void initTest() {
        stocks = createEntity(em);
    }

    @Test
    @Transactional
    void createStocks() throws Exception {
        int databaseSizeBeforeCreate = stocksRepository.findAll().size();
        // Create the Stocks
        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isCreated());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeCreate + 1);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStocks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(DEFAULT_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(DEFAULT_STOCK_TYPE);
        assertThat(testStocks.getExchange()).isEqualTo(DEFAULT_EXCHANGE);
    }

    @Test
    @Transactional
    void createStocksWithExistingId() throws Exception {
        // Create the Stocks with an existing ID
        stocks.setId(1L);

        int databaseSizeBeforeCreate = stocksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStocksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get all the stocksList
        restStocksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stocks.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].currentPrice").value(hasItem(DEFAULT_CURRENT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].stockType").value(hasItem(DEFAULT_STOCK_TYPE)))
            .andExpect(jsonPath("$.[*].exchange").value(hasItem(DEFAULT_EXCHANGE)));
    }

    @Test
    @Transactional
    void getStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        // Get the stocks
        restStocksMockMvc
            .perform(get(ENTITY_API_URL_ID, stocks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stocks.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.currentPrice").value(DEFAULT_CURRENT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.stockType").value(DEFAULT_STOCK_TYPE))
            .andExpect(jsonPath("$.exchange").value(DEFAULT_EXCHANGE));
    }

    @Test
    @Transactional
    void getNonExistingStocks() throws Exception {
        // Get the stocks
        restStocksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks
        Stocks updatedStocks = stocksRepository.findById(stocks.getId()).get();
        // Disconnect from session so that the updates on updatedStocks are not directly saved in db
        em.detach(updatedStocks);
        updatedStocks
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStocks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStocks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(UPDATED_STOCK_TYPE);
        assertThat(testStocks.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void putNonExistingStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stocks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStocksWithPatch() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks using partial update
        Stocks partialUpdatedStocks = new Stocks();
        partialUpdatedStocks.setId(stocks.getId());

        partialUpdatedStocks
            .code(UPDATED_CODE)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStocks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(UPDATED_STOCK_TYPE);
        assertThat(testStocks.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void fullUpdateStocksWithPatch() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();

        // Update the stocks using partial update
        Stocks partialUpdatedStocks = new Stocks();
        partialUpdatedStocks.setId(stocks.getId());

        partialUpdatedStocks
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .currentPrice(UPDATED_CURRENT_PRICE)
            .stockType(UPDATED_STOCK_TYPE)
            .exchange(UPDATED_EXCHANGE);

        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStocks))
            )
            .andExpect(status().isOk());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
        Stocks testStocks = stocksList.get(stocksList.size() - 1);
        assertThat(testStocks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStocks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStocks.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStocks.getCurrentPrice()).isEqualTo(UPDATED_CURRENT_PRICE);
        assertThat(testStocks.getStockType()).isEqualTo(UPDATED_STOCK_TYPE);
        assertThat(testStocks.getExchange()).isEqualTo(UPDATED_EXCHANGE);
    }

    @Test
    @Transactional
    void patchNonExistingStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stocks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStocks() throws Exception {
        int databaseSizeBeforeUpdate = stocksRepository.findAll().size();
        stocks.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStocksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stocks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Stocks in the database
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStocks() throws Exception {
        // Initialize the database
        stocksRepository.saveAndFlush(stocks);

        int databaseSizeBeforeDelete = stocksRepository.findAll().size();

        // Delete the stocks
        restStocksMockMvc
            .perform(delete(ENTITY_API_URL_ID, stocks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stocks> stocksList = stocksRepository.findAll();
        assertThat(stocksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
