package com.mx.axeleratum.americantower.contract.origin.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mx.axeleratum.americantower.contract.core.model.ContractLand;
import com.mx.axeleratum.americantower.contract.origin.dto.ContractLandDto;
import com.mx.axeleratum.americantower.contract.origin.dto.entry.ContractLandDtoEntry;
import com.mx.axeleratum.americantower.contract.origin.mapper.ContratMapper;
import com.mx.axeleratum.americantower.contract.origin.service.ContractService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/land")
@Api(tags = "ContractLandDto Controller Methods", description = "ContractLandDto Controller")
@Slf4j
public class ContractLandController {

	@Autowired
	ContractService contratService;

	@Autowired
	private ContratMapper contratMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a land contract given an id", notes = "Returns a land  contract given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<ContractLandDto> findContratLand(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractLand contract = contratService.findContractLandById(id);
		return ResponseEntity.ok(contratMapper.toContractLandDto(contract));
	}

	@GetMapping("/")
	@ApiOperation(value = "Get all land contracts by page", notes = "Returns a list of land contracts by page.", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<ContractLandDto>> findContratLandPage(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<ContractLand> page = contratService.findAllContractLands(pageable);
		return ResponseEntity.ok(page.map(contratMapper::toContractLandDto));
	}

	@GetMapping("/")
	@ApiOperation(value = "Get all land contracts by page", notes = "Returns a list of land contracts by page.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<ContractLandDto>> findAllContratLand(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractLand> contracts = contratService.findAllContractLands();
		return ResponseEntity.ok(contratMapper.toContractLandDtoList(contracts));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all land contracts", notes = "Returns a list of land contracts.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<ContractLandDto>> findContratLand(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractLand> contracts = contratService.findAllContractLands();
		return ResponseEntity.ok(contratMapper.toContractLandDtoList(contracts));
	}

	@PostMapping
	@ApiOperation(value = "Add a new contract Land", notes = "Returns the created contract land", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ContractLandDto addContratLand(@RequestBody ContractLandDtoEntry contractLandDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractLand contractLand = contratService.addContractLand(contratMapper.toContractLandFromEntry(contractLandDto));
		return contratMapper.toContractLandDto(contractLand);
	}
	
	@PostMapping("/batch")
	@ApiOperation(value = "Add a new contract Land", notes = "Returns the created contract land", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ContractLandDto addContratLand(@RequestBody ContractLandDto contractLandDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale,
			@RequestBody List<ContractLandDtoEntry> notificationsDto) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractLand contractLand = contratService.addContractLand(contratMapper.toContractLand(contractLandDto));
		return contratMapper.toContractLandDto(contractLand);
	}
	
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a alert by id", notes = "Delete a alert by id", produces = "application/json")
	public void deleteNotification(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		contratService.deleteContractLandByID(id);
	}
	
	@DeleteMapping
	@ApiOperation(value = "Delete all alers", notes = "Delete all alers", produces = "application/json")
	public void deleteNotification(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		contratService.deleteAllContractLand();
	}
}
