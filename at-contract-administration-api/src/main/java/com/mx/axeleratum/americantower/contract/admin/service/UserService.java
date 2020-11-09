package com.mx.axeleratum.americantower.contract.admin.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mx.axeleratum.americantower.contract.admin.dto.UserPassDto;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.User;
import com.mx.axeleratum.americantower.contract.core.model.UserPhoto;
import com.mx.axeleratum.americantower.contract.core.repository.PhotoRepository;
import com.mx.axeleratum.americantower.contract.core.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	private final String PASSWORD_DEFAULT = "welcome1";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhotoRepository photoRepository;

//	@Autowired
//	private EmailService emailService;

	public User findUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User save(User user) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(user)) {
			if (Objects.nonNull(user.getId())) {
				return userRepository.save(user);
			} else {
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				user.setPassword(bCryptPasswordEncoder.encode(PASSWORD_DEFAULT));
//				try {
//					if (user.getCorreo() != null) {
//						emailService.sendSimpleMessage(user.getCorreo(), "Usuario creado", 
//								"Se le ha asignado un usuario de sistema." + 
//								" Usuario:" + user.getUsername() + " Password: " + PASSWORD_DEFAULT);
//					}
//				}catch (Exception e) {
//					// FIXME: da error el envío de mail corregir
//				}
				return userRepository.insert(user);
			}
		} else {
			throw new BussinessServiceException("No se puede insertar una instancia nula de usuario");
		}
	}

	public User savePhoto(String idUser, MultipartFile file) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = findById(idUser);
		if (user == null)
			throw new BussinessServiceException("El usuario no existe");
		Optional<UserPhoto> findPhoto = (user.getIdPhoto() == null) ? null
				: photoRepository.findById(user.getIdPhoto());
		UserPhoto photo = null;
		try {
			if (findPhoto!=null && findPhoto.isPresent()) {
				/**
				 * Cambiar la foto
				 */
				photo = findPhoto.get();
				photo.setImagen(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				photo = photoRepository.save(photo);
			} else {
				/**
				 * Agregar la foto
				 */
				photo = new UserPhoto();
				photo.setFileName(file.getOriginalFilename());
				photo.setSize(file.getSize());
				photo.setType(file.getContentType());
				photo.setImagen(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				photo = photoRepository.insert(photo);
			}
			user.setIdPhoto(photo.getId());
			user = userRepository.save(user);
		} catch (IOException e) {
			throw new BussinessServiceException("Error cargando la imagen");
		}
		return user;
	}

	public UserPhoto findPhoto(String idUser) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = findById(idUser);
		if (user == null)
			throw new BussinessServiceException("El usuario no existe");
		Optional<UserPhoto> findPhoto = (user.getIdPhoto() == null) ? null
				: photoRepository.findById(user.getIdPhoto());
		UserPhoto photo = (findPhoto.isPresent()) ? findPhoto.get() : null;
		return photo;
	}

	public User findById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<User> o = userRepository.findById(id);
		return o.isPresent() ? o.get() : null;
	}

	public void deleteById(String id) {
		userRepository.deleteById(id);
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}

	public void changePassword(User user, UserPassDto password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(password.getNewPassword()));
//		try {
//			if (user.getCorreo() != null) {
//				emailService.sendSimpleMessage(user.getCorreo(),"Cambio de Password","Ha cambiado el password, de manera satisfactoria.");
//			}
//		}catch (Exception e) {
//			// FIXME: da error el envío de mail corregir
//		}
		this.save(user);
	}

	public void resetPassword(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(PASSWORD_DEFAULT));
		this.save(user);
	}

	public List<UserPhoto> findAllPhotos() {
		return photoRepository.findAll();
	}

	public void deleteAllPhotos() {
		photoRepository.deleteAll();
	}

}
