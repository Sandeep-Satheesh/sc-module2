package org.smallchange.backend.service;

import java.util.List;
import java.util.Optional;

import org.smallchange.backend.domain.Stock;

/**
 * Service Interface for managing {@link Stock}.
 */
public interface StockService {
    /**
     * Save a stocks.
     *
     * @param stock the entity to save.
     * @return the persisted entity.
     */
    Stock save(Stock stock);

    /**
     * Updates a stocks.
     *
     * @param stock the entity to update.
     * @return the persisted entity.
     */
    Stock update(Stock stock);

    /**
     * Partially updates a stocks.
     *
     * @param stock the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Stock> partialUpdate(Stock stock);

    /**
     * Get all the stocks.
     *
     * @return the list of entities.
     */
    List<Stock> findAll();

    /**
     * Get the "id" stocks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Stock> findOne(String id);

    /**
     * Delete the "id" stocks.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
