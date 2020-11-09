package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.repository.MasterTemplateRepository;
import com.querydsl.core.types.Predicate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MasterTemplateService {

	@Autowired
	MasterTemplateRepository masterTemplateRepository;

	public MasterTemplate addMasterTemplate(MasterTemplate template) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return masterTemplateRepository.save(template);
	}

	public MasterTemplate findByType(String tipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return masterTemplateRepository.findByTipo(tipo);
	}

	public MasterTemplate getInstance(String tipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		// FIXME: Cuando se tenga en json final, de torre  o se tenga la parte administracion del master
		// Debo sacar la info de la base
        //MasterTemplate template = findByType(tipo);
		MasterTemplate template = null;
		if (template == null) {
			try {
				template = getJsonFromFile(tipo + ".json");
				//template = masterTemplateRepository.save(template);
			} catch (IOException e) {
				log.error("Error leyendo json master template: " + tipo, e);
				e.printStackTrace();
			}
		}
		log.info("Master Instance");
		log.info(template + "");
		return template;
	}

	public MasterTemplate getJsonFromFile(String name) throws IOException {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		InputStream inputStream = this.getClass().getResourceAsStream("/" + name);
		ObjectMapper mapper = new ObjectMapper();
		MasterTemplate template = mapper.readValue(inputStream, MasterTemplate.class);
		log.info("MasterTemplate from json");
		log.info(template + "");
		return template;
	}

	public List<MasterTemplate> findAllMasterTemplate() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return masterTemplateRepository.findAll();
	}

	public MasterTemplate findMasterTemplateByType(String tipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return masterTemplateRepository.findByTipo(tipo);
	}

	public MasterTemplate saveMasterTemplate(MasterTemplate template) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(template) && template.getId() != null && template.getId().isEmpty() == false) {
			return masterTemplateRepository.save(template);
		} else {
			throw new BussinessServiceException("NO se puede insertar una instancia nula de MasterTemplate");
		}
	}

	public Page<MasterTemplate> findAllPageablesTemplates(Pageable pageable, Predicate predicate) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (predicate == null) {
			log.info("El predicado es null");
			return masterTemplateRepository.findAll(pageable);
		} else {
			log.info("El predicado no es null");
			return masterTemplateRepository.findAll(predicate, pageable);
		}	
	}

	public List<MasterTemplate> findAllMasterTemplate(Predicate predicate) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return (List<MasterTemplate>) masterTemplateRepository.findAll(predicate);
	}

	public MasterTemplate findById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<MasterTemplate> o = masterTemplateRepository.findById(id);
		return o.isPresent() ? o.get() : null;
	}

	public MasterTemplate saveTemplate(MasterTemplate masterTemplate) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return masterTemplateRepository.save(masterTemplate);
	}

	public MasterTemplate insertTemplate(MasterTemplate masterTemplate) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return masterTemplateRepository.insert(masterTemplate);
	}

	public void deleteTemplateById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		masterTemplateRepository.deleteById(id);
	}

	public void deleteAllTemplates() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		masterTemplateRepository.deleteAll();		
	}

}
