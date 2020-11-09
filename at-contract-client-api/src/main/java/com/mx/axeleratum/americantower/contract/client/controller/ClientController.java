package com.mx.axeleratum.americantower.contract.client.controller;

import com.mx.axeleratum.americantower.contract.client.dto.ClientDto;
import com.mx.axeleratum.americantower.contract.client.mapper.ClientMapper;
import com.mx.axeleratum.americantower.contract.client.service.ClientService;
import com.mx.axeleratum.americantower.contract.core.model.Client;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/")
@Api(tags = "ClientDto Controller Methods", description = "ClientDto Controller")
@Slf4j
public class ClientController {

	@Autowired
	ClientService clientService;

	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a client given an id", notes = "Returns a client given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<ClientDto> findClient(@PathVariable("id") String id,
												@ApiIgnore("Ignored because swagger ui shows the wrong params, "
														+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Client client = clientService.findById(id);
		return ResponseEntity.ok(clientMapper.toClientDto(client));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all clients", notes = "Returns a list of clients", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<ClientDto>> findAllClient(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Client> clients = clientService.findAll();
		return ResponseEntity.ok(clientMapper.toClientDtoList(clients));
	}

	@GetMapping("/nombre/like/{searchName}")
	@ApiOperation(value = "Get all clients by nombre is like search str", notes = "Returns a list of clients clients by nombre is like search str", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<ClientDto>> findAllClient(
			@PathVariable("searchName") String name,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Client> clients = clientService.findAllByNombreLike(name);
		return ResponseEntity.ok(clientMapper.toClientDtoList(clients));
	}


	@GetMapping
	@ApiOperation(value = "Get all clients by page", notes = "Returns a list of clients in a page", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<ClientDto>> findClient(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
													  @ApiIgnore("Ignored because swagger ui shows the wrong params, "
															  + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Client> page = clientService.findAll(pageable);
		return ResponseEntity.ok(page.map(clientMapper::toClientDto));
	}


	@PostMapping
	@ApiOperation(value = "Add a new client", notes = "Returns the created client", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ClientDto addClient(@RequestBody ClientDto clientDto,
							   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
									   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Client client = clientService.addClient(clientMapper.toClient(clientDto));
		return clientMapper.toClientDto(client);
	}

	@PatchMapping
	@ApiOperation(value = "Add a new client", notes = "Returns the created client", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addContacts(@RequestBody ClientDto clientDto,
							   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
									   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		clientService.addContacts(clientMapper.toClient(clientDto));
	}


	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a client by id", notes = "Delete a client by id", produces = "application/json")
	public void deleteNotification(@PathVariable("id") String id,
								   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
										   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		clientService.deleteById(id);
	}

	@DeleteMapping
	@ApiOperation(value = "Delete all clients", notes = "Delete all clients", produces = "application/json")
	public void deleteNotification(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		clientService.deleteAll();
	}

}
