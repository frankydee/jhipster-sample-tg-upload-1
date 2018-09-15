package com.sys_integrator.repository;

import com.sys_integrator.domain.RecordingTechnology;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecordingTechnology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordingTechnologyRepository extends JpaRepository<RecordingTechnology, Long> {

}
