package org.crashcourse.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.crashcourse.domain.model.BankSlip;
import org.crashcourse.service.BankSlipService;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

@RestController
@Description("Controller for image type API")
@RequestMapping(BankSlipController.BANK_SLIP_URL)
public class BankSlipController {

    public static final String BANK_SLIP_URL = "/bankslip";
    
    private final BankSlipService service;

    BankSlipController(final BankSlipService service) {
	this.service = service;
    }
    
    @ResponseStatus(OK)
    @GetMapping(value = "/{id:[\\d]*}", produces = APPLICATION_JSON_VALUE)
    public BankSlip getById(@PathVariable(name = "id", required = true) final Long id) {
	return service.findById(id);
    }

    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<BankSlip> getByFilter(
		    @Filter
		    final Specification<BankSlip> filter,
		    
		    @PageableDefault(value = 10, page = 0)
		    final Pageable page) {

	return service.findBySpecification(filter, page);
    }
}
