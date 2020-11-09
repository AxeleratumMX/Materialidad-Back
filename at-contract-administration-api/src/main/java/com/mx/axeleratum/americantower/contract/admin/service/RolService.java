package com.mx.axeleratum.americantower.contract.admin.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Rol;
import com.mx.axeleratum.americantower.contract.core.repository.RolRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RolService {

	@Autowired
	private RolRepository rolRepository;

	public Rol findByName(String name) {
		return rolRepository.findByName(name);
	}

	public Page<Rol> findAll(Pageable pageable) {
		return rolRepository.findAll(pageable);
	}

	public List<Rol> findAll() {
		return rolRepository.findAll();
	}

	public Rol save(Rol rol) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(rol)) {
			if (Objects.nonNull(rol.getId())) {
				return rolRepository.save(rol);
			} else {
				return rolRepository.insert(rol);
			}
		} else {
			throw new BussinessServiceException("No se puede insertar una instancia nula de rol");
		}
	}

	public Rol findById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<Rol> o = rolRepository.findById(id);
		return o.isPresent() ? o.get() : null;
	}

	public void deleteById(String id) {
		rolRepository.deleteById(id);
	}

	public void deleteAll() {
		rolRepository.deleteAll();
	}

}
