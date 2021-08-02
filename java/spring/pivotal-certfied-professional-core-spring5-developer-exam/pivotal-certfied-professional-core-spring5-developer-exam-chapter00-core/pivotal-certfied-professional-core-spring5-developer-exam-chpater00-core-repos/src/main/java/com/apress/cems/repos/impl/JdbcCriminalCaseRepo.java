package com.apress.cems.repos.impl;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.repos.CriminalCaseRepo;
import com.apress.cems.repos.util.CriminalCaseRowMapper;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.CaseType;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Repository
public class JdbcCriminalCaseRepo  extends JdbcAbstractRepo<CriminalCase> implements CriminalCaseRepo {
    private RowMapper<CriminalCase> rowMapper = new CriminalCaseRowMapper();

    public JdbcCriminalCaseRepo(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<CriminalCase> findById(Long id) {
        var sql = "select ID, CASE_NUMBER, CASE_TYPE, STATUS, SHORT_DESCRIPTION from CRIMINAL_CASE where ID= ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Set<CriminalCase> findByLeadInvestigator(Detective detective) {
        var sql =  "select ID, CASE_NUMBER, CASE_TYPE, STATUS, SHORT_DESCRIPTION from CRIMINAL_CASE c, DETECTIVE d where c.LEAD_INVESTIGATOR=d.ID and d.ID= ?";
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{detective.getId()}, rowMapper));
    }

    @Override
    public Optional<CriminalCase> findByNumber(String caseNumber) {
        var sql = "select ID, CASE_NUMBER, CASE_TYPE, STATUS, SHORT_DESCRIPTION from CRIMINAL_CASE where CASE_NUMBER= ?";
        var result = jdbcTemplate.queryForObject(sql, rowMapper, caseNumber);
        return result == null ? Optional.empty() :  Optional.of(result);
    }

    @Override
    public void save(CriminalCase cc) {
        jdbcTemplate.update(
                "insert into CRIMINAL_CASE(ID, CASE_NUMBER, CASE_TYPE, STATUS, SHORT_DESCRIPTION, LEAD_INVESTIGATOR ) values(?,?,?,?,?,?,?)",
                cc.getId(), cc.getNumber(), cc.getType(), cc.getStatus(), cc.getShortDescription(), cc.getLeadInvestigator().getId()
        );
    }

    @Override
    public void delete(CriminalCase entity) {
        jdbcTemplate.update("delete from CRIMINAL_CASE where ID =? ", entity.getId());
    }

    @Override
    public int deleteById(Long entityId) {
        return jdbcTemplate.update("delete from CRIMINAL_CASE where ID =? ", entityId);
    }

    @Override
    public Set<CriminalCase> findByStatus(CaseStatus status) {
        throw new NotImplementedException("Not needed for this implementation.");
    }

    @Override
    public Set<CriminalCase> findByType(CaseType type) {
        throw new NotImplementedException("Not needed for this implementation.");
    }
}
