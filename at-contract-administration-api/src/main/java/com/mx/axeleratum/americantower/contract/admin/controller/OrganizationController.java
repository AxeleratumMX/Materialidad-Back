package com.mx.axeleratum.americantower.contract.admin.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.mx.axeleratum.americantower.contract.admin.dto.OrganizationDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.OrganizationMapper;
import com.mx.axeleratum.americantower.contract.admin.service.OrganizationService;
import com.mx.axeleratum.americantower.contract.core.model.Organization;
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/organization")
@Api(tags = "Organization Controller Methods", description = "Organization Controller Methods")
@Slf4j

public class OrganizationController {

	@Autowired
	OrganizationService organizationService;

	@Autowired
	private OrganizationMapper organizationMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "Get all list of organizatios by page", notes = "Returns a list of organizatios by page.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<OrganizationDto>> findAllByPage(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Organization> page = organizationService.findAll(pageable);
		return ResponseEntity.ok(page.map(organizationMapper::toOrganizationDto));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all list of organizations", notes = "Returns a list of organizations.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<OrganizationDto>> findAll(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Organization> organizations = organizationService.findAll();
		return ResponseEntity.ok(organizationMapper.toListToOrganizationDto(organizations));
	}
	
	@PostMapping
	@ApiOperation(value = "Add a new organization", notes = "Returns Add a new organization", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<OrganizationDto> add(@RequestBody OrganizationDto organizationDto,
																   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
														   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(organizationDto, "La organizacion no puede ser nulo");
		Organization organization = organizationService.save(organizationMapper.toOrganization(organizationDto));
		return ResponseEntity.ok(organizationMapper.toOrganizationDto(organization));
	}
	
	@PutMapping
	@ApiOperation(value = "Save changes to organization", notes = "Returns the  organization with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<OrganizationDto> save(@RequestBody OrganizationDto organizationDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(organizationDto, "El usuario no puede ser nulo");
		Preconditions.checkNotNull(organizationDto.getId(),  "id del usuario no puede ser nulo");
		RestPreconditions.checkFound(organizationService.findById(organizationDto.getId()),"No se encontro el usuario con id "+ organizationDto.getId());
		Organization organization = organizationService.save(organizationMapper.toOrganization(organizationDto));
		return ResponseEntity.ok(organizationMapper.toOrganizationDto(organization));
	}
	
}
