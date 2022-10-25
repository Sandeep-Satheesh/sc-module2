package org.smallchange.backend.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smallchange.backend.domain.Bonds;
import org.smallchange.backend.repository.BondsRepository;
import org.smallchange.backend.service.BondsService;
import org.smallchange.backend.web.rest.errors.BadRequestAlertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.smallchange.backend.domain.Bonds}.
 */
@RestController
@RequestMapping("/api")
public class BondsResource {

    private final Logger log = LoggerFactory.getLogger(BondsResource.class);

    private static final String ENTITY_NAME = "bonds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BondsService bondsService;

    private final BondsRepository bondsRepository;

    public BondsResource(BondsService bondsService, BondsRepository bondsRepository) {
        this.bondsService = bondsService;
        this.bondsRepository = bondsRepository;
    }

    /**
     * {@code POST  /bonds} : Create a new bonds.
     *
     * @param bonds the bonds to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonds, or with status {@code 400 (Bad Request)} if the bonds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonds")
    public ResponseEntity<Bonds> createBonds(@RequestBody Bonds bonds) throws URISyntaxException {
        log.debug("REST request to save Bonds : {}", bonds);
        if (bonds.getId() != null) {
            throw new BadRequestAlertException("A new bonds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bonds result = bondsService.save(bonds);
        return ResponseEntity
            .created(new URI("/api/bonds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bonds/:id} : Updates an existing bonds.
     *
     * @param id the id of the bonds to save.
     * @param bonds the bonds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonds,
     * or with status {@code 400 (Bad Request)} if the bonds is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonds/{id}")
    public ResponseEntity<Bonds> updateBonds(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bonds bonds)
        throws URISyntaxException {
        log.debug("REST request to update Bonds : {}, {}", id, bonds);
        if (bonds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bonds result = bondsService.update(bonds);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonds.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bonds/:id} : Partial updates given fields of an existing bonds, field will ignore if it is null
     *
     * @param id the id of the bonds to save.
     * @param bonds the bonds to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonds,
     * or with status {@code 400 (Bad Request)} if the bonds is not valid,
     * or with status {@code 404 (Not Found)} if the bonds is not found,
     * or with status {@code 500 (Internal Server Error)} if the bonds couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bonds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bonds> partialUpdateBonds(@PathVariable(value = "id", required = false) final Long id, @RequestBody Bonds bonds)
        throws URISyntaxException {
        log.debug("REST request to partial update Bonds partially : {}, {}", id, bonds);
        if (bonds.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bonds.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bondsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bonds> result = bondsService.partialUpdate(bonds);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bonds.getId().toString())
        );
    }

    /**
     * {@code GET  /bonds} : get all the bonds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonds in body.
     */
    @GetMapping("/bonds")
    public List<Bonds> getAllBonds() {
        log.debug("REST request to get all Bonds");
        return bondsService.findAll();
    }

    /**
     * {@code GET  /bonds/:id} : get the "id" bonds.
     *
     * @param id the id of the bonds to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonds, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonds/{id}")
    public ResponseEntity<Bonds> getBonds(@PathVariable Long id) {
        log.debug("REST request to get Bonds : {}", id);
        Optional<Bonds> bonds = bondsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonds);
    }

    /**
     * {@code DELETE  /bonds/:id} : delete the "id" bonds.
     *
     * @param id the id of the bonds to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonds/{id}")
    public ResponseEntity<Void> deleteBonds(@PathVariable Long id) {
        log.debug("REST request to delete Bonds : {}", id);
        bondsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
