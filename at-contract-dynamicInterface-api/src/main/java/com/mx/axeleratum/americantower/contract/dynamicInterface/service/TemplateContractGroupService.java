package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Client;
import com.mx.axeleratum.americantower.contract.core.model.ContactType;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplateGroup;
import com.mx.axeleratum.americantower.contract.core.repository.ContractTemplateGroupRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateContractGroupService {

	@Autowired
	ContractTemplateGroupRepository contractTemplateGroupRepository;

	@Autowired
	MessageSource messageSource;

	public ContractTemplateGroup save(ContractTemplateGroup contractTemplateGroup) {		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(contractTemplateGroup)) {
			if (Objects.nonNull(contractTemplateGroup.getId())) {
				return contractTemplateGroupRepository.save(contractTemplateGroup);
			} else {
				return contractTemplateGroupRepository.insert(contractTemplateGroup);
			}
		} else {
			throw new BussinessServiceException("No se puede insertar una instancia nula de ContractTemplateGroup");
		}		
	}

	public ContractTemplateGroup findById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<ContractTemplateGroup> o = contractTemplateGroupRepository.findById(id);
		if (o.isPresent()) {
			return o.get();
		} else {
			throw new BussinessServiceException("Grupo no encontrado");
		}
	}

	public Page<ContractTemplateGroup> findAll(Pageable pageable) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return contractTemplateGroupRepository.findAll(pageable);
	}

	public void deleteById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		contractTemplateGroupRepository.deleteById(id);
	}

	public void deleteAll() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		contractTemplateGroupRepository.deleteAll();
	}

	public ContractTemplateGroup findByName(String name) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<ContractTemplateGroup> o = contractTemplateGroupRepository.findByName(name);
		if (o.isPresent()) {
			return o.get();
		} else {
			throw new BussinessServiceException("Grupo no encontrado");
		}
	}
}
