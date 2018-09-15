package com.sys_integrator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sys_integrator.domain.InvalidationCode;
import com.sys_integrator.repository.InvalidationCodeRepository;
import com.sys_integrator.web.rest.errors.BadRequestAlertException;
import com.sys_integrator.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvalidationCode.
 */
@RestController
@RequestMapping("/api")
public class InvalidationCodeResource {

    private final Logger log = LoggerFactory.getLogger(InvalidationCodeResource.class);

    private static final String ENTITY_NAME = "invalidationCode";

    private final InvalidationCodeRepository invalidationCodeRepository;

    public InvalidationCodeResource(InvalidationCodeRepository invalidationCodeRepository) {
        this.invalidationCodeRepository = invalidationCodeRepository;
    }

    /**
     * POST  /invalidation-codes : Create a new invalidationCode.
     *
     * @param invalidationCode the invalidationCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invalidationCode, or with status 400 (Bad Request) if the invalidationCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invalidation-codes")
    @Timed
    public ResponseEntity<InvalidationCode> createInvalidationCode(@RequestBody InvalidationCode invalidationCode) throws URISyntaxException {
        log.debug("REST request to save InvalidationCode : {}", invalidationCode);
        if (invalidationCode.getId() != null) {
            throw new BadRequestAlertException("A new invalidationCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvalidationCode result = invalidationCodeRepository.save(invalidationCode);
        return ResponseEntity.created(new URI("/api/invalidation-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invalidation-codes : Updates an existing invalidationCode.
     *
     * @param invalidationCode the invalidationCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invalidationCode,
     * or with status 400 (Bad Request) if the invalidationCode is not valid,
     * or with status 500 (Internal Server Error) if the invalidationCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invalidation-codes")
    @Timed
    public ResponseEntity<InvalidationCode> updateInvalidationCode(@RequestBody InvalidationCode invalidationCode) throws URISyntaxException {
        log.debug("REST request to update InvalidationCode : {}", invalidationCode);
        if (invalidationCode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvalidationCode result = invalidationCodeRepository.save(invalidationCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invalidationCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invalidation-codes : get all the invalidationCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invalidationCodes in body
     */
    @GetMapping("/invalidation-codes")
    @Timed
    public List<InvalidationCode> getAllInvalidationCodes() {
        log.debug("REST request to get all InvalidationCodes");
        return invalidationCodeRepository.findAll();
    }

    /**
     * GET  /invalidation-codes/:id : get the "id" invalidationCode.
     *
     * @param id the id of the invalidationCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invalidationCode, or with status 404 (Not Found)
     */
    @GetMapping("/invalidation-codes/{id}")
    @Timed
    public ResponseEntity<InvalidationCode> getInvalidationCode(@PathVariable Long id) {
        log.debug("REST request to get InvalidationCode : {}", id);
        Optional<InvalidationCode> invalidationCode = invalidationCodeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invalidationCode);
    }

    /**
     * DELETE  /invalidation-codes/:id : delete the "id" invalidationCode.
     *
     * @param id the id of the invalidationCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invalidation-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvalidationCode(@PathVariable Long id) {
        log.debug("REST request to delete InvalidationCode : {}", id);

        invalidationCodeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
