package com.mx.axeleratum.americantower.contract.dynamicInterface.controller;

import com.google.common.base.Enums;
import com.google.common.base.Preconditions;
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TemplateHeaderDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TypeMasterContract;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.DynamicInterfaceMapper;
import com.mx.axeleratum.americantower.contract.core.model.Template;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.TemplateService;
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
@RequestMapping("/templates")
@Api(tags = "Dinamic Contract Interface Controller Methods", description = "Dinamic Contract Interface Controller")
@Slf4j
public class DynamicInterfaceController {

	@Autowired
	TemplateService dynamicInterfaceService;

	@Autowired
	private DynamicInterfaceMapper dynamicInterfaceMapperMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a template given the contract id", notes = "Returns a template given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<TemplateDto> findDynamicContractInterfaceTemplate(@PathVariable("id") String id,
																			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Template template = RestPreconditions.checkFound(dynamicInterfaceService.findTemplateById(id), "No se encontro template con id "+id);
		return ResponseEntity.ok(dynamicInterfaceMapperMapper.toTemplateDto(template));
	}
	
	@GetMapping("/client/{idClient}")
	@ApiOperation(value = "Get a list of template given the client id.", 
	              notes = "Returns a list of template given the client id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<TemplateDto>> findTemplatesByClientId(
			@PathVariable("idClient") String idClient,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
	    List<Template> templates = dynamicInterfaceService.findTemplateByClientId(idClient);
	    return ResponseEntity.ok(dynamicInterfaceMapperMapper.toListTemplateDto(templates));
	}
	
	@GetMapping("/type/{type}")
	@ApiOperation(value = "Get a new template by tipo contrato tower/land", notes = "Returns a new template by tipo contrato TOWER/LAND", produces = "application/json")
	public ResponseEntity<TemplateDto> addTemplate(
			@ApiParam(value = "The contract type tower/land") @PathVariable("type") String type,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(type,"El tipo del template no puede ser nulo");
		RestPreconditions.checkIsTrue(Enums.getIfPresent(TypeMasterContract.class, type).isPresent(),"El tipo de contrato es invalido");
		Template template = dynamicInterfaceService.getTemplatByType(type);
		return ResponseEntity.ok(dynamicInterfaceMapperMapper.toTemplateDto(template));
	}

	@PutMapping
	@ApiOperation(value = "Save changes to the template", notes = "Returns the template with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TemplateDto> saveTemplate(@RequestBody TemplateDto templateDto,
													@ApiIgnore("Ignored because swagger ui shows the wrong params, "
															+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(templateDto, "La template no puede ser nulo");
		Template template = dynamicInterfaceService.saveTemplate(dynamicInterfaceMapperMapper.toTemplate(templateDto));
		return ResponseEntity.ok(dynamicInterfaceMapperMapper.toTemplateDto(template));
	}


	@GetMapping()
	@ApiOperation(value = "Get a list of template by page", notes = "Returns a list of template by page.", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=lastModifiedDate,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<TemplateHeaderDto>> findPageableTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Template> page = dynamicInterfaceService.findAllPageablesTemplates(pageable);
		return ResponseEntity.ok(page.map(dynamicInterfaceMapperMapper::toTemplateHeaderDto));
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "Get all list of template", notes = "Returns a list of templates.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<TemplateHeaderDto>> findAllTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Template> templates = dynamicInterfaceService.findAllTemplates();
		return ResponseEntity.ok(dynamicInterfaceMapperMapper.toTemplateHeaderDtos(templates));
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
		RestPreconditions.checkFound(dynamicInterfaceService.findTemplateById(id),"No se encontro el template con id "+ id );
		dynamicInterfaceService.deleteTemplateById(id);
	}
	
	@DeleteMapping()
	@ApiOperation(value = "Delete all templates", notes = "Delete all templates", produces = "application/json")
	public void deleteAllTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		dynamicInterfaceService.deleteAlldTemplates();
	}

	@GetMapping("/name/like/{name}")
	@ApiOperation(value = "Get a list of template given the like nombre.", notes = "Returns a list of template given the client id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<TemplateDto>> findTemplatesByNameLike(@PathVariable("name") String name,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Template> templates = dynamicInterfaceService.findATemplateByNombreLike(name);
		return ResponseEntity.ok(dynamicInterfaceMapperMapper.toListTemplateDto(templates));
	}	
}
