package com.apress.cems.pojos.repos;

import java.util.Optional;
import java.util.Set;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface CriminalCaseRepo extends AbstractRepo<CriminalCase> {

    Set<CriminalCase> findByLeadInvestigator(Detective detective);

    Optional<CriminalCase> findByNumber(String caseNumber);

}
