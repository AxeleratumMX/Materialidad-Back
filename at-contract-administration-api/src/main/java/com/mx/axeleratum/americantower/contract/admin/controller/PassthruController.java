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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.mx.axeleratum.americantower.contract.admin.dto.PassthruDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.PassthruMapper;
import com.mx.axeleratum.americantower.contract.admin.service.PassthruService;
import com.mx.axeleratum.americantower.contract.core.model.Passthru;
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/passthru")
@Api(tags = "Passthru Controller Methods", description = "Passthru Controller Methods")
@Slf4j
public class PassthruController {

	@Autowired
	PassthruService passthruService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	PassthruMapper passthruMapper;

	@GetMapping
	@ApiOperation(value = "Get all list of Passthru", notes = "Returns a list of master Passthrus.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<PassthruDto>> findAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Passthru> Passthrus = passthruService.findAll();
		return ResponseEntity.ok(passthruMapper.toListPassthruDto(Passthrus));
	}
	
	@GetMapping("/idActivo/{id}")
	@ApiOperation(value = "Get all list of Passthru", notes = "Returns a list of master Passthrus.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<PassthruDto>> findByIdActivo(@PathVariable("id") Long id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Passthru> Passthrus = passthruService.findByIdActivo(id);
		return ResponseEntity.ok(passthruMapper.toListPassthruDto(Passthrus));
	}

	@PutMapping
	@ApiOperation(value = "Save changes to the Passthru", notes = "Returns the Passthru with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PassthruDto> saveTemplateContract(@RequestBody PassthruDto PassthruDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(PassthruDto, "El Passthru no puede ser nulo");
		Preconditions.checkNotNull(PassthruDto.getId(), "id del Passthru no puede ser nulo");
		RestPreconditions.checkFound(passthruService.findById(PassthruDto.getId()),
				"No se encontro el Passthru con id: " + PassthruDto.getId()
						+ ". El template no puede ser modificado");
		Passthru Passthru = passthruService.save(passthruMapper.toPassthru(PassthruDto));
		return ResponseEntity.ok(passthruMapper.toPassthruDto(Passthru));
	}

	@PostMapping
	@ApiOperation(value = "Created a new Passthru", notes = "Returns the new Passthru", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<PassthruDto> newTemplateContract(@RequestBody PassthruDto PassthruDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(PassthruDto, "El Passthru no puede ser nulo");
		Passthru Passthru = passthruService.insert(passthruMapper.toPassthru(PassthruDto));
		return ResponseEntity.ok(passthruMapper.toPassthruDto(Passthru));
	}
	
	@DeleteMapping()
	@ApiOperation(value = "Delete all Passthrus", notes = "Delete all Passthrus", produces = "application/json")
	public void deleteAllPassthrus(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		passthruService.deleteAll();
	}
}
