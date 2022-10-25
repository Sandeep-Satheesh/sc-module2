package org.smallchange.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.TradeHistory;
import org.smallchange.backend.repository.TradeHistoryRepository;
import org.smallchange.backend.service.TradeHistoryService;
import org.smallchange.backend.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.smallchange.backend.domain.TradeHistory}.
 */
@RestController
@RequestMapping("/api")
public class TradeHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TradeHistoryResource.class);

    private static final String ENTITY_NAME = "tradeHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradeHistoryService tradeHistoryService;

    private final TradeHistoryRepository tradeHistoryRepository;

    public TradeHistoryResource(TradeHistoryService tradeHistoryService, TradeHistoryRepository tradeHistoryRepository) {
        this.tradeHistoryService = tradeHistoryService;
        this.tradeHistoryRepository = tradeHistoryRepository;
    }

    /**
     * {@code POST  /trade-histories} : Create a new tradeHistory.
     *
     * @param tradeHistory the tradeHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tradeHistory, or with status {@code 400 (Bad Request)} if the tradeHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trade-histories")
    public ResponseEntity<TradeHistory> createTradeHistory(@RequestBody TradeHistory tradeHistory) throws URISyntaxException {
        log.debug("REST request to save TradeHistory : {}", tradeHistory);
        if (tradeHistory.getId() != null) {
            throw new BadRequestAlertException("A new tradeHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradeHistory result = tradeHistoryService.save(tradeHistory);
        return ResponseEntity
            .created(new URI("/api/trade-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trade-histories/:id} : Updates an existing tradeHistory.
     *
     * @param id the id of the tradeHistory to save.
     * @param tradeHistory the tradeHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradeHistory,
     * or with status {@code 400 (Bad Request)} if the tradeHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tradeHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trade-histories/{id}")
    public ResponseEntity<TradeHistory> updateTradeHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TradeHistory tradeHistory
    ) throws URISyntaxException {
        log.debug("REST request to update TradeHistory : {}, {}", id, tradeHistory);
        if (tradeHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tradeHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tradeHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TradeHistory result = tradeHistoryService.update(tradeHistory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tradeHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trade-histories/:id} : Partial updates given fields of an existing tradeHistory, field will ignore if it is null
     *
     * @param id the id of the tradeHistory to save.
     * @param tradeHistory the tradeHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradeHistory,
     * or with status {@code 400 (Bad Request)} if the tradeHistory is not valid,
     * or with status {@code 404 (Not Found)} if the tradeHistory is not found,
     * or with status {@code 500 (Internal Server Error)} if the tradeHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trade-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TradeHistory> partialUpdateTradeHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TradeHistory tradeHistory
    ) throws URISyntaxException {
        log.debug("REST request to partial update TradeHistory partially : {}, {}", id, tradeHistory);
        if (tradeHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tradeHistory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tradeHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TradeHistory> result = tradeHistoryService.partialUpdate(tradeHistory);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tradeHistory.getId().toString())
        );
    }

    /**
     * {@code GET  /trade-histories} : get all the tradeHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tradeHistories in body.
     */
    @GetMapping("/trade-histories")
    public List<TradeHistory> getAllTradeHistories() {
        log.debug("REST request to get all TradeHistories");
        return tradeHistoryService.findAll();
    }

    /**
     * {@code GET  /trade-histories/:id} : get the "id" tradeHistory.
     *
     * @param id the id of the tradeHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tradeHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trade-histories/{id}")
    public ResponseEntity<TradeHistory> getTradeHistory(@PathVariable Long id) {
        log.debug("REST request to get TradeHistory : {}", id);
        Optional<TradeHistory> tradeHistory = tradeHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradeHistory);
    }

    /**
     * {@code DELETE  /trade-histories/:id} : delete the "id" tradeHistory.
     *
     * @param id the id of the tradeHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trade-histories/{id}")
    public ResponseEntity<Void> deleteTradeHistory(@PathVariable Long id) {
        log.debug("REST request to delete TradeHistory : {}", id);
        tradeHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
