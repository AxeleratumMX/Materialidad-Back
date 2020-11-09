package com.mx.axeleratum.americantower.contract.origin.controller;

import com.mx.axeleratum.americantower.contract.core.model.ContractTower;
import com.mx.axeleratum.americantower.contract.origin.dto.ContratTowerDto;
import com.mx.axeleratum.americantower.contract.origin.mapper.ContratMapper;
import com.mx.axeleratum.americantower.contract.origin.service.ContractService;
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
@RequestMapping("/tower")
@Api(tags = "ContratTowerDto Controller Methods", description = "ContratTowerDto Controller")
@Slf4j
public class ContractTowerController {

	@Autowired
	ContractService contratService;

	@Autowired
	private ContratMapper contratMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a tower contract given an id", notes = "Returns a tower contract given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<ContratTowerDto> findContratTower(@PathVariable("id") String id,
                                                       @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTower contrat = contratService.findContractTowerById(id);
		return ResponseEntity.ok(contratMapper.toContractTowerDto(contrat));
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "Get all tower contracts", notes = "Returns a list of tower contracts.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<ContratTowerDto>> findNotification(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractTower> contrats = contratService.findAllContratTower();
		return ResponseEntity.ok(contratMapper.toListContractTowerDto(contrats));
	}

	@GetMapping
	@ApiOperation(value = "Get all tower contracts by page", notes = "Returns a list of tower contracts by page.", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<ContratTowerDto>> findContratTower(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
                                                             @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<ContractTower> page = contratService.findAllContractTowers(pageable);
		return ResponseEntity.ok(page.map(contratMapper::toContractTowerDto));
	}

	@PostMapping
	@ApiOperation(value = "Add a new contract tower", notes = "Returns the created contract tower", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ContratTowerDto addContratTower(@RequestBody ContratTowerDto contractTowerDto,
                                      @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTower contratTower = contratService.addContractTower(contratMapper.toContractTower(contractTowerDto));
		return contratMapper.toContractTowerDto(contratTower);
	}
	
	@PostMapping("/batch")
	@ApiOperation(value = "Add a list of new Contrats Tower", notes = "Returns the list Contrats Tower", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Page<ContratTowerDto>> addAllNotification(
			@RequestBody List<ContratTowerDto> contratsTower,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<ContractTower> page = contratService
				.addAllContractTower(contratMapper.toContractList(contratsTower));
		return ResponseEntity.ok(page.map(contratMapper::toContractTowerDto));
	}
	

	@ApiOperation(value = "Delete a Contrat Tower by id", notes = "Delete a Contrats Tower by id", produces = "application/json")
	@DeleteMapping("/{id}")
	public void deleteNotification(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		contratService.deleteContratTowerById(id);
	}
	
	@ApiOperation(value = "Delete all Contrats Tower", notes = "Delete all Contrats Tower", produces = "application/json")
	@DeleteMapping
	public void deleteNotification(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		contratService.deleteAllContratTower();
	}
	
}
