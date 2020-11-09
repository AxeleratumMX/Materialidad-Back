package com.mx.axeleratum.americantower.contract.notification.controller;

import com.mx.axeleratum.americantower.contract.core.model.Notification;
import com.mx.axeleratum.americantower.contract.notification.dto.NotificationDto;
import com.mx.axeleratum.americantower.contract.notification.mapper.NotificationMapper;
import com.mx.axeleratum.americantower.contract.notification.service.KaleidoService;
import com.mx.axeleratum.americantower.contract.notification.service.NotificationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/notifications")
@Api(tags = "NotificationDto Controller Methods", description = "NotificationDto Controller")
@Slf4j
public class NotificationController {

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


	@GetMapping("/{id}")
	@ApiOperation(value = "Get a notification given an id", notes = "Returns a notification by id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<NotificationDto> findNotification(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Notification notification = notificationService.findById(id);
		return ResponseEntity.ok(notificationMapper.toNotificationDto(notification));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all notifications", notes = "Returns a list of notifications.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<NotificationDto>> findNotification(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Notification> notifications = notificationService.findAll();
		return ResponseEntity.ok(notificationMapper.toNotificationDtoList(notifications));
	}


	@GetMapping("/pageable")
	@ApiOperation(value = "Get all notifications by page", notes = "Returns a list of notifications by page.", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<NotificationDto>> findNotification(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<Notification> page = notificationService.findAll(pageable);
		return ResponseEntity.ok(page.map(notificationMapper::toNotificationDto));
	}

	@GetMapping()
	@ApiOperation(value = "Get all notifications assaigned to user", notes = "Get all notifications assaigned to user.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<NotificationDto>> findNotificationReceivedByUserName(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Notification> notifications = new ArrayList<>();
		if (object instanceof String) {
			notifications = notificationService.findByReceiverUserName((String)object);
		}
		return ResponseEntity.ok(notificationMapper.toNotificationDtoList(notifications));
	}



	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a notificatio by id", notes = "Delete a notification by id", produces = "application/json")
	public void deleteNotification(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		notificationService.deleteById(id);
	}



}
