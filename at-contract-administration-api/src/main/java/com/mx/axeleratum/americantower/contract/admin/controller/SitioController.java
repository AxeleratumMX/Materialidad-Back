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
import com.mx.axeleratum.americantower.contract.admin.dto.SitioDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.SitioMapper;
import com.mx.axeleratum.americantower.contract.admin.service.SitioService;
import com.mx.axeleratum.americantower.contract.core.model.Sitio;
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sitio")
@Api(tags = "Sitio Controller Methods", description = "Sitio Controller Methods")
@Slf4j
public class SitioController {

	@Autowired
	SitioService sitioService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SitioMapper sitioMapper;

	@GetMapping("/all")
	@ApiOperation(value = "Get all list of sitio", notes = "Returns a list of master sitios.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<SitioDto>> findAll(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Sitio> sitios = sitioService.findAll();
		return ResponseEntity.ok(sitioMapper.toListSitioDto(sitios));
	}

	@PutMapping
	@ApiOperation(value = "Save changes to the sitio", notes = "Returns the sitio with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<SitioDto> saveTemplateContract(@RequestBody SitioDto sitioDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(sitioDto, "El sitio no puede ser nulo");
		Preconditions.checkNotNull(sitioDto.getId(), "id del sitio no puede ser nulo");
		RestPreconditions.checkFound(sitioService.findById(sitioDto.getId()),
				"No se encontro el sitio con id: " + sitioDto.getId()
						+ ". El template no puede ser modificado");
		Sitio sitio = sitioService.save(sitioMapper.toSitio(sitioDto));
		return ResponseEntity.ok(sitioMapper.toSitioDto(sitio));
	}

	@PostMapping
	@ApiOperation(value = "Created a new sitio", notes = "Returns the new sitio", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<SitioDto> newTemplateContract(@RequestBody SitioDto SitioDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(SitioDto, "El sitio no puede ser nulo");
		Sitio Sitio = sitioService.insert(sitioMapper.toSitio(SitioDto));
		return ResponseEntity.ok(sitioMapper.toSitioDto(Sitio));
	}
	
	@DeleteMapping()
	@ApiOperation(value = "Delete all sitios", notes = "Delete all sitios", produces = "application/json")
	public void deleteAllsitios(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		sitioService.deleteAll();
	}
	
	@GetMapping("/client/name/{name}")
	@ApiOperation(value = "Get all list of sitio by name of client", notes = "Returns a list of sitio by name of client.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<SitioDto>> findByClientName(@PathVariable("name") String clientName,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Sitio> sitios = sitioService.findByClientName(clientName);
		return ResponseEntity.ok(sitioMapper.toListSitioDto(sitios));
	}
	
	@GetMapping("/client/{id}")
	@ApiOperation(value = "Get all list of sitio by id of client", notes = "Returns a list of sitio by id of client.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<SitioDto>> findByClientId(@PathVariable("id") String idClient,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Sitio> sitios = sitioService.findByClientId(idClient);
		return ResponseEntity.ok(sitioMapper.toListSitioDto(sitios));
	}
}
