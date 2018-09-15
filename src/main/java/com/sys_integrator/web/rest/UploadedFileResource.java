package com.sys_integrator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sys_integrator.domain.UploadedFile;
import com.sys_integrator.repository.UploadedFileRepository;
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
 * REST controller for managing UploadedFile.
 */
@RestController
@RequestMapping("/api")
public class UploadedFileResource {

    private final Logger log = LoggerFactory.getLogger(UploadedFileResource.class);

    private static final String ENTITY_NAME = "uploadedFile";

    private final UploadedFileRepository uploadedFileRepository;

    public UploadedFileResource(UploadedFileRepository uploadedFileRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
    }

    /**
     * POST  /uploaded-files : Create a new uploadedFile.
     *
     * @param uploadedFile the uploadedFile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uploadedFile, or with status 400 (Bad Request) if the uploadedFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/uploaded-files")
    @Timed
    public ResponseEntity<UploadedFile> createUploadedFile(@RequestBody UploadedFile uploadedFile) throws URISyntaxException {
        log.debug("REST request to save UploadedFile : {}", uploadedFile);
        if (uploadedFile.getId() != null) {
            throw new BadRequestAlertException("A new uploadedFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadedFile result = uploadedFileRepository.save(uploadedFile);
        return ResponseEntity.created(new URI("/api/uploaded-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /uploaded-files : Updates an existing uploadedFile.
     *
     * @param uploadedFile the uploadedFile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uploadedFile,
     * or with status 400 (Bad Request) if the uploadedFile is not valid,
     * or with status 500 (Internal Server Error) if the uploadedFile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/uploaded-files")
    @Timed
    public ResponseEntity<UploadedFile> updateUploadedFile(@RequestBody UploadedFile uploadedFile) throws URISyntaxException {
        log.debug("REST request to update UploadedFile : {}", uploadedFile);
        if (uploadedFile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadedFile result = uploadedFileRepository.save(uploadedFile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uploadedFile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /uploaded-files : get all the uploadedFiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of uploadedFiles in body
     */
    @GetMapping("/uploaded-files")
    @Timed
    public List<UploadedFile> getAllUploadedFiles() {
        log.debug("REST request to get all UploadedFiles");
        return uploadedFileRepository.findAll();
    }

    /**
     * GET  /uploaded-files/:id : get the "id" uploadedFile.
     *
     * @param id the id of the uploadedFile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uploadedFile, or with status 404 (Not Found)
     */
    @GetMapping("/uploaded-files/{id}")
    @Timed
    public ResponseEntity<UploadedFile> getUploadedFile(@PathVariable Long id) {
        log.debug("REST request to get UploadedFile : {}", id);
        Optional<UploadedFile> uploadedFile = uploadedFileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uploadedFile);
    }

    /**
     * DELETE  /uploaded-files/:id : delete the "id" uploadedFile.
     *
     * @param id the id of the uploadedFile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/uploaded-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteUploadedFile(@PathVariable Long id) {
        log.debug("REST request to delete UploadedFile : {}", id);

        uploadedFileRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
