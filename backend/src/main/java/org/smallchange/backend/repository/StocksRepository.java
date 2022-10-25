package org.smallchange.backend.repository;

import org.smallchange.backend.domain.Stocks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stocks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {}
