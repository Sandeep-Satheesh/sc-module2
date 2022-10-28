package org.smallchange.backend.service;

import java.util.List;
import java.util.Optional;

import org.smallchange.backend.domain.Bond;

/**
 * Service Interface for managing {@link Bond}.
 */
public interface BondService {
    /**
     * Save a bonds.
     *
     * @param bond the entity to save.
     * @return the persisted entity.
     */
    Bond save(Bond bond);

    /**
     * Updates a bonds.
     *
     * @param bond the entity to update.
     * @return the persisted entity.
     */
    Bond update(Bond bond);

    /**
     * Partially updates a bonds.
     *
     * @param bond the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bond> partialUpdate(Bond bond);

    /**
     * Get all the bonds.
     *
     * @return the list of entities.
     */
    List<Bond> findAll();

    /**
     * Get the "id" bonds.
     *
     * @param code the id of the entity.
     * @return the entity.
     */
    Optional<Bond> findOne(String code);

    /**
     * Delete the "id" bonds.
     *
     * @param code the id of the entity.
     */
    void delete(String code);
}
