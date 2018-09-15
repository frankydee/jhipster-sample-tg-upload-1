package com.sys_integrator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sys_integrator.domain.FileType;
import com.sys_integrator.repository.FileTypeRepository;
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
 * REST controller for managing FileType.
 */
@RestController
@RequestMapping("/api")
public class FileTypeResource {

    private final Logger log = LoggerFactory.getLogger(FileTypeResource.class);

    private static final String ENTITY_NAME = "fileType";

    private final FileTypeRepository fileTypeRepository;

    public FileTypeResource(FileTypeRepository fileTypeRepository) {
        this.fileTypeRepository = fileTypeRepository;
    }

    /**
     * POST  /file-types : Create a new fileType.
     *
     * @param fileType the fileType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileType, or with status 400 (Bad Request) if the fileType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-types")
    @Timed
    public ResponseEntity<FileType> createFileType(@Valid @RequestBody FileType fileType) throws URISyntaxException {
        log.debug("REST request to save FileType : {}", fileType);
        if (fileType.getId() != null) {
            throw new BadRequestAlertException("A new fileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileType result = fileTypeRepository.save(fileType);
        return ResponseEntity.created(new URI("/api/file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-types : Updates an existing fileType.
     *
     * @param fileType the fileType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileType,
     * or with status 400 (Bad Request) if the fileType is not valid,
     * or with status 500 (Internal Server Error) if the fileType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-types")
    @Timed
    public ResponseEntity<FileType> updateFileType(@Valid @RequestBody FileType fileType) throws URISyntaxException {
        log.debug("REST request to update FileType : {}", fileType);
        if (fileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileType result = fileTypeRepository.save(fileType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-types : get all the fileTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fileTypes in body
     */
    @GetMapping("/file-types")
    @Timed
    public List<FileType> getAllFileTypes() {
        log.debug("REST request to get all FileTypes");
        return fileTypeRepository.findAll();
    }

    /**
     * GET  /file-types/:id : get the "id" fileType.
     *
     * @param id the id of the fileType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileType, or with status 404 (Not Found)
     */
    @GetMapping("/file-types/{id}")
    @Timed
    public ResponseEntity<FileType> getFileType(@PathVariable Long id) {
        log.debug("REST request to get FileType : {}", id);
        Optional<FileType> fileType = fileTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fileType);
    }

    /**
     * DELETE  /file-types/:id : delete the "id" fileType.
     *
     * @param id the id of the fileType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileType(@PathVariable Long id) {
        log.debug("REST request to delete FileType : {}", id);

        fileTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
