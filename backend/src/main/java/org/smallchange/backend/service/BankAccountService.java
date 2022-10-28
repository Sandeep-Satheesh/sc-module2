package org.smallchange.backend.service;

import java.util.List;
import java.util.Optional;

import org.smallchange.backend.domain.Account;

/**
 * Service Interface for managing {@link Account}.
 */
public interface BankAccountService {
    /**
     * Save a bankAccount.
     *
     * @param bankAccount the entity to save.
     * @return the persisted entity.
     */
    Account save(Account bankAccount);

    /**
     * Updates a bankAccount.
     *
     * @param bankAccount the entity to update.
     * @return the persisted entity.
     */
    Account update(Account bankAccount);

    /**
     * Partially updates a bankAccount.
     *
     * @param bankAccount the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Account> partialUpdate(Account bankAccount);

    /**
     * Get all the bankAccounts.
     *
     * @return the list of entities.
     */
    List<Account> findAll();

    /**
     * Get the "id" bankAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Account> findOne(String id);

    /**
     * Delete the "id" bankAccount.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
