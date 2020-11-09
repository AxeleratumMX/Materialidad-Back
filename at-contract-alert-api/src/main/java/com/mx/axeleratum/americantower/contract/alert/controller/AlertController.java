package com.mx.axeleratum.americantower.contract.alert.controller;

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

import com.mx.axeleratum.americantower.contract.alert.dto.AlertDto;
import com.mx.axeleratum.americantower.contract.alert.dto.AlertDtoEntry;
import com.mx.axeleratum.americantower.contract.alert.mapper.AlertMapper;
import com.mx.axeleratum.americantower.contract.alert.model.Alert;
import com.mx.axeleratum.americantower.contract.alert.service.AlertService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/")
@Api(tags = "AlertDto Controller Methods", description = "AlertDto Controller")
@Slf4j
public class AlertController {

	@Autowired
	AlertService alertService;

	@Autowired
	private AlertMapper alertMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a alert given an id", notes = "Returns an alert by id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<AlertDto> findAlert(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Alert alert = alertService.findById(id);
		return ResponseEntity.ok(alertMapper.toAlertDto(alert));
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "Get all alerts", notes = "Returns a list of alerts.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<AlertDto>> findAllAlert(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Alert> alerts = alertService.findAll();
		return ResponseEntity.ok(alertMapper.toAlertDtoList(alerts));
	}	

	@GetMapping
	@ApiOperation(value = "Get all alerts by page", notes = "Returns a page with a list of alerts.", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<AlertDto>> findAlert(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Alert> page = alertService.findAll(pageable);
		return ResponseEntity.ok(page.map(alertMapper::toAlertDto));
	}

	@PostMapping
	@ApiOperation(value = "Add a new alert", notes = "Returns the alert created", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public AlertDto addNotification(@RequestBody AlertDtoEntry alertDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Alert alert = alertService.addAlert(alertMapper.fromEntryToDto(alertDto));
		return alertMapper.toAlertDto(alert);
	}
	
	@PostMapping("/batch")
	@ApiOperation(value = "Add a list of new alerts", notes = "Returns the list of alerts created", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Page<AlertDto>> addAllNotification(
			@RequestBody List<AlertDtoEntry> notificationsDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Alert> page = alertService.addAllAlerts(alertMapper.fromEntryToAlertList(notificationsDto));
		return ResponseEntity.ok(page.map(alertMapper::toAlertDto));
	}
		
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a alert by id", notes = "Delete a alert by id", produces = "application/json")
	public void deleteNotification(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		alertService.deleteById(id);
	}
	
	@DeleteMapping
	@ApiOperation(value = "Delete all alers", notes = "Delete all alers", produces = "application/json")
	public void deleteNotification(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		alertService.deleteAll();
	}
}
