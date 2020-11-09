package com.mx.axeleratum.americantower.contract.dynamicInterface.controller;

import com.google.common.base.Preconditions;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Client;
import com.mx.axeleratum.americantower.contract.core.model.Contact;
import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import com.mx.axeleratum.americantower.contract.core.util.RestPreconditions;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractTemplateHeaderDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.FirmanteDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.PartialContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.TemplateContractMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.MasterTemplateService;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.TemplateContractService;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.ValidatorService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.mx.axeleratum.americantower.contract.dynamicInterface.security.SecurityConstants.HEADER_STRING;

@RestController
@RequestMapping("/templates/contract")
@Api(tags = "Dinamic Contract Interface Controller Methods", description = "Dinamic Contract Interface Controller")
@Slf4j
public class TemplateContractController {

	@Autowired
	TemplateContractService templateContractService;

	@Autowired
	private TemplateContractMapper templateContractMapper;
	
	@Autowired
	MasterTemplateService masterTemplateService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ValidatorService validatorService;

	@Autowired
	ClientRepository clientRepository;

	@PostMapping()
	@ApiOperation(value = "Add a new contract template", notes = "Returns Add a new contract template", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ContractTemplateDto> addTemplateContract(@RequestBody ContractTemplateDto contractTemplateDto,
																   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
														   + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		//cargar el sitio en el contrato
		Integer idActivo = contractTemplateDto.getTemplateInstance().getIdActivo();
		if (Objects.nonNull(idActivo) && Objects.nonNull(contractTemplateDto.listToMapContractValue().get("idActivo"))) {
			contractTemplateDto.listToMapContractValue().get("idActivo").setContent(idActivo.toString());
		}

		Optional<Client> oClient = clientRepository.findById(contractTemplateDto.getTemplateInstance().getClientId());
		if (oClient.isPresent() && Objects.nonNull(contractTemplateDto.listToMapContractValue().get("idCliente"))) {
			contractTemplateDto.listToMapContractValue().get("idCliente").setContent(oClient.get().getIdCliente());
		}


		contractTemplateDto = validatorService.processOperationOnLoadAllContract(contractTemplateDto);
		ContractTemplate contractTemplate = templateContractService.saveContractTemplate(templateContractMapper.toContractTemplate(contractTemplateDto));
		return ResponseEntity.ok(templateContractMapper.toContratTemplateDto(contractTemplate));
	}


	@PatchMapping("/{id}/status/{status}")
	@ApiOperation(value = "Change Status of Contract", notes = "Change Status of Contract", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void changeStatus(
			@PathVariable("id") String id,
			@PathVariable("status") ContractStatusType status,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
																			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String bearerToken = request.getHeader(HEADER_STRING);

		if (object instanceof String) {
			String username = (String)object;
			templateContractService.changeStatus(id,status,username,bearerToken);

		//creo la instancia del proceso y a continuacion completo la primera tarea
		ContractTemplate contractTemplate = templateContractService.findContractTemplateById(id);
		if (Objects.isNull(contractTemplate.getProcessInstanceId())) {
			contractTemplate.setProcessInstanceId(templateContractService.startBPMProcessInstance(contractTemplate,username));
			templateContractService.saveContractTemplate(contractTemplate);
		}
		templateContractService.completeInitialTask(contractTemplate.getProcessInstanceId(),username);

		} else {
			throw new BussinessServiceException("Usuario no definido");
		}

	}

	@PutMapping
	@ApiOperation(value = "Save changes to the contract template instance", notes = "Returns the  contract template with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ContractTemplateDto> saveTemplateContract(
			@RequestBody ContractTemplateDto contractTemplateDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(contractTemplateDto, "El contract template no puede ser nulo");
		Preconditions.checkNotNull(contractTemplateDto.getId(), "id del contract template no puede ser nulo");
		RestPreconditions.checkFound(templateContractService.findContractTemplateById(contractTemplateDto.getId()),
				"No se encontro el contract template con id: " + contractTemplateDto.getId()
						+ ". El template no puede ser modificado");
		ContractTemplate contractTemplate = templateContractService
				.saveContractTemplate(templateContractMapper.toContractTemplate(contractTemplateDto));
		return ResponseEntity.ok(templateContractMapper.toContratTemplateDto(contractTemplate));
	}


	@PostMapping("/batch")
	@ApiOperation(value = "Add a list of new contract template instances", notes = "Returns a new listcontract template instances", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<List<ContractTemplateDto>> addAllContractTemplate(@RequestBody List<ContractTemplateDto> contractTemplateDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractTemplate> templates = templateContractService.addAllContractTemplate(templateContractMapper.toContractTemplateList(contractTemplateDto));
		return ResponseEntity.ok(templateContractMapper.toContractTemplateDtoList(templates));
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a contract template instance given an id", notes = "Returns a contract template instance given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<ContractTemplateDto> findContractTemplateInstance(@PathVariable("id") String id,
																			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
																					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTemplate contractTemplate = templateContractService.findContractTemplateById(id);
		return ResponseEntity.ok(templateContractMapper.toContratTemplateDto(contractTemplate));
	}
	
	@GetMapping()
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
		@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
		@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
				+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "Get all list of contract template instances by page", notes = "Returns a list of contract template instances by page.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<ContractTemplateHeaderDto>> findAllContractTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale,@ApiIgnore("Ignored because swagger ui shows the wrong params, "
							+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<ContractTemplate> page = templateContractService.findAllContractTemplate(pageable);
	 return ResponseEntity.ok(page.map(templateContractMapper::toContractTemplateHeaderDto));
	}
	
	@GetMapping("/all")
	@ApiOperation(value = "Get all list of contract template instances", notes = "Returns a list of contract template instances.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<ContractTemplateHeaderDto>> findAllContractTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractTemplate> contracTemplates = templateContractService.findAllContractTemplate();
		return ResponseEntity.ok(templateContractMapper.toContractTemplateHeaderDtos(contracTemplates));
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a contract template instance given an id", notes = "Delete a contract template instance given an id", produces = "application/json")
	public void deleteContractTemplate(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swaggerui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(id,"id de template no puede ser nulo");
		RestPreconditions.checkFound(templateContractService.findContractTemplateById(id),"No se encontro el template con id "+ id );
		templateContractService.deleteContractTemplateById(id);
	}
	
	@DeleteMapping()
	@ApiOperation(value = "Delete all contract template intances", notes = "Delete all dynamic contract interface template by id", produces = "application/json")
	public void deleteAllContractTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		templateContractService.deleteAllContractTemplates();
	}

	@GetMapping("/partial/{id}")
	@ApiOperation(value = "Get a partial contractTemplate given the contractTemplate id", notes = "Returns a partial contracTemplate given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<PartialContractTemplateDto> findPartialDynamicContractInterfaceTemplate(@PathVariable("id") String id,
																								  @ApiIgnore("Ignored because swagger ui shows the wrong params, "
																					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ContractTemplate contractTemplate = RestPreconditions.checkFound(templateContractService.findPartialContractTemplateById(id), "No se encontro contractTemplate con id "+id);
		return ResponseEntity.ok(templateContractMapper.toPartialContractTemplateDto(contractTemplate));
	}

	@GetMapping("/{id}/firmantes")
	@ApiOperation(value = "Get a partial contractTemplate given the contractTemplate id", notes = "Returns a partial contracTemplate given an id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<FirmanteDto>> findFirmantesTemplateContract(@PathVariable("id") String id,
																		   @ApiIgnore("Ignored because swagger ui shows the wrong params, "
																										  + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Contact> contacts = templateContractService.findFirmantesContractTemplate(id);
		return ResponseEntity.ok(templateContractMapper.toFirmanteDtoList(contacts));
	}
	


}
