package com.sys_integrator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sys_integrator.domain.RecordingTechnology;
import com.sys_integrator.repository.RecordingTechnologyRepository;
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
 * REST controller for managing RecordingTechnology.
 */
@RestController
@RequestMapping("/api")
public class RecordingTechnologyResource {

    private final Logger log = LoggerFactory.getLogger(RecordingTechnologyResource.class);

    private static final String ENTITY_NAME = "recordingTechnology";

    private final RecordingTechnologyRepository recordingTechnologyRepository;

    public RecordingTechnologyResource(RecordingTechnologyRepository recordingTechnologyRepository) {
        this.recordingTechnologyRepository = recordingTechnologyRepository;
    }

    /**
     * POST  /recording-technologies : Create a new recordingTechnology.
     *
     * @param recordingTechnology the recordingTechnology to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recordingTechnology, or with status 400 (Bad Request) if the recordingTechnology has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recording-technologies")
    @Timed
    public ResponseEntity<RecordingTechnology> createRecordingTechnology(@Valid @RequestBody RecordingTechnology recordingTechnology) throws URISyntaxException {
        log.debug("REST request to save RecordingTechnology : {}", recordingTechnology);
        if (recordingTechnology.getId() != null) {
            throw new BadRequestAlertException("A new recordingTechnology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordingTechnology result = recordingTechnologyRepository.save(recordingTechnology);
        return ResponseEntity.created(new URI("/api/recording-technologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recording-technologies : Updates an existing recordingTechnology.
     *
     * @param recordingTechnology the recordingTechnology to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recordingTechnology,
     * or with status 400 (Bad Request) if the recordingTechnology is not valid,
     * or with status 500 (Internal Server Error) if the recordingTechnology couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recording-technologies")
    @Timed
    public ResponseEntity<RecordingTechnology> updateRecordingTechnology(@Valid @RequestBody RecordingTechnology recordingTechnology) throws URISyntaxException {
        log.debug("REST request to update RecordingTechnology : {}", recordingTechnology);
        if (recordingTechnology.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecordingTechnology result = recordingTechnologyRepository.save(recordingTechnology);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recordingTechnology.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recording-technologies : get all the recordingTechnologies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of recordingTechnologies in body
     */
    @GetMapping("/recording-technologies")
    @Timed
    public List<RecordingTechnology> getAllRecordingTechnologies() {
        log.debug("REST request to get all RecordingTechnologies");
        return recordingTechnologyRepository.findAll();
    }

    /**
     * GET  /recording-technologies/:id : get the "id" recordingTechnology.
     *
     * @param id the id of the recordingTechnology to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recordingTechnology, or with status 404 (Not Found)
     */
    @GetMapping("/recording-technologies/{id}")
    @Timed
    public ResponseEntity<RecordingTechnology> getRecordingTechnology(@PathVariable Long id) {
        log.debug("REST request to get RecordingTechnology : {}", id);
        Optional<RecordingTechnology> recordingTechnology = recordingTechnologyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recordingTechnology);
    }

    /**
     * DELETE  /recording-technologies/:id : delete the "id" recordingTechnology.
     *
     * @param id the id of the recordingTechnology to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recording-technologies/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecordingTechnology(@PathVariable Long id) {
        log.debug("REST request to delete RecordingTechnology : {}", id);

        recordingTechnologyRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
