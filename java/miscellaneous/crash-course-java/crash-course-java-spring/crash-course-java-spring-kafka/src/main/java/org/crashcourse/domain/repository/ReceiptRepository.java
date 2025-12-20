package org.crashcourse.domain.repository;

import java.util.Optional;

import org.crashcourse.domain.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>, JpaSpecificationExecutor<Receipt>, PagingAndSortingRepository<Receipt, Long> {

    Optional<Receipt> findByCode(final String code);
}
