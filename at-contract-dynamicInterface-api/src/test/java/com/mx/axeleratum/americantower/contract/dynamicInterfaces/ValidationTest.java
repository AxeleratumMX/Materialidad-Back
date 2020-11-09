package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mx.axeleratum.americantower.contract.core.model.Client;
import com.mx.axeleratum.americantower.contract.core.model.Contact;
import com.mx.axeleratum.americantower.contract.core.model.ContactType;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.Template;
import com.mx.axeleratum.americantower.contract.core.repository.ClientRepository;
import com.mx.axeleratum.americantower.contract.core.repository.ContactRepository;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.DynamicInterfaceMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.TemplateContractMapper;
import com.mx.axeleratum.americantower.contract.dynamicInterface.repository.TemplateRepository;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.TemplateService;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.MasterTemplateService;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.TemplateContractService;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.ValidatorService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class ValidationTest {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	TemplateService templateService;

	@Autowired
	TemplateContractService contractService;

	@Autowired
	MasterTemplateService master;

	@Autowired
	ValidatorService validation;

	@Autowired
	DynamicInterfaceMapper Templatemapper;

	@Autowired
	TemplateContractMapper contractMapper;

	String CONTACT_TYPE_FIRMANTE = "Name " + ContactType.FIRMANTE.toString();
	String CONTAC_TYPE_CONTACT = "Name " + ContactType.CONTACTO.toString();

	protected Contact getContact(String name, ContactType contactType) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Contact> contacts = contactRepository.findByName(name);
		if (contacts.size() > 0) {
			log.info("El " + name + " contacto existe");
			return contacts.get(0);
		}
		log.info("El " + name + " fue creado");
		Contact contact = new Contact();
		contact.setName(name);
		contact.setTipoContacto(contactType);
		contactRepository.save(contact);
		return contact;
	}

	protected String getAnyIdClient() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Client> clients = clientRepository.findAll();
		if (clients.size() > 0) {
			log.info("El client contacto existe");
			return clients.get(0).getId();
		}
		Client client = new Client();
		client.setNombre("Lisi Client");
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(getContact(CONTACT_TYPE_FIRMANTE, ContactType.FIRMANTE));
		contacts.add(getContact(CONTAC_TYPE_CONTACT, ContactType.CONTACTO));
		client.setContactos(contacts);
		try {
			client = clientRepository.save(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client.getId();
	}

	protected Template getAnyTemplate(String type) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		//templateRepository.deleteAll();
		Template template = templateService.getTemplatByType(type);
		template.setClientId(getAnyIdClient());
		return template;
	}

	protected ContractTemplate getAnyTowerContract() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		//contractService.deleteAllContractTemplates();
//		List<ContractTemplate> listContract = contractService.findByTemplateTipoContrato("TOWER");
		ContractTemplate contract = null;
//		if (listContract.size() > 0) {
//			log.info("El contrato ya existe y no se crea");
//			contract = listContract.get(0);
//			assertThat(contract).isNotNull();
//		} else {
			log.info("El contrato no existe y se crea");
			Template template = getAnyTemplate("TOWER");
			template.setName("Lisi template");
			contract = new ContractTemplate();
			contract.setTemplate(template);
			contract = contractService.saveContractTemplate(contract);
//		}
		assertThat(contract).isNotNull();
		return contract;
	}
	
	protected void check(String id, String value, ContractValueDto contractValue) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("[" + contractValue.getId() + "]: value:(" + contractValue.getContent() +"),("+value+")");
		log.info("("+ contractValue.getId() + ") errores anteriores: " + contractValue.getOperationErrors());
		assertThat(contractValue).isNotNull();
		assertThat(contractValue.getId()).isNotNull();
		assertThat(contractValue.getId()).isEqualTo(id);
		assertThat(contractValue.getContent()).isEqualTo(value);
		log.info("---" + contractValue.getId() + "-- OK -----");
	}
	
	protected void checkList(String list, String valueAsignado, ContractValueDto listValue) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("[" + listValue.getId() + "]: option:(" + listValue.getContent() +"),("+valueAsignado+")");
		assertThat(listValue).isNotNull();
		assertThat(listValue.getId()).isNotNull();
		assertThat(listValue.getId()).isEqualTo(list);
		assertThat(listValue.getContent()).isIn(valueAsignado);	
   }
	
	protected void checkEditable(String id, Boolean edit, ContractValueDto contractValue) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("[" + contractValue.getId() + "]: editable:(" + contractValue.getEditable() +"),("+edit+")");
		assertThat(contractValue).isNotNull();
		assertThat(contractValue.getId()).isNotNull();
		assertThat(contractValue.getId()).isEqualTo(id);
		assertThat(contractValue.getEditable()).isEqualTo(edit);
	}
	
	protected void infoResult(int numErrord,String id,String value, ContractValueDto contractValue) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("(" + contractValue.getId() + "): value:(" + contractValue.getContent() +"),("+value+")");
		log.info("("+ contractValue.getId() + ") errors: " + contractValue.getOperationErrors());
		assertThat(contractValue).isNotNull();
		assertThat(contractValue.getId()).isNotNull();
		assertThat(contractValue.getId()).isEqualTo(id);
		assertThat(contractValue.getContent()).isEqualTo(value);
		assertThat(contractValue.getOperationErrors()).isNotNull();
		assertThat(contractValue.getOperationErrors().size()).isEqualTo(numErrord);
	}

	
	@Test
	public void okTest() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		assertThat("Ok").isEqualTo("Ok");
	}
}
