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
import org.smallchange.backend.domain.Account;
import org.smallchange.backend.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountResourceIT {
/*
    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACC_NO = 1;
    private static final Integer UPDATED_ACC_NO = 2;

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACC_TYPE = "BBBBBBBBBB";

    private static final Float DEFAULT_ACC_BALANCE = 1F;
    private static final Float UPDATED_ACC_BALANCE = 2F;

    private static final String ENTITY_API_URL = "/api/bank-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankAccountMockMvc;

    private Account bankAccount;

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static Account createEntity(EntityManager em) {
        Account bankAccount = new Account()
            .userId(DEFAULT_USER_ID)
            .accNo(DEFAULT_ACC_NO)
            .bankName(DEFAULT_BANK_NAME)
            .accType(DEFAULT_ACC_TYPE)
            .accBalance(DEFAULT_ACC_BALANCE);
        return bankAccount;
    }

    *//**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static Account createUpdatedEntity(EntityManager em) {
        Account bankAccount = new Account()
            .userId(UPDATED_USER_ID)
            .accNo(UPDATED_ACC_NO)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);
        return bankAccount;
    }

    @BeforeEach
    public void initTest() {
        bankAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createBankAccount() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();
        // Create the BankAccount
        restBankAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isCreated());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate + 1);
        Account testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBankAccount.getAccNo()).isEqualTo(DEFAULT_ACC_NO);
        assertThat(testBankAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankAccount.getAccType()).isEqualTo(DEFAULT_ACC_TYPE);
        assertThat(testBankAccount.getAccBalance()).isEqualTo(DEFAULT_ACC_BALANCE);
    }

    @Test
    @Transactional
    void createBankAccountWithExistingId() throws Exception {
        // Create the BankAccount with an existing ID
        bankAccount.setId(1L);

        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get all the bankAccountList
        restBankAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].accNo").value(hasItem(DEFAULT_ACC_NO)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accType").value(hasItem(DEFAULT_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].accBalance").value(hasItem(DEFAULT_ACC_BALANCE.doubleValue())));
    }

    @Test
    @Transactional
    void getBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, bankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccount.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.accNo").value(DEFAULT_ACC_NO))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accType").value(DEFAULT_ACC_TYPE))
            .andExpect(jsonPath("$.accBalance").value(DEFAULT_ACC_BALANCE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingBankAccount() throws Exception {
        // Get the bankAccount
        restBankAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount
        Account updatedBankAccount = bankAccountRepository.findById(bankAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccount are not directly saved in db
        em.detach(updatedBankAccount);
        updatedBankAccount
            .userId(UPDATED_USER_ID)
            .accNo(UPDATED_ACC_NO)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);

        restBankAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankAccount))
            )
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        Account testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBankAccount.getAccNo()).isEqualTo(UPDATED_ACC_NO);
        assertThat(testBankAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testBankAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankAccount.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankAccountWithPatch() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount using partial update
        Account partialUpdatedBankAccount = new Account();
        partialUpdatedBankAccount.setId(bankAccount.getId());

        partialUpdatedBankAccount.userId(UPDATED_USER_ID).accNo(UPDATED_ACC_NO).accType(UPDATED_ACC_TYPE);

        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccount))
            )
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        Account testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBankAccount.getAccNo()).isEqualTo(UPDATED_ACC_NO);
        assertThat(testBankAccount.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testBankAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testBankAccount.getAccBalance()).isEqualTo(DEFAULT_ACC_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateBankAccountWithPatch() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount using partial update
        Account partialUpdatedBankAccount = new Account();
        partialUpdatedBankAccount.setId(bankAccount.getId());

        partialUpdatedBankAccount
            .userId(UPDATED_USER_ID)
            .accNo(UPDATED_ACC_NO)
            .bankName(UPDATED_BANK_NAME)
            .accType(UPDATED_ACC_TYPE)
            .accBalance(UPDATED_ACC_BALANCE);

        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankAccount))
            )
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        Account testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBankAccount.getAccNo()).isEqualTo(UPDATED_ACC_NO);
        assertThat(testBankAccount.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testBankAccount.getAccType()).isEqualTo(UPDATED_ACC_TYPE);
        assertThat(testBankAccount.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();
        bankAccount.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankAccount))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankAccount in the database
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeDelete = bankAccountRepository.findAll().size();

        // Delete the bankAccount
        restBankAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Account> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}
