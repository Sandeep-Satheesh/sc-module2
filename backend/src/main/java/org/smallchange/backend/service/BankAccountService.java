package org.smallchange.backend.service;

import java.util.List;
import java.util.Optional;
import org.smallchange.backend.domain.BankAccount;

/**
 * Service Interface for managing {@link BankAccount}.
 */
public interface BankAccountService {
    /**
     * Save a bankAccount.
     *
     * @param bankAccount the entity to save.
     * @return the persisted entity.
     */
    BankAccount save(BankAccount bankAccount);

    /**
     * Updates a bankAccount.
     *
     * @param bankAccount the entity to update.
     * @return the persisted entity.
     */
    BankAccount update(BankAccount bankAccount);

    /**
     * Partially updates a bankAccount.
     *
     * @param bankAccount the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankAccount> partialUpdate(BankAccount bankAccount);

    /**
     * Get all the bankAccounts.
     *
     * @return the list of entities.
     */
    List<BankAccount> findAll();

    /**
     * Get the "id" bankAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankAccount> findOne(Long id);

    /**
     * Delete the "id" bankAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
