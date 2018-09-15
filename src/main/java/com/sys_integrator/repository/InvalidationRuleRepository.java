package com.sys_integrator.repository;

import com.sys_integrator.domain.InvalidationRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InvalidationRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvalidationRuleRepository extends JpaRepository<InvalidationRule, Long> {

}
