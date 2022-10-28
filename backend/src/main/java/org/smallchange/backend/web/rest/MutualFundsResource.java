package org.smallchange.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.MutualFund;
import org.smallchange.backend.repository.MutualFundsRepository;
import org.smallchange.backend.service.MutualFundService;
import org.smallchange.backend.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link MutualFund}.
 */
@RestController
@RequestMapping("/api")
public class MutualFundsResource {

    private final Logger log = LoggerFactory.getLogger(MutualFundsResource.class);

    private static final String ENTITY_NAME = "mutualFunds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MutualFundService mutualFundService;

    private final MutualFundsRepository mutualFundsRepository;

    public MutualFundsResource(MutualFundService mutualFundService, MutualFundsRepository mutualFundsRepository) {
        this.mutualFundService = mutualFundService;
        this.mutualFundsRepository = mutualFundsRepository;
    }

    /**
     * {@code POST  /mutual-funds} : Create a new mutualFunds.
     *
     * @param mutualFund the mutualFunds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mutualFunds, or with status {@code 400 (Bad Request)} if the mutualFunds has already a code.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mutual-funds")
    public ResponseEntity<MutualFund> createMutualFunds(@RequestBody MutualFund mutualFund) throws URISyntaxException {
        log.debug("REST request to save MutualFunds : {}", mutualFund);
        if (mutualFund.getCode() != null) {
            throw new BadRequestAlertException("A new mutualFunds cannot already have an code", ENTITY_NAME, "code exists");
        }
        MutualFund result = mutualFundService.save(mutualFund);
        return ResponseEntity
            .created(new URI("/api/mutual-funds/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getCode().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mutual-funds/:code} : Updates an existing mutualFunds.
     *
     * @param code the code of the mutualFunds to save.
     * @param mutualFund the mutualFunds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutualFunds,
     * or with status {@code 400 (Bad Request)} if the mutualFunds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mutualFunds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mutual-funds/{code}")
    public ResponseEntity<MutualFund> updateMutualFunds(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody MutualFund mutualFund
    ) throws URISyntaxException {
        log.debug("REST request to update MutualFunds : {}, {}", code, mutualFund);
        if (mutualFund.getCode() == null) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "code null");
        }
        if (!Objects.equals(code, mutualFund.getCode())) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "code invalid");
        }

        if (!mutualFundsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "code not found");
        }

        MutualFund result = mutualFundService.update(mutualFund);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutualFund.getCode().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mutual-funds/:code} : Partial updates given fields of an existing mutualFunds, field will ignore if it is null
     *
     * @param code the code of the mutualFunds to save.
     * @param mutualFund the mutualFunds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutualFunds,
     * or with status {@code 400 (Bad Request)} if the mutualFunds is not valid,
     * or with status {@code 404 (Not Found)} if the mutualFunds is not found,
     * or with status {@code 500 (Internal Server Error)} if the mutualFunds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mutual-funds/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MutualFund> partialUpdateMutualFunds(
        @PathVariable(value = "code", required = false) final String code,
        @RequestBody MutualFund mutualFund
    ) throws URISyntaxException {
        log.debug("REST request to partial update MutualFunds partially : {}, {}", code, mutualFund);
        if (mutualFund.getCode() == null) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "code null");
        }
        if (!Objects.equals(code, mutualFund.getCode())) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "code invalid");
        }

        if (!mutualFundsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "code not found");
        }

        Optional<MutualFund> result = mutualFundService.partialUpdate(mutualFund);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutualFund.getCode().toString())
        );
    }

    /**
     * {@code GET  /mutual-funds} : get all the mutualFunds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mutualFunds in body.
     */
    @GetMapping("/mutual-funds")
    public List<MutualFund> getAllMutualFunds() {
        log.debug("REST request to get all MutualFunds");
        return mutualFundService.findAll();
    }

    /**
     * {@code GET  /mutual-funds/:code} : get the "code" mutualFunds.
     *
     * @param code the code of the mutualFunds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mutualFunds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mutual-funds/{code}")
    public ResponseEntity<MutualFund> getMutualFunds(@PathVariable String code) {
        log.debug("REST request to get MutualFunds : {}", code);
        Optional<MutualFund> mutualFunds = mutualFundService.findOne(code);
        return ResponseUtil.wrapOrNotFound(mutualFunds);
    }

    /**
     * {@code DELETE  /mutual-funds/:code} : delete the "code" mutualFunds.
     *
     * @param code the code of the mutualFunds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mutual-funds/{code}")
    public ResponseEntity<String> deleteMutualFunds(@PathVariable String code) {
        log.debug("REST request to delete MutualFunds : {}", code);
        mutualFundService.delete(code);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, code.toString()))
            .build();
    }
}
