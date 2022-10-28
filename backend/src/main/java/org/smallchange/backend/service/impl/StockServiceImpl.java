package org.smallchange.backend.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.Stock;
import org.smallchange.backend.repository.StocksRepository;
import org.smallchange.backend.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Stock}.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StocksRepository stocksRepository;

    public StockServiceImpl(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    @Override
    public Stock save(Stock stock) {
        log.debug("Request to save Stocks : {}", stock);
        return stocksRepository.save(stock);
    }

    @Override
    public Stock update(Stock stock) {
        log.debug("Request to update Stocks : {}", stock);
        return stocksRepository.save(stock);
    }

    @Override
    public Optional<Stock> partialUpdate(Stock stock) {
        log.debug("Request to partially update Stocks : {}", stock);

        return stocksRepository
            .findById(stock.getCode())
            .map(existingStocks -> {
                if (stock.getCode() != null) {
                    existingStocks.setCode(stock.getCode());
                }
                if (stock.getName() != null) {
                    existingStocks.setName(stock.getName());
                }
                if (stock.getQuantity() != null) {
                    existingStocks.setQuantity(stock.getQuantity());
                }
                if (stock.getCurrentPrice() != null) {
                    existingStocks.setCurrentPrice(stock.getCurrentPrice());
                }
                if (stock.getStockType() != null) {
                    existingStocks.setStockType(stock.getStockType());
                }
                if (stock.getExchangeName() != null) {
                    existingStocks.setExchangeName(stock.getExchangeName());
                }

                return existingStocks;
            })
            .map(stocksRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Stock> findAll() {
        log.debug("Request to get all Stocks");
        return stocksRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stock> findOne(String id) {
        log.debug("Request to get Stocks : {}", id);
        return stocksRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Stocks : {}", id);
        stocksRepository.deleteById(id);
    }
}
