package org.smallchange.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.TradeHistory;
import org.smallchange.backend.repository.TradeHistoryRepository;
import org.smallchange.backend.service.TradeHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TradeHistory}.
 */
@Service
@Transactional
public class TradeHistoryServiceImpl implements TradeHistoryService {

    private final Logger log = LoggerFactory.getLogger(TradeHistoryServiceImpl.class);

    private final TradeHistoryRepository tradeHistoryRepository;

    public TradeHistoryServiceImpl(TradeHistoryRepository tradeHistoryRepository) {
        this.tradeHistoryRepository = tradeHistoryRepository;
    }

    @Override
    public TradeHistory save(TradeHistory tradeHistory) {
        log.debug("Request to save TradeHistory : {}", tradeHistory);
        return tradeHistoryRepository.save(tradeHistory);
    }

    @Override
    public TradeHistory update(TradeHistory tradeHistory) {
        log.debug("Request to update TradeHistory : {}", tradeHistory);
        return tradeHistoryRepository.save(tradeHistory);
    }

    @Override
    public Optional<TradeHistory> partialUpdate(TradeHistory tradeHistory) {
        log.debug("Request to partially update TradeHistory : {}", tradeHistory);

        return tradeHistoryRepository
            .findById(tradeHistory.getId())
            .map(existingTradeHistory -> {
                if (tradeHistory.getTradeId() != null) {
                    existingTradeHistory.setTradeId(tradeHistory.getTradeId());
                }
                if (tradeHistory.getUserId() != null) {
                    existingTradeHistory.setUserId(tradeHistory.getUserId());
                }
                if (tradeHistory.getAssetCode() != null) {
                    existingTradeHistory.setAssetCode(tradeHistory.getAssetCode());
                }
                if (tradeHistory.getTradePrice() != null) {
                    existingTradeHistory.setTradePrice(tradeHistory.getTradePrice());
                }
                if (tradeHistory.getTradeType() != null) {
                    existingTradeHistory.setTradeType(tradeHistory.getTradeType());
                }
                if (tradeHistory.getTradeQuantity() != null) {
                    existingTradeHistory.setTradeQuantity(tradeHistory.getTradeQuantity());
                }
                if (tradeHistory.getTradeDate() != null) {
                    existingTradeHistory.setTradeDate(tradeHistory.getTradeDate());
                }

                return existingTradeHistory;
            })
            .map(tradeHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TradeHistory> findAll() {
        log.debug("Request to get all TradeHistories");
        return tradeHistoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TradeHistory> findOne(Long id) {
        log.debug("Request to get TradeHistory : {}", id);
        return tradeHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TradeHistory : {}", id);
        tradeHistoryRepository.deleteById(id);
    }
}
