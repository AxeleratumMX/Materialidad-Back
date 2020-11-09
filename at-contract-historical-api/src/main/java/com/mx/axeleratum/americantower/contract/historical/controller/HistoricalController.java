package com.mx.axeleratum.americantower.contract.historical.controller;

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

import com.mx.axeleratum.americantower.contract.historical.dto.HistoryDto;
import com.mx.axeleratum.americantower.contract.historical.dto.entry.HistoryDtoEntry;
import com.mx.axeleratum.americantower.contract.historical.mapper.HistoricalMapper;
import com.mx.axeleratum.americantower.contract.historical.model.History;
import com.mx.axeleratum.americantower.contract.historical.service.HistoricalService;

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
@Api(tags = "HistoricalDto Controller Methods", description = "HistoricalDto Controller")
@Slf4j
public class HistoricalController {

	@Autowired
	HistoricalService historicalService;

	@Autowired
	private HistoricalMapper historicalMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get a history given an id", notes = "Returns a history given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<HistoryDto> findHistory(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		History history = historicalService.findById(id);
		return ResponseEntity.ok(historicalMapper.toHistoryDto(history));
	}
	
	
	@GetMapping("/all")
	@ApiOperation(value = "Get all histories", notes = "Returns a list of histories.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<HistoryDto>> findNotification(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<History> notifications = historicalService.findAll();
		return ResponseEntity.ok(historicalMapper.toHistoryDtoList(notifications));
	}

	@GetMapping 
	@ApiOperation(value = "Get all histories", notes = "Returns a list of histories", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<HistoryDto>> findHistory(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<History> page = historicalService.findAll(pageable);
		return ResponseEntity.ok(page.map(historicalMapper::toHistoryDto));
	}

	@PostMapping
	@ApiOperation(value = "Add a new history", notes = "Returns the created history", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public HistoryDto addHistory(@RequestBody HistoryDtoEntry historyDtoEntry,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		History history = historicalService.addHistory(historicalMapper.toHistoryFromEntry(historyDtoEntry));
		return historicalMapper.toHistoryDto(history);
	}
	
	@PostMapping("/batch")
	@ApiOperation(value = "Add a list of new history", notes = "Returns the list of history", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Page<HistoryDto>> addAllNotification(
			@RequestBody List<HistoryDtoEntry> historyDtoEntry,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<History> page = historicalService.addHistories(historicalMapper.fromEntryListToHistoryList(historyDtoEntry));
		return ResponseEntity.ok(page.map(historicalMapper::toHistoryDto));
	}
	

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a history by id", notes = "Delete a history by id", produces = "application/json")
	public void deleteNotification(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		historicalService.deleteById(id);
	}
	
	@DeleteMapping
	@ApiOperation(value = "Delete all histories", notes = "Delete all histories", produces = "application/json")
	public void deleteNotification(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		historicalService.deleteAll();
	}
}
