package org.smallchange.backend.repository;

import org.smallchange.backend.domain.MutualFund;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MutualFunds entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MutualFundsRepository extends JpaRepository<MutualFund, String> {}
