package com.sys_integrator.repository;

import com.sys_integrator.domain.UploadedFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UploadedFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {

}
