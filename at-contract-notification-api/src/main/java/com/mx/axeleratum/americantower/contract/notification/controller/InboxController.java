package com.mx.axeleratum.americantower.contract.notification.controller;

import com.mx.axeleratum.americantower.contract.core.dto.KaleidoRequestDto;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.ContractStatusType;
import com.mx.axeleratum.americantower.contract.core.model.ResponseType;
import com.mx.axeleratum.americantower.contract.notification.dto.CommentDto;
import com.mx.axeleratum.americantower.contract.notification.dto.CompleteTaskDto;
import com.mx.axeleratum.americantower.contract.notification.dto.ResponseTaskDto;
import com.mx.axeleratum.americantower.contract.notification.dto.TaskDto;
import com.mx.axeleratum.americantower.contract.notification.mapper.NotificationMapper;
import com.mx.axeleratum.americantower.contract.notification.service.InboxService;
import com.mx.axeleratum.americantower.contract.notification.service.KaleidoService;
import com.mx.axeleratum.americantower.contract.notification.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/tasks")
@Api(tags = "NotificationDto Controller Methods", description = "NotificationDto Controller")
@Slf4j
public class InboxController {

	@Autowired
	NotificationService notificationService;

	@Autowired
	private NotificationMapper notificationMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@Autowired
	KaleidoService kaleidoService;

	@Autowired
	InboxService inboxService;



	@GetMapping()
	@ApiOperation(value = "Get all tasks by username", notes = "Returns a list of tasks by username.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<TaskDto>> findTasksByUserName(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (object instanceof String) {
			return ResponseEntity.ok(inboxService.findTasksByUserName((String)object));
		} else {
			throw new BussinessServiceException("Usuario no definido");
		}
	}

	@PostMapping("/response/task")
	@ApiOperation(value = "Complete task with comments", notes = "Complete task with comments", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void completeTask(@RequestBody ResponseTaskDto responseTaskDto,
		 @ApiIgnore("Ignored because swagger ui shows the wrong params, "
				 + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		inboxService.responseTask(responseTaskDto);

		try {
			String keyStatus = "";
			String status = "";
			if (responseTaskDto.getContractStatusKey().equals(ContractStatusType.REVISION.toString())) {
				keyStatus= "REVISION_OK";
				status = "Aprobado";
			}
			if (responseTaskDto.getContractStatusKey().equals(ContractStatusType.FIRMA.toString())) {
				keyStatus= "APROBADO";
				status = "Aprobado";
			}
			//agregar manejo de errores respuesta de kaleido
			boolean paramValue = (responseTaskDto.getResponse() == ResponseType.ACCEPT || responseTaskDto.getResponse() == ResponseType.APPROVE);
			if (!paramValue) {
				keyStatus= "RECHAZADO";
				status = "Rechazado";
			}

			kaleidoService.postData(build(responseTaskDto.getResponseUser(),responseTaskDto.getContractId(),keyStatus,status ,responseTaskDto.getComments()));

		}catch (Exception ex) {
			throw new BussinessServiceException("contractStatusKey no puede ser nulo");
		}



	}

	@PostMapping("/complete/task")
	@ApiOperation(value = "Complete task", notes = "Complete task ", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void completeTask(@RequestBody CompleteTaskDto completeTaskDto,
							 @ApiIgnore("Ignored because swagger ui shows the wrong params, "
									 + "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		inboxService.completeTask(completeTaskDto);

		try {
			String keyStatus = "";
			String status = "";
			if (completeTaskDto.getContractStatusKey().equals(ContractStatusType.BORRADOR.toString())) {
				keyStatus= "REVISION";
				status = "En Revisión";
			}
			if (completeTaskDto.getContractStatusKey().equals(ContractStatusType.REVISION.toString())) {
				keyStatus= "FIRMA";
				status = ContractStatusType.FIRMA.getContractType();
			}
			if (completeTaskDto.getContractStatusKey().equals(ContractStatusType.FIRMA.toString())) {
				keyStatus= "ACTIVADO";
				status = ContractStatusType.ACTIVO.getContractType();
			}
			//agregar manejo de errores respuesta de kaleido
			kaleidoService.postData(build(completeTaskDto.getResponseUser(),completeTaskDto.getContractId(),keyStatus,status ,null));

		}catch (Exception ex) {
			throw new BussinessServiceException("contractStatusKey no puede ser nulo");
		}
	}

	@GetMapping("/comments/{processInstanceId}")
	@ApiOperation(value = "Get all comments by processInstanceId", notes = "Returns a list of comments.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public List<CommentDto> findComments(@PathVariable("processInstanceId")String processInstanceId) {
		return inboxService.findComments(processInstanceId);
	}

	private KaleidoRequestDto build(String username, String idContrato, String keyStatus,String status, String comment) {
		KaleidoRequestDto kaleidoRequestDto = new KaleidoRequestDto();
		kaleidoRequestDto.setKeyStatus(keyStatus);
		kaleidoRequestDto.setStatus(status);
		if (Objects.isNull(comment)) {
			kaleidoRequestDto.setComment("Se envía contrato a " + kaleidoRequestDto.getStatus());
		} else {
			kaleidoRequestDto.setComment(comment);
		}
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateString = dtf.format(now);
		kaleidoRequestDto.setFecha(dateString);
		kaleidoRequestDto.setId(idContrato);
		kaleidoRequestDto.setUser(username);
		return kaleidoRequestDto;


	}


}
