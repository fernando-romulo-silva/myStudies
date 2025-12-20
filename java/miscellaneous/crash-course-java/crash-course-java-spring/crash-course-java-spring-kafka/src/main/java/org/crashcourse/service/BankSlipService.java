package org.crashcourse.service;

import static java.text.MessageFormat.format;

import org.crashcourse.domain.model.BankSlip;
import org.crashcourse.domain.repository.BankSlipRepository;
import org.crashcourse.infra.config.logging.Loggable;
import org.crashcourse.infra.dto.ProcessDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Loggable
@Service
@Transactional
public class BankSlipService {

    private final BankSlipRepository repository;
    
    private final ReceiptService receiptService;
    
    BankSlipService(final BankSlipRepository repository, final ReceiptService notaService) {
	this.repository = repository;
	this.receiptService = notaService;
    }
    
    public BankSlip save(final BankSlip bankSlip) {

	final var result = repository.save(bankSlip);

	return result;
    }
    
    public void execute(final ProcessDTO processDTO) {
	
	final var receipt = receiptService.findByNumero(processDTO.receiptCode());
	final var bankSlip = new BankSlip(processDTO.bankSlipCode(), receipt);
	
	save(bankSlip);
    }
    
    @Transactional(readOnly = true)
    public Page<BankSlip> findBySpecification(final Specification<BankSlip> filter, final Pageable page) {
	return repository.findAll(filter, page);
    }

    @Transactional(readOnly = true)
    public BankSlip findById(final Long id) {
	return repository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(format("BankSlip with id ''{0}'' not found", id)));
    }
}
