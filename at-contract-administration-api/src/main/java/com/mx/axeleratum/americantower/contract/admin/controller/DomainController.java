package com.mx.axeleratum.americantower.contract.admin.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.mx.axeleratum.americantower.contract.admin.dto.DomainDto;
import com.mx.axeleratum.americantower.contract.admin.dto.DomainValueDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.DomainMapper;
import com.mx.axeleratum.americantower.contract.admin.service.DomainService;
import com.mx.axeleratum.americantower.contract.core.model.Domain;
import com.mx.axeleratum.americantower.contract.core.model.DomainValue;
import com.mx.axeleratum.americantower.contract.core.model.Domains;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/domains")
@Api(tags = "Domain Controller Methods", description = "Controller para obtener las listas de valores")
@Slf4j

public class DomainController {

	@Autowired
	DomainService domainService;

	@Autowired
	private DomainMapper domainMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping()
	@ApiOperation(value = "Get a domain names", notes = "Returns all domain names.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Domains[]> getAllDomainNames(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Domains.values();
		return ResponseEntity.ok(Domains.values());
	}

	@GetMapping("/{domainKey}")
	@ApiOperation(value = "Get a domain values", notes = "Returns a domain values.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<DomainValueDto>> findDomainValues(@PathVariable("domainKey") String domainKey,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Domain domain = domainService.findDomain(domainKey);
		DomainDto domainDto = domainMapper.toDomainDto(domain);
		return ResponseEntity.ok(domainDto.getDomainValues());
	}

	@GetMapping("/{domain}/subdomain/{subDomainKey}")
	@ApiOperation(value = "Get a sub domain values", notes = "Returns a sub domain values.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<DomainValueDto>> findSubDomainValues(@PathVariable("subDomainKey") String subDomainKey,
			@PathVariable("domain") String domain, @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<DomainValue> domainValue = domainService.findSubDomain(domain, subDomainKey);

		return ResponseEntity.ok(domainMapper.toDomainValueDtoList(domainValue));
	}

	@GetMapping("/{domain}/{key}")
	@ApiOperation(value = "Get a sub domain value", notes = "Returns a sub domain value.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<DomainValueDto> findSubDomainValue(@PathVariable("key") String key,
			@PathVariable("domain") String domain, @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		DomainValue domainValue = domainService.findDomainValueByKey(domain, key);

		return ResponseEntity.ok(domainMapper.toDomainValueDto(domainValue));
	}

	@PostMapping
	@ApiOperation(value = "Add a new Domain", notes = "Returns the domain created. Support create domain values", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public DomainDto addDomain(@RequestBody DomainDto domainDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Domain domain = domainMapper.toDomain(domainDto);
		domain = domainService.addDomain(domain);
		return domainMapper.toDomainDto(domain);
	}

	@PostMapping("/{domain}/value")
	@ApiOperation(value = "Add a new Domain", notes = "Returns the domain created. Support create domain values", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public DomainValueDto addDomainValue(@PathVariable("domain") String domain,
			@RequestBody DomainValueDto domainValueDto, @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		DomainValue domainValue = domainService.addDomainValue(domain, domainMapper.toDomainValue(domainValueDto));
		return domainMapper.toDomainValueDto(domainValue);

	}

	@DeleteMapping("/{domain}")
	@ApiOperation(value = "Get a status values", notes = "Returns a domain values.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public void deleteDomain(@PathVariable("domain") String domain,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		domainService.deleteDomain(domain);
	}

	@DeleteMapping("/{domain}/value/{domainValue}")
	@ApiOperation(value = "Get a status values", notes = "Returns a domain values.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public void deleteDomainValue(@PathVariable("domain") String domain,
			@PathVariable("domainValue") String domainValue,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		domainService.deleteDomainValue(domain, domainValue);
	}

	@PostMapping("/TIME_PERIOD")
	@ApiOperation(value = "Add a new Domain", notes = "Returns the domain created. Support create domain values", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public DomainDto addDomain(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		Domain domain = domainService.addPerioodo();		
		return domainMapper.toDomainDto(domain);
	}
}
