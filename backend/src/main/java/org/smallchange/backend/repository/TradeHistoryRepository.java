package org.smallchange.backend.repository;

import org.smallchange.backend.domain.TradeHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TradeHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {}
