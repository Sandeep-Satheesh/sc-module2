package org.smallchange.backend.service;

import java.util.List;
import java.util.Optional;

import org.smallchange.backend.domain.MutualFund;

/**
 * Service Interface for managing {@link MutualFund}.
 */
public interface MutualFundService {
    /**
     * Save a mutualFunds.
     *
     * @param mutualFund the entity to save.
     * @return the persisted entity.
     */
    MutualFund save(MutualFund mutualFund);

    /**
     * Updates a mutualFunds.
     *
     * @param mutualFund the entity to update.
     * @return the persisted entity.
     */
    MutualFund update(MutualFund mutualFund);

    /**
     * Partially updates a mutualFunds.
     *
     * @param mutualFund the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MutualFund> partialUpdate(MutualFund mutualFund);

    /**
     * Get all the mutualFunds.
     *
     * @return the list of entities.
     */
    List<MutualFund> findAll();

    /**
     * Get the "id" mutualFunds.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MutualFund> findOne(String code);

    /**
     * Delete the "id" mutualFunds.
     *
     * @param id the id of the entity.
     */
    void delete(String code);
}
