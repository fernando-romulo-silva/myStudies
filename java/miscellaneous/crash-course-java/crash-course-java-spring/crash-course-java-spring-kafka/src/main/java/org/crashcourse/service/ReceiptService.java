package org.crashcourse.service;

import static java.text.MessageFormat.format;

import org.crashcourse.domain.model.Receipt;
import org.crashcourse.domain.repository.ReceiptRepository;
import org.crashcourse.infra.config.logging.Loggable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
@Loggable
public class ReceiptService {

    private ReceiptRepository repository;
    
    ReceiptService(final ReceiptRepository repository) {
	this.repository = repository;
    }
    
    @Transactional
    public Receipt save(@NotNull @Valid final Receipt nota) {

	final var notaResult = repository.save(nota);

	return notaResult;
    }
    
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<Receipt> findBySpecification(final Specification<Receipt> filter, final Pageable page) {
	return repository.findAll(filter, page);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Receipt findById(final Long id) {
	return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(format("Receipt de id ''{0}'' nao encontrada", id)));
    }
    
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Receipt findByNumero(final String numero) {
	return repository.findByCode(numero)
			.orElseThrow(() -> new IllegalArgumentException(format("Receipt de id ''{0}'' nao encontrada", numero)));
    }
}
