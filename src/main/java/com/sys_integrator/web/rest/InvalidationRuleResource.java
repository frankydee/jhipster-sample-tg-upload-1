package com.sys_integrator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sys_integrator.domain.InvalidationRule;
import com.sys_integrator.repository.InvalidationRuleRepository;
import com.sys_integrator.web.rest.errors.BadRequestAlertException;
import com.sys_integrator.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvalidationRule.
 */
@RestController
@RequestMapping("/api")
public class InvalidationRuleResource {

    private final Logger log = LoggerFactory.getLogger(InvalidationRuleResource.class);

    private static final String ENTITY_NAME = "invalidationRule";

    private final InvalidationRuleRepository invalidationRuleRepository;

    public InvalidationRuleResource(InvalidationRuleRepository invalidationRuleRepository) {
        this.invalidationRuleRepository = invalidationRuleRepository;
    }

    /**
     * POST  /invalidation-rules : Create a new invalidationRule.
     *
     * @param invalidationRule the invalidationRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invalidationRule, or with status 400 (Bad Request) if the invalidationRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invalidation-rules")
    @Timed
    public ResponseEntity<InvalidationRule> createInvalidationRule(@Valid @RequestBody InvalidationRule invalidationRule) throws URISyntaxException {
        log.debug("REST request to save InvalidationRule : {}", invalidationRule);
        if (invalidationRule.getId() != null) {
            throw new BadRequestAlertException("A new invalidationRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvalidationRule result = invalidationRuleRepository.save(invalidationRule);
        return ResponseEntity.created(new URI("/api/invalidation-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invalidation-rules : Updates an existing invalidationRule.
     *
     * @param invalidationRule the invalidationRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invalidationRule,
     * or with status 400 (Bad Request) if the invalidationRule is not valid,
     * or with status 500 (Internal Server Error) if the invalidationRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invalidation-rules")
    @Timed
    public ResponseEntity<InvalidationRule> updateInvalidationRule(@Valid @RequestBody InvalidationRule invalidationRule) throws URISyntaxException {
        log.debug("REST request to update InvalidationRule : {}", invalidationRule);
        if (invalidationRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvalidationRule result = invalidationRuleRepository.save(invalidationRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invalidationRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invalidation-rules : get all the invalidationRules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invalidationRules in body
     */
    @GetMapping("/invalidation-rules")
    @Timed
    public List<InvalidationRule> getAllInvalidationRules() {
        log.debug("REST request to get all InvalidationRules");
        return invalidationRuleRepository.findAll();
    }

    /**
     * GET  /invalidation-rules/:id : get the "id" invalidationRule.
     *
     * @param id the id of the invalidationRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invalidationRule, or with status 404 (Not Found)
     */
    @GetMapping("/invalidation-rules/{id}")
    @Timed
    public ResponseEntity<InvalidationRule> getInvalidationRule(@PathVariable Long id) {
        log.debug("REST request to get InvalidationRule : {}", id);
        Optional<InvalidationRule> invalidationRule = invalidationRuleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invalidationRule);
    }

    /**
     * DELETE  /invalidation-rules/:id : delete the "id" invalidationRule.
     *
     * @param id the id of the invalidationRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invalidation-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvalidationRule(@PathVariable Long id) {
        log.debug("REST request to delete InvalidationRule : {}", id);

        invalidationRuleRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
