package com.mx.axeleratum.americantower.contract.dynamicInterface.controller;

import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.ValidatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/contract/validations")
@Api(tags = "Rules", description = "Rules Controller")
@Slf4j
public class ValidatorController {

	@Autowired
	ValidatorService validatorService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;
	
	@GetMapping("/rules")
	@ApiOperation(value = "Prueba que el servicio esta activo", notes = "Prueba que el servicio esta activo", produces = "application/json")
	public ResponseEntity<String> getMessage() throws IOException {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return ResponseEntity.ok("Service Ok");
	}

	@PostMapping("/upload/file")
	public ResponseEntity<List<String[]>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("File name: " + file.getOriginalFilename());
		log.info("File size: " + file.getSize());
		log.info("File context type:" + file.getContentType());
		return ResponseEntity.ok(validatorService.processFile(file));
	}

	@PatchMapping("/rules")
	@ApiOperation(value = "Validacion y ejecucion de calculos de negocio", notes = "Validacion y ejecucion de calculos de negocio", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<ContractValueDto>> apllyContractValuesRules(@RequestBody ContractTemplateDto contract,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractValueDto> contractValuesDto = validatorService.processOperation(contract);
		return ResponseEntity.ok(contractValuesDto);
	}
	
	@PatchMapping("/rules/all")
	@ApiOperation(value = "Validacion y ejecucion de calculos de negocio", notes = "Validacion y ejecucion de calculos de negocio", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ContractTemplateDto> apllyContractValuesRulesAll(@RequestBody ContractTemplateDto contract,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		contract = validatorService.processOperationReturnAllContract(contract);
		return ResponseEntity.ok(contract);
	}

	@PatchMapping("/rules/onload")
	@ApiOperation(value = "Validacion y ejecucion de calculos de negocio", notes = "Validacion y ejecucion de calculos de negocio", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ContractTemplateDto> apllyContractValuesOnloadAll(@RequestBody ContractTemplateDto contract,
																		   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
																				   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		contract = validatorService.processOperationOnLoadAllContract(contract);
		return ResponseEntity.ok(contract);
	}

}
