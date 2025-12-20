package org.crashcourse.domain.repository;

import java.util.Optional;

import org.crashcourse.domain.model.BankSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankSlipRepository extends JpaRepository<BankSlip, Long>, JpaSpecificationExecutor<BankSlip>, PagingAndSortingRepository<BankSlip, Long> {

    Optional<BankSlip> findByCode(final String code);
}
