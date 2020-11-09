package com.mx.axeleratum.americantower.contract.dynamicInterface.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.MasterTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.MasterTemplateMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.MasterTemplateService;
import com.querydsl.core.types.Predicate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/templates/master")
@Api(tags = "Administrative Master Template Controller Methods", description = "Administrative Master Template Controller Methods")
@Slf4j
public class MasterTemplateController {

	@Autowired
	MasterTemplateService masterTemplateService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@Autowired
	MasterTemplateMapper masterTemplateMapper;

	@GetMapping()
	@ApiOperation(value = "Get a list of maste template by page", notes = "Returns a list of master template by page.", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=lastModifiedDate,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<MasterTemplateDto>> findPageableTemplates(@QuerydslPredicate(root = MasterTemplate.class) Predicate predicate,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<MasterTemplate> page = masterTemplateService.findAllPageablesTemplates(pageable,predicate);
		return ResponseEntity.ok(page.map(masterTemplateMapper::toMasterTemplateDto));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all list of master template", notes = "Returns a list of master templates.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<MasterTemplateDto>> findAllTemplates(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<MasterTemplate> templates = masterTemplateService.findAllMasterTemplate();
		return ResponseEntity.ok(masterTemplateMapper.toListMasterTemplateDto(templates));
	}

	@PutMapping
	@ApiOperation(value = "Save changes to the master template", notes = "Returns the  master template with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MasterTemplateDto> saveTemplateContract(@RequestBody MasterTemplateDto masterTemplateDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(masterTemplateDto, "El contract template no puede ser nulo");
		Preconditions.checkNotNull(masterTemplateDto.getId(), "id del contract template no puede ser nulo");
		RestPreconditions.checkFound(masterTemplateService.findById(masterTemplateDto.getId()),
				"No se encontro el contract template con id: " + masterTemplateDto.getId()
						+ ". El template no puede ser modificado");
		MasterTemplate masterTemplate = masterTemplateService
				.saveTemplate(masterTemplateMapper.toMaterTemplate(masterTemplateDto));
		return ResponseEntity.ok(masterTemplateMapper.toMasterTemplateDto(masterTemplate));
	}

	@PostMapping
	@ApiOperation(value = "Created a new master template", notes = "Returns the new master template", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MasterTemplateDto> newTemplateContract(@RequestBody MasterTemplateDto masterTemplateDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(masterTemplateDto, "El contract template no puede ser nulo");
		MasterTemplate masterTemplate = masterTemplateService
				.insertTemplate(masterTemplateMapper.toMaterTemplate(masterTemplateDto));
		return ResponseEntity.ok(masterTemplateMapper.toMasterTemplateDto(masterTemplate));
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a template by id", notes = "Delete a template by id", produces = "application/json")
	public void deleteTemplate(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(id,"id de template no puede ser nulo");
		RestPreconditions.checkFound(masterTemplateService.findById(id),"No se encontro el template con id "+ id );
		masterTemplateService.deleteTemplateById(id);
	}
	
	@DeleteMapping()
	@ApiOperation(value = "Delete all templates", notes = "Delete all templates", produces = "application/json")
	public void deleteAllTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		masterTemplateService.deleteAllTemplates();
	}
}
