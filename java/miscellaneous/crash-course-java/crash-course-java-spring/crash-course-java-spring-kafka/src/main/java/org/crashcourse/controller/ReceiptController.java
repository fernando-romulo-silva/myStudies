package org.crashcourse.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import org.crashcourse.domain.model.Receipt;
import org.crashcourse.service.ReceiptService;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@Description("Controller for image type API")
@RequestMapping(ReceiptController.NOTA_URL)
public class ReceiptController {
    
    public static final String NOTA_URL = "/receipt";
    
    private final ReceiptService service;

    ReceiptController(final ReceiptService service) {
	this.service = service;
    }
    
    @ResponseStatus(OK)
    @GetMapping(value = "/{id:[\\d]*}", produces = APPLICATION_JSON_VALUE)
    public Receipt getById(@PathVariable(name = "id", required = true) final Long id) {
	return service.findById(id);
    }

    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<Receipt> getByFilter(
		    @Filter
		    final Specification<Receipt> filter,
		    
		    @PageableDefault(value = 10, page = 0)
		    final Pageable page) {

	return service.findBySpecification(filter, page);
    }
    
    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = { TEXT_PLAIN_VALUE, APPLICATION_JSON_VALUE })
    public void create(
		    @RequestBody
		    final Receipt nota,
		    final HttpServletResponse response) {

	final var result = service.save(nota);
	
	response.addHeader("Location", NOTA_URL + "/" + result.getId());
    }
}
