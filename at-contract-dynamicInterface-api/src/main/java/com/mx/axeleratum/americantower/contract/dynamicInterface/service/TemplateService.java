package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.Template;
import com.mx.axeleratum.americantower.contract.core.repository.ContractTemplateRepository;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.DynamicInterfaceMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.repository.TemplateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateService {
	
	@Autowired
	ContractTemplateRepository contractTemplateRepository;

	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	MasterTemplateService masterTemplateService;

	private static String estadoInicial = "BORRADOR";
	
	public Template findTemplateById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<Template> o = templateRepository.findById(id);
		return o.isPresent()?o.get():null;
	}


	public List<Template> findTemplateByClientId(String idClient) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Template> templates = templateRepository.findByClientId(idClient);
		return templates;
	}

	public Page<Template> findAllPageablesTemplates(Pageable pageable) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return templateRepository.findAll(pageable);
	}
	

	public void deleteTemplateById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		templateRepository.deleteById(id);
	}

	public void deleteAlldTemplates() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		templateRepository.deleteAll();
	}
	
	public List<Template> findAllTemplates() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return templateRepository.findAll(Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
	}

	public Template getTemplatByType(String tipo) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		MasterTemplate masterTemplate = masterTemplateService.getInstance(tipo); 
		Template template = DynamicInterfaceMapper.INSTANCE.toTemplateFromMaster(masterTemplate);
		template.setEstado(estadoInicial);		
		log.info("Template basado en un tipo: "+tipo);
		log.info(template+"");		
		return template;
	}
	
	public Template saveTemplate(Template template) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(template)) {
			if (Objects.nonNull(template.getIdTemplate())) {
				//Si el template que se quiere modificar no existe, no se puede modificar
				Optional<Template> oTemplate = templateRepository.findById(template.getIdTemplate());
				if(!oTemplate.isPresent()) {
					throw new BussinessServiceException("El template no existe, no se pudo modificar");
				}
				//Si en la modificación se modifica el nombre a el nombre de otra template existente
				if(!oTemplate.get().getName().equals(template.getName())) {
					oTemplate = templateRepository.findByName(template.getName());
					if(oTemplate.isPresent()) {
						throw new BussinessServiceException("El nombre del template ya existe, no se pudo modificar la template");
					}
				}
				return templateRepository.save(template);
			} else {
				Optional<Template> oTemplate = templateRepository.findByName(template.getName());
				if(oTemplate.isPresent()) {
					throw new BussinessServiceException("El nombre del template ya existe, no se pudo ingresar la template");
				}
				return templateRepository.insert(template);
			}
		} else {
			throw new BussinessServiceException("No se puede insertar una instancia nula de Template");
		}
	}

	public List<Template> findATemplateByNombreLike(String search) {
		return templateRepository.findAllByNombreLike(search);
	}

}
