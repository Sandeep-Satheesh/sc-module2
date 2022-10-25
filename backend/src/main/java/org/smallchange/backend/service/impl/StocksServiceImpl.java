package org.smallchange.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.Stocks;
import org.smallchange.backend.repository.StocksRepository;
import org.smallchange.backend.service.StocksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Stocks}.
 */
@Service
@Transactional
public class StocksServiceImpl implements StocksService {

    private final Logger log = LoggerFactory.getLogger(StocksServiceImpl.class);

    private final StocksRepository stocksRepository;

    public StocksServiceImpl(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Stocks save(Stocks stocks) {
        log.debug("Request to save Stocks : {}", stocks);
        return stocksRepository.save(stocks);
    }

    @Override
    public Stocks update(Stocks stocks) {
        log.debug("Request to update Stocks : {}", stocks);
        return stocksRepository.save(stocks);
    }

    @Override
    public Optional<Stocks> partialUpdate(Stocks stocks) {
        log.debug("Request to partially update Stocks : {}", stocks);

        return stocksRepository
            .findById(stocks.getId())
            .map(existingStocks -> {
                if (stocks.getCode() != null) {
                    existingStocks.setCode(stocks.getCode());
                }
                if (stocks.getName() != null) {
                    existingStocks.setName(stocks.getName());
                }
                if (stocks.getQuantity() != null) {
                    existingStocks.setQuantity(stocks.getQuantity());
                }
                if (stocks.getCurrentPrice() != null) {
                    existingStocks.setCurrentPrice(stocks.getCurrentPrice());
                }
                if (stocks.getStockType() != null) {
                    existingStocks.setStockType(stocks.getStockType());
                }
                if (stocks.getExchange() != null) {
                    existingStocks.setExchange(stocks.getExchange());
                }

                return existingStocks;
            })
            .map(stocksRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stocks> findAll() {
        log.debug("Request to get all Stocks");
        return stocksRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stocks> findOne(Long id) {
        log.debug("Request to get Stocks : {}", id);
        return stocksRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stocks : {}", id);
        stocksRepository.deleteById(id);
    }
}
