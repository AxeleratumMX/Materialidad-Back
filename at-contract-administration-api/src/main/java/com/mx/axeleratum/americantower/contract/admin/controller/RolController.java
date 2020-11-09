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
import com.mx.axeleratum.americantower.contract.admin.dto.RolDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.RolMapper;
import com.mx.axeleratum.americantower.contract.admin.service.RolService;
import com.mx.axeleratum.americantower.contract.core.model.Rol;
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
@RequestMapping("/rols")
@Api(tags = "Rol Controller Methods", description = "Rol Controller Methods")
@Slf4j
public class RolController {

	@Autowired
	RolService rolService;

	@Autowired
	private RolMapper rolMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/name/{name}")
	@ApiOperation(value = "Get a rol given the name", notes = "Returns a rol given the name.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<RolDto> findRolByRolName(@PathVariable("name") String rolName,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Rol rol = RestPreconditions.checkFound(rolService.findByName(rolName),
				"No se encontro el rol con el nombre " + rolName);
		return ResponseEntity.ok(rolMapper.toRolDto(rol));
	}

	@GetMapping()
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "Get all list of rols by page", notes = "Returns a list of rols by page.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<RolDto>> findAllByPage(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Rol> page = rolService.findAll(pageable);
		return ResponseEntity.ok(page.map(rolMapper::toRolDto));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all list of rols", notes = "Returns a list of rols.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<RolDto>> findAll(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Rol> rols = rolService.findAll();
		return ResponseEntity.ok(rolMapper.toListRolDto(rols));
	}
	
	@PostMapping()
	@ApiOperation(value = "Add a new rol", notes = "Returns Add a new rol", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RolDto> add(@RequestBody RolDto rolDto,
																   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
														   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(rolDto, "El rol no puede ser nulo");
		Rol rol = rolMapper.toRol(rolDto);
		RestPreconditions.checkEntity(rol);
		rol = rolService.save(rol);
		return ResponseEntity.ok(rolMapper.toRolDto(rol));
	}
	
	@PutMapping
	@ApiOperation(value = "Save changes to rol", notes = "Returns the  rol with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<RolDto> save(@RequestBody RolDto rolDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(rolDto, "El rol no puede ser nulo");
		Preconditions.checkNotNull(rolDto.getId(),  "id del rol no puede ser nulo");
		RestPreconditions.checkFound(rolService.findById(rolDto.getId()),"No se encontro el rol con id "+ rolDto.getId());
		Rol rol = rolMapper.toRol(rolDto);
		RestPreconditions.checkEntity(rol);
		rol = rolService.save(rol);
		return ResponseEntity.ok(rolMapper.toRolDto(rol));
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a rol by id", notes = "Delete a rol by id", produces = "application/json")
	public void deleteTemplate(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(id,"id de rol no puede ser nulo");
		RestPreconditions.checkFound(rolService.findById(id),"No se encontro el usuario con id "+ id );
		rolService.deleteById(id);
	}
	
	@DeleteMapping()
	@ApiOperation(value = "Delete all rols", notes = "Delete all rols", produces = "application/json")
	public void deleteAllTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		rolService.deleteAll();
	}

}
