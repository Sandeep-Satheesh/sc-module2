package org.smallchange.backend.repository;

import org.smallchange.backend.domain.Bonds;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bonds entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BondsRepository extends JpaRepository<Bonds, Long> {}
