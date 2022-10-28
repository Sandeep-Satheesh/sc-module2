package org.smallchange.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.Bond;
import org.smallchange.backend.repository.BondsRepository;
import org.smallchange.backend.service.BondService;
import org.smallchange.backend.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Bond}.
 */
@RestController
@RequestMapping("/api")
public class BondsResource {

    private final Logger log = LoggerFactory.getLogger(BondsResource.class);

    private static final String ENTITY_NAME = "bonds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BondService bondService;

    private final BondsRepository bondsRepository;

    public BondsResource(BondService bondService, BondsRepository bondsRepository) {
        this.bondService = bondService;
        this.bondsRepository = bondsRepository;
    }

    /**
     * {@code POST  /bonds} : Create a new bonds.
     *
     * @param bond the bonds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonds, or with status {@code 400 (Bad Request)} if the bonds has already a code.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonds")
    public ResponseEntity<Bond> createBonds(@RequestBody Bond bond) throws URISyntaxException {
        log.debug("REST request to save Bonds : {}", bond);
        if (bond.getCode() != null) {
            throw new BadRequestAlertException("A new bonds cannot already have an code", ENTITY_NAME, "code exists");
        }
        Bond result = bondService.save(bond);
        return ResponseEntity
            .created(new URI("/api/bonds/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getCode().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bonds/:code} : Updates an existing bonds.
     *
     * @param code the code of the bonds to save.
     * @param bond the bonds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonds,
     * or with status {@code 400 (Bad Request)} if the bonds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonds/{code}")
    public ResponseEntity<Bond> updateBonds(@PathVariable(value = "code", required = false) final String code, @RequestBody Bond bond)
        throws URISyntaxException {
        log.debug("REST request to update Bonds : {}, {}", code, bond);
        if (bond.getCode() == null) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, bond.getCode())) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "idinvalid");
        }

        if (!bondsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bond result = bondService.update(bond);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bond.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /bonds/:code} : Partial updates given fields of an existing bonds, field will ignore if it is null
     *
     * @param code the code of the bonds to save.
     * @param bond the bonds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonds,
     * or with status {@code 400 (Bad Request)} if the bonds is not valid,
     * or with status {@code 404 (Not Found)} if the bonds is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bonds/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bond> partialUpdateBonds(@PathVariable(value = "code", required = false) final String code, @RequestBody Bond bond)
        throws URISyntaxException {
        log.debug("REST request to partial update Bonds partially : {}, {}", code, bond);
        if (bond.getCode() == null) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, bond.getCode())) {
            throw new BadRequestAlertException("Invalid code", ENTITY_NAME, "idinvalid");
        }

        if (!bondsRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bond> result = bondService.partialUpdate(bond);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bond.getCode().toString())
        );
    }

    /**
     * {@code GET  /bonds} : get all the bonds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonds in body.
     */
    @GetMapping("/bonds")
    public List<Bond> getAllBonds() {
        log.debug("REST request to get all Bonds");
        return bondService.findAll();
    }

    /**
     * {@code GET  /bonds/:code} : get the "code" bonds.
     *
     * @param code the code of the bonds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonds/{code}")
    public ResponseEntity<Bond> getBonds(@PathVariable String code) {
        log.debug("REST request to get Bonds : {}", code);
        Optional<Bond> bonds = bondService.findOne(code);
        return ResponseUtil.wrapOrNotFound(bonds);
    }

    /**
     * {@code DELETE  /bonds/:code} : delete the "code" bonds.
     *
     * @param code the code of the bonds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonds/{code}")
    public ResponseEntity<Void> deleteBonds(@PathVariable String code) {
        log.debug("REST request to delete Bonds : {}", code);
        bondService.delete(code);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, code))
            .build();
    }
}
