package com.sys_integrator.repository;

import com.sys_integrator.domain.InvalidationCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvalidationCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvalidationCodeRepository extends JpaRepository<InvalidationCode, Long> {

}
