package com.sys_integrator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sys_integrator.domain.FileByTechnology;
import com.sys_integrator.repository.FileByTechnologyRepository;
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
 * REST controller for managing FileByTechnology.
 */
@RestController
@RequestMapping("/api")
public class FileByTechnologyResource {

    private final Logger log = LoggerFactory.getLogger(FileByTechnologyResource.class);

    private static final String ENTITY_NAME = "fileByTechnology";

    private final FileByTechnologyRepository fileByTechnologyRepository;

    public FileByTechnologyResource(FileByTechnologyRepository fileByTechnologyRepository) {
        this.fileByTechnologyRepository = fileByTechnologyRepository;
    }

    /**
     * POST  /file-by-technologies : Create a new fileByTechnology.
     *
     * @param fileByTechnology the fileByTechnology to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileByTechnology, or with status 400 (Bad Request) if the fileByTechnology has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-by-technologies")
    @Timed
    public ResponseEntity<FileByTechnology> createFileByTechnology(@RequestBody FileByTechnology fileByTechnology) throws URISyntaxException {
        log.debug("REST request to save FileByTechnology : {}", fileByTechnology);
        if (fileByTechnology.getId() != null) {
            throw new BadRequestAlertException("A new fileByTechnology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileByTechnology result = fileByTechnologyRepository.save(fileByTechnology);
        return ResponseEntity.created(new URI("/api/file-by-technologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-by-technologies : Updates an existing fileByTechnology.
     *
     * @param fileByTechnology the fileByTechnology to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileByTechnology,
     * or with status 400 (Bad Request) if the fileByTechnology is not valid,
     * or with status 500 (Internal Server Error) if the fileByTechnology couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-by-technologies")
    @Timed
    public ResponseEntity<FileByTechnology> updateFileByTechnology(@RequestBody FileByTechnology fileByTechnology) throws URISyntaxException {
        log.debug("REST request to update FileByTechnology : {}", fileByTechnology);
        if (fileByTechnology.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileByTechnology result = fileByTechnologyRepository.save(fileByTechnology);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileByTechnology.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-by-technologies : get all the fileByTechnologies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fileByTechnologies in body
     */
    @GetMapping("/file-by-technologies")
    @Timed
    public List<FileByTechnology> getAllFileByTechnologies() {
        log.debug("REST request to get all FileByTechnologies");
        return fileByTechnologyRepository.findAll();
    }

    /**
     * GET  /file-by-technologies/:id : get the "id" fileByTechnology.
     *
     * @param id the id of the fileByTechnology to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileByTechnology, or with status 404 (Not Found)
     */
    @GetMapping("/file-by-technologies/{id}")
    @Timed
    public ResponseEntity<FileByTechnology> getFileByTechnology(@PathVariable Long id) {
        log.debug("REST request to get FileByTechnology : {}", id);
        Optional<FileByTechnology> fileByTechnology = fileByTechnologyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fileByTechnology);
    }

    /**
     * DELETE  /file-by-technologies/:id : delete the "id" fileByTechnology.
     *
     * @param id the id of the fileByTechnology to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-by-technologies/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileByTechnology(@PathVariable Long id) {
        log.debug("REST request to delete FileByTechnology : {}", id);

        fileByTechnologyRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
