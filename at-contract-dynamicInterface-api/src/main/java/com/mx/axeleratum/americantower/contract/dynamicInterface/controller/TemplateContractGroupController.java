package com.mx.axeleratum.americantower.contract.dynamicInterface.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplateGroup;
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateGroupDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.MasterTemplateMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.TemplateContractMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.TemplateContractGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/templates/contract/groups")
@Api(tags = "Dinamic Contract Interface Controller Methods", description = "Dinamic Contract Interface Controller")
@Slf4j
public class TemplateContractGroupController {

	@Autowired
	TemplateContractGroupService templateContractGroupService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private TemplateContractMapper templateContractMapper;

	@Autowired
	MasterTemplateMapper masterTemplateMapper;

	@PostMapping
	@ApiOperation(value = "Add a new group contract template", notes = "Returns add a new group contract template", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ContractTemplateGroupDto> addTemplateContractGroup(
			@RequestBody ContractTemplateGroupDto groupDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTemplateGroup group = templateContractGroupService
				.save(templateContractMapper.toContractTemplateGroup(groupDto));
		return ResponseEntity.ok(templateContractMapper.toContractTemplateGroupDto(group));
	}

	@PutMapping
	@ApiOperation(value = "Save changes to the group contract template", notes = "Returns the  group to contract template with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ContractTemplateGroupDto> saveTemplateContract(@RequestBody ContractTemplateGroupDto groupDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(groupDto, "El grupo no puede ser nulo");
		Preconditions.checkNotNull(groupDto.getId(), "id del grupo no puede ser nulo");
		RestPreconditions.checkFound(templateContractGroupService.findById(groupDto.getId()),
				"No se encontro el grupo con id: " + groupDto.getId() + ". El grupo no puede ser modificado");
		ContractTemplateGroup group = templateContractGroupService
				.save(templateContractMapper.toContractTemplateGroup(groupDto));
		return ResponseEntity.ok(templateContractMapper.toContractTemplateGroupDto(group));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a group contract template given an id", notes = "Returns a group a contract template given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<ContractTemplateGroupDto> findContractById(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTemplateGroup group = templateContractGroupService.findById(id);
		return ResponseEntity.ok(templateContractMapper.toContractTemplateGroupDto(group));
	}

	@GetMapping("/name/{name}")
	@ApiOperation(value = "Get a group contract template by name", notes = "Returns a group contract template by name.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<ContractTemplateGroupDto> findContractTemplateInstance(@PathVariable("name") String name,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTemplateGroup group = templateContractGroupService.findByName(name);
		return ResponseEntity.ok(templateContractMapper.toContractTemplateGroupDto(group));
	}

	@GetMapping
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "Get all list of groups contract template by page", notes = "Returns a list of groups contract templates by page.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<ContractTemplateGroupDto>> findAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<ContractTemplateGroup> page = templateContractGroupService.findAll(pageable);
		return ResponseEntity.ok(page.map(templateContractMapper::toContractTemplateGroupDto));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a group contract template given an id", notes = "Delete a group contract template given an id", produces = "application/json")
	public void deleteContractTemplate(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swaggerui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(id, "id del grupo no puede ser nulo");
		RestPreconditions.checkFound(templateContractGroupService.findById(id), "No se encontro el grupo con id " + id);
		templateContractGroupService.deleteById(id);
	}

	@DeleteMapping
	@ApiOperation(value = "Delete all group contract template", notes = "Delete all group contract template by id", produces = "application/json")
	public void deleteAllContractTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		templateContractGroupService.deleteAll();
	}

}
