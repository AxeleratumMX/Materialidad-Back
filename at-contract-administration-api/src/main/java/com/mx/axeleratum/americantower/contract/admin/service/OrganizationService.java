package com.mx.axeleratum.americantower.contract.admin.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Organization;
import com.mx.axeleratum.americantower.contract.core.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrganizationService {
	@Autowired
	private OrganizationRepository organizationRepository;

	public Organization findUserByName(String name) {
		return organizationRepository.findByName(name);
	}
	
	public List<Organization> findByActivo(Boolean activo) {
		return organizationRepository.findByActivo(activo);
	}

	public Page<Organization> findAll(Pageable pageable) {
		return organizationRepository.findAll(pageable);
	}

	public List<Organization> findAll() {
		return organizationRepository.findAll();
	}

	public Organization save(Organization organization) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(organization)) {
			if (Objects.nonNull(organization.getId())) {
				return organizationRepository.save(organization);
			} else {
				organization.setActivo(true);
				return organizationRepository.insert(organization);
			}
		} else {
			throw new BussinessServiceException("No se puede insertar una organizacion nula de usuario");
		}
	}

	public Organization findById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<Organization> o = organizationRepository.findById(id);
		return o.isPresent() ? o.get() : null;
	}
}
