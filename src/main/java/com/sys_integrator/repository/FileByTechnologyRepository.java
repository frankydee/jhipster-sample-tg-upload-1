package com.sys_integrator.repository;

import com.sys_integrator.domain.FileByTechnology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileByTechnology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileByTechnologyRepository extends JpaRepository<FileByTechnology, Long> {

}
