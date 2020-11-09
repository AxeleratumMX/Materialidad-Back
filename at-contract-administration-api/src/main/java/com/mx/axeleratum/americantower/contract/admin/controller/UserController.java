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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;
import com.mx.axeleratum.americantower.contract.admin.dto.UserDto;
import com.mx.axeleratum.americantower.contract.admin.dto.UserPassDto;
import com.mx.axeleratum.americantower.contract.admin.dto.UserPhotoDto;
import com.mx.axeleratum.americantower.contract.admin.mapper.UserMapper;
import com.mx.axeleratum.americantower.contract.admin.service.UserService;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.User;
import com.mx.axeleratum.americantower.contract.core.model.UserPhoto;
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
@RequestMapping("/users")
@Api(tags = "User Controller Methods", description = "User Controller Methods")
@Slf4j

public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	MessageSource messageSource;

	@GetMapping("/logged/user")
	@ApiOperation(value = "Get a user given the username", notes = "Returns a username given an username.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<UserDto> findUserByUserName(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (object instanceof String) {
			User user = RestPreconditions.checkFound(userService.findUserByUserName((String) object),
					"No se encontro el usuario con username " + object);
			return ResponseEntity.ok(userMapper.toUserDto(user));
		} else {
			throw new BussinessServiceException("Usuario no definido");
		}
	}

	@GetMapping()
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)", defaultValue = "50"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.", defaultValue = "5"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) ej=id,desc. "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@ApiOperation(value = "Get all list of users by page", notes = "Returns a list of users by page.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<Page<UserDto>> findAllByPage(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") @PageableDefault(page = 0, size = 50) Pageable pageable) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Page<User> page = userService.findAll(pageable);
		return ResponseEntity.ok(page.map(userMapper::toUserDto));
	}

	@GetMapping("/all")
	@ApiOperation(value = "Get all list of users", notes = "Returns a list of users.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<UserDto>> findAll(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<User> users = userService.findAll();
		return ResponseEntity.ok(userMapper.toListUserDto(users));
	}

	@PostMapping()
	@ApiOperation(value = "Add a new user", notes = "Returns Add a new user", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UserDto> add(@RequestBody UserDto userDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(userDto, "El user no puede ser nulo");
		User other = userService.findUserByUserName(userDto.getUsername());
		RestPreconditions.checkIsTrue((other == null), "El username ya existe");
		User user = userMapper.toUser(userDto);
		RestPreconditions.checkEntity(user);
		user = userService.save(user);
		return ResponseEntity.ok(userMapper.toUserDto(user));
	}

	@PutMapping
	@ApiOperation(value = "Save changes to user", notes = "Returns the  user with the saved changes", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<UserDto> save(@RequestBody UserDto userDto,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(userDto, "El usuario no puede ser nulo");
		Preconditions.checkNotNull(userDto.getId(), "id del usuario no puede ser nulo");
		User userExistente = userService.findById(userDto.getId());
		RestPreconditions.checkFound(userExistente, "No se encontro el usuario con id " + userDto.getId());
		User user = userMapper.toUser(userDto);
		/**
		 * Para que no no se reemplace el password
		 */
		user.setPassword(userExistente.getPassword());
		RestPreconditions.checkEntity(user);
		user = userService.save(user);
		return ResponseEntity.ok(userMapper.toUserDto(user));
	}

	@PatchMapping
	@ApiOperation(value = "Change user password", notes = "Returns ok change", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Boolean> changePassword(@RequestBody UserPassDto password,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		/**
		 * Cambio de password de la misma persona que se autentica, por eso no verifico
		 * el password viejo
		 */
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (object instanceof String) {
			User user = userService.findUserByUserName((String) object);
			userService.changePassword(user, password);
		} else {
			throw new BussinessServiceException("Usuario no definido");
		}

		return ResponseEntity.ok(true);
	}

	@PutMapping("/reset/{username}")
	@ApiOperation(value = "Reset user password", notes = "Reset user password", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Boolean> resetPassword(@PathVariable("username") String userName,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		/**
		 * FIXME: esto debe ser segurizado por rol sólo usuarios admin del sistema
		 * deberian resetear password de usuarios
		 */
		User user = userService.findUserByUserName(userName);
		userService.resetPassword(user);
		return ResponseEntity.ok(true);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a user by id", notes = "Delete a user by id", produces = "application/json")
	public void deleteTemplate(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Preconditions.checkNotNull(id, "id de usuario no puede ser nulo");
		RestPreconditions.checkFound(userService.findById(id), "No se encontro el usuario con id " + id);
		userService.deleteById(id);
	}

	@DeleteMapping()
	@ApiOperation(value = "Delete all users", notes = "Delete all users", produces = "application/json")
	public void deleteAllTemplates(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		userService.deleteAll();
	}

	@GetMapping("/{id}/photo")
	@ApiOperation(value = "Get a photo from user id", notes = "Returns a photo from user id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<byte[]> findPhoto(@PathVariable("id") String id,
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = userService.findById(id);
		RestPreconditions.checkFound(user, "No se encontro el usuario con id " + id);
		UserPhoto photo = userService.findPhoto(user.getId());
		return ResponseEntity.ok().contentType(MediaType.valueOf(photo.getType())).body(photo.getImagen().getData());
	}

	@PostMapping("/{id}/photo")
	@ApiOperation(value = "Save a photo from user id", notes = "Returns a photo id.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<UserDto> uploadPhoto(@PathVariable("id") String id,
			@RequestParam("photo") MultipartFile photo, @ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("File name: " + photo.getOriginalFilename());
		log.info("File size: " + photo.getSize());
		log.info("File context type:" + photo.getContentType());
		User user = userService.findById(id);
		RestPreconditions.checkFound(user, "No se encontro el usuario con id " + id);
		user = userService.savePhoto(id, photo);
		return ResponseEntity.ok(userMapper.toUserDto(user));
	}

	@GetMapping("/photos/all")
	@ApiOperation(value = "Get all list of users", notes = "Returns a list of users.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public ResponseEntity<List<UserPhotoDto>> findAllPhotos(
			@ApiIgnore("Ignored because swagger ui shows the wrong params, "
					+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<UserPhoto> photos = userService.findAllPhotos();
		return ResponseEntity.ok(userMapper.toListUserPhotoDto(photos));
	}

	@DeleteMapping("/photos/all")
	@ApiOperation(value = "Get all list of users", notes = "Returns a list of users.", produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 400, message = "Error 400") })
	public void deleteAllPhotos(@ApiIgnore("Ignored because swagger ui shows the wrong params, "
			+ "instead they are explained in the implicit params") Locale locale) {
		log.info(messageSource.getMessage("rest.title", null, locale) + request.getServletPath() + " "
				+ messageSource.getMessage("rest.method", null, locale)
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		userService.deleteAllPhotos();
	}

}
