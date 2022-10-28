package org.smallchange.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.Bond;
import org.smallchange.backend.repository.BondsRepository;
import org.smallchange.backend.service.BondService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bond}.
 */
@Service
@Transactional
public class BondServiceImpl implements BondService {

    private final Logger log = LoggerFactory.getLogger(BondServiceImpl.class);

    private final BondsRepository bondsRepository;

    public BondServiceImpl(BondsRepository bondsRepository) {
        this.bondsRepository = bondsRepository;
    }

    @Override
    public Bond save(Bond bond) {
        log.debug("Request to save Bonds : {}", bond);
        return bondsRepository.save(bond);
    }

    @Override
    public Bond update(Bond bond) {
        log.debug("Request to update Bonds : {}", bond);
        return bondsRepository.save(bond);
    }

    @Override
    public Optional<Bond> partialUpdate(Bond bond) {
        log.debug("Request to partially update Bonds : {}", bond);

        return bondsRepository
            .findById(bond.getCode())
            .map(existingBonds -> {
                if (bond.getCode() != null) {
                    existingBonds.setCode(bond.getCode());
                }
                if (bond.getName() != null) {
                    existingBonds.setName(bond.getName());
                }
                if (bond.getQuantity() != null) {
                    existingBonds.setQuantity(bond.getQuantity());
                }
                if (bond.getCurrentPrice() != null) {
                    existingBonds.setCurrentPrice(bond.getCurrentPrice());
                }
                if (bond.getInterestRate() != null) {
                    existingBonds.setInterestRate(bond.getInterestRate());
                }
                if (bond.getDurationMonths() != null) {
                    existingBonds.setDurationMonths(bond.getDurationMonths());
                }
                if (bond.getBondType() != null) {
                    existingBonds.setBondType(bond.getBondType());
                }
                if (bond.getExchangeName() != null) {
                    existingBonds.setExchangeName(bond.getExchangeName());
                }

                return existingBonds;
            })
            .map(bondsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bond> findAll() {
        log.debug("Request to get all Bonds");
        return bondsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bond> findOne(String id) {
        log.debug("Request to get Bonds : {}", id);
        return bondsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Bonds : {}", id);
        bondsRepository.deleteById(id);
    }
}
