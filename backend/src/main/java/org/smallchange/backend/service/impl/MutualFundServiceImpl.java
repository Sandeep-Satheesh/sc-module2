package org.smallchange.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.MutualFund;
import org.smallchange.backend.repository.MutualFundsRepository;
import org.smallchange.backend.service.MutualFundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MutualFund}.
 */
@Service
@Transactional
public class MutualFundServiceImpl implements MutualFundService {

    private final Logger log = LoggerFactory.getLogger(MutualFundServiceImpl.class);

    private final MutualFundsRepository mutualFundsRepository;

    public MutualFundServiceImpl(MutualFundsRepository mutualFundsRepository) {
        this.mutualFundsRepository = mutualFundsRepository;
    }

    @Override
    public MutualFund save(MutualFund mutualFund) {
        log.debug("Request to save MutualFunds : {}", mutualFund);
        return mutualFundsRepository.save(mutualFund);
    }

    @Override
    public MutualFund update(MutualFund mutualFund) {
        log.debug("Request to update MutualFunds : {}", mutualFund);
        return mutualFundsRepository.save(mutualFund);
    }

    @Override
    public Optional<MutualFund> partialUpdate(MutualFund mutualFund) {
        log.debug("Request to partially update MutualFunds : {}", mutualFund);

        return mutualFundsRepository
            .findById(mutualFund.getCode())
            .map(existingMutualFunds -> {
                if (mutualFund.getCode() != null) {
                    existingMutualFunds.setCode(mutualFund.getCode());
                }
                if (mutualFund.getName() != null) {
                    existingMutualFunds.setName(mutualFund.getName());
                }
                if (mutualFund.getQuantity() != null) {
                    existingMutualFunds.setQuantity(mutualFund.getQuantity());
                }
                if (mutualFund.getCurrentPrice() != null) {
                    existingMutualFunds.setCurrentPrice(mutualFund.getCurrentPrice());
                }
                if (mutualFund.getMfType() != null) {
                    existingMutualFunds.setMfType(mutualFund.getMfType());
                }
                if (mutualFund.getInterestRate() != null) {
                    existingMutualFunds.setInterestRate(mutualFund.getInterestRate());
                }

                return existingMutualFunds;
            })
            .map(mutualFundsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MutualFund> findAll() {
        log.debug("Request to get all MutualFunds");
        return mutualFundsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MutualFund> findOne(String id) {
        log.debug("Request to get MutualFunds : {}", id);
        return mutualFundsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete MutualFunds : {}", id);
        mutualFundsRepository.deleteById(id);
    }
}
