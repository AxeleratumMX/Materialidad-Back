package com.mx.axeleratum.americantower.contract.dynamicInterface.service;

import com.mx.axeleratum.americantower.contract.core.dto.*;
import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.model.*;
import com.mx.axeleratum.americantower.contract.core.repository.*;
import com.mx.axeleratum.americantower.contract.dynamicInterface.repository.MasterTemplateRepository;
import com.mx.axeleratum.americantower.contract.dynamicInterface.repository.TemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateContractService {
	
	@Autowired
	ContractTemplateRepository contractTemplateRepository;

	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	MasterTemplateRepository masterTemplateRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	DomainRepository domainRepository;

	@Autowired
	DomainValueRepository domainValueRepository;

	@Autowired
	KaleidoServiceFeign kaleidoServiceFeign;

	@Autowired
	CamundaBpmService camundaBpmService;

	@Autowired
	SitioRepository sitioRepository;


	public ContractTemplate findContractTemplateById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<ContractTemplate> o = contractTemplateRepository.findById(id);
		if (o.isPresent()) {
			return o.get();
		} else {
			throw new BussinessServiceException("ContractTemplate no encontrado");
		}
	}

	public ContractTemplate findPartialContractTemplateById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		Optional<ContractTemplate> o = contractTemplateRepository.findPartialContractTemplateById(id);
		if (o.isPresent()) {
			return o.get();
		} else {
			throw new BussinessServiceException("ContractTemplate no encontrado");
		}
	}


	public ContractTemplate saveContractTemplate(ContractTemplate contractTemplate) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		if (Objects.nonNull(contractTemplate)) {
			if (Objects.nonNull(contractTemplate.getId())) {
				Optional<ContractTemplate> oContractTemplate1 = contractTemplateRepository.findById(contractTemplate.getId());
				oContractTemplate1.ifPresent(
						c -> {
							contractTemplate.setFirmantes(c.getFirmantes());
							contractTemplate.setRevisores(c.getRevisores());
						}
				);
				return contractTemplateRepository.save(contractTemplate);
			} else {
				Optional<Client>  oClient = clientRepository.findById(contractTemplate.getTemplate().getClientId());
				oClient.ifPresent(client -> {
					contractTemplate.setRevisores( oClient.get().getContactos().stream().filter(contact -> contact.getTipoContacto()== ContactType.CONTACTO).collect(Collectors.toList()));
					contractTemplate.setFirmantes( oClient.get().getContactos().stream().filter(contact -> contact.getTipoContacto()== ContactType.FIRMANTE).collect(Collectors.toList()));
				});

				return contractTemplateRepository.insert(contractTemplate);
			}
		} else {
			throw new BussinessServiceException("NO se puede insertar una instancia nula de ContractTemplate");
		}
	}


	public void changeStatus(String id, ContractStatusType contractStatusType, String username, String bearerToken) {
		if (Objects.nonNull(id)) {
			Optional<ContractTemplate> o = contractTemplateRepository.findById(id);

			if (o.isPresent()) {
				ContractTemplate contractTemplate = o.get();
				contractTemplate.getTemplate().setEstado(contractStatusType.toString());
				contractTemplateRepository.save(o.get());
				if (contractStatusType == ContractStatusType.BORRADOR) {
				//todo que implementar
				}
				if (contractStatusType == ContractStatusType.REVISION) {

					callKaleido(id,ContractStatusType.BORRADOR.toString(),"Borrador","Inicio instancia blockchain",username,bearerToken);
					callKaleido(id,ContractStatusType.REVISION.toString(),"En Revision","En envia a revisión",username,bearerToken);

					//notifico a los contactos del cliente

				}
				if (contractStatusType == ContractStatusType.FIRMA) {
				}
				if (contractStatusType == ContractStatusType.ACTIVO) {
				}
				}
				if (contractStatusType == ContractStatusType.CANCELADO) {

				}

			}

		 else {
			throw new BussinessServiceException("ContractTemplate con id " + id + "no encontrado");
		}

		log.info("ContractTemplate con "+ id +" modificado , nuevo estado  " + ContractStatusType.REVISION.getContractType());


	}

	public List<ContractTemplate> addAllContractTemplate(List<ContractTemplate> contractTemplates) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		contractTemplates = contractTemplateRepository.saveAll(contractTemplates);
		return contractTemplates;
	}
	
	public List<ContractTemplate> findAllContractTemplate() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return contractTemplateRepository.findAll(Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
	}
	
	public Page<ContractTemplate> findAllContractTemplate(Pageable pageable) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		return contractTemplateRepository.findAll(pageable);
	}


	public void deleteContractTemplateById(String id) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		contractTemplateRepository.deleteById(id);
	}


	public void deleteAllContractTemplates() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		contractTemplateRepository.deleteAll();
	}


	public List<Contact> findFirmantesContractTemplate(String id) {
		Optional<ContractTemplate> oContractTemplate = contractTemplateRepository.findById(id);
		if (oContractTemplate.isPresent()) {
			ContractTemplate contractTemplate = oContractTemplate.get();
			Optional<Client> oClient = clientRepository.findById(contractTemplate.getTemplate().getClientId());
			List<Contact> contacts = new ArrayList<>();
			if (oClient.isPresent()) {
				contacts = oClient.get().getContactos().stream().filter(contact -> contact.getTipoContacto()== ContactType.FIRMANTE).collect(Collectors.toList());
			}
			return contacts;
		}
		else {
			throw new BussinessServiceException("NO se encontrro el contract con id " + id);
		}
	}

	//Camunda BPM

	public  String startBPMProcessInstance(ContractTemplate contractTemplate, String username) {
			CamundaProcessInstanceResponseDto camundaProcessInstanceResponseDto = camundaBpmService.startProcessInstance(buildCamundaProcessInstanceRequestDto(contractTemplate,username));
			log.info("process instance created");
			log.info(camundaProcessInstanceResponseDto.toString());
			return camundaProcessInstanceResponseDto.getId();

	}

	public void completeInitialTask(String processInstanceId , String username) {
		List<CamundaTaskDto> camundaTaskDtoList = camundaBpmService.getTasksByProcessInstanceId(processInstanceId);
		if (Objects.nonNull(camundaTaskDtoList) && camundaTaskDtoList.size() >0) {
			CamundaTaskDto camundaTaskDto = camundaTaskDtoList.get(0);
			if (camundaTaskDto.getAssignee().equals(username)) {
				camundaBpmService.completeTask(camundaTaskDtoList.get(0).getId(),new CamundaTaskRequestDto());
			}
		}
	}

	private CamundaProcessInstanceRequestDto buildCamundaProcessInstanceRequestDto(ContractTemplate contractTemplate, String username) {
		try {
			Domain domain = domainRepository.findByKey(Domains.CONTRATO);
			Optional<DomainValue> oTipoContratoDomainValue = domainValueRepository.findByDomainIdAndKey(domain.getId(), contractTemplate.getTemplate().getTipoContratoOracle());
			String tipoContrato = null;
			String subTipoContrato = null;
			if (oTipoContratoDomainValue.isPresent()) {
				DomainValue tipoContratoDomainValue = oTipoContratoDomainValue.get();
				tipoContrato = tipoContratoDomainValue.getValue();
				Domain subDomain = domainRepository.findByKey(Domains.SUB_CONTRATO);
				Optional<DomainValue> oSubTipoContratoDomainValue = domainValueRepository.findByDomainIdAndSubDomainValueAndKey(subDomain.getId(), contractTemplate.getTemplate().getTipoContratoOracle(), contractTemplate.getTemplate().getSubTipoContratoOracle());
				subTipoContrato = oSubTipoContratoDomainValue.isPresent() ? oSubTipoContratoDomainValue.get().getValue() : null;
			}

			Map<String, Object> values = new HashMap<String,Object>();
			values.put("asssignCreateContractUser" ,new CamundaVariableDto(username)); //todo meter el usuario logueado!

			values.put("contractId", new CamundaVariableDto(contractTemplate.getId()));
			String folio = "";
			try {
				folio = contractTemplate.listToMapContractValue().get("folOperations").getContent();
			} catch (Exception ex) {
				//nada para hacer folio no viene
			}
			values.put("folio", new CamundaVariableDto(folio));
			values.put("statusKey", new CamundaVariableDto(contractTemplate.getTemplate().getEstado()));
			values.put("status",new CamundaVariableDto(ContractStatusType.valueOf(contractTemplate.getTemplate().getEstado()).getContractType()));
			Optional<Client> oClient = clientRepository.findById(contractTemplate.getTemplate().getClientId());
			if (oClient.isPresent()) {
				values.put("cliente",new CamundaVariableDto(oClient.get().getNombre()));
			}
			values.put("tipoContrato",new CamundaVariableDto(tipoContrato));
			values.put("subTipoContrato",new CamundaVariableDto(subTipoContrato));
			values.put("assetNumber",new CamundaVariableDto(contractTemplate.getTemplate().getIdActivo()));


			CamundaProcessInstanceRequestDto camundaProcessInstanceRequestDto  = new CamundaProcessInstanceRequestDto();
			camundaProcessInstanceRequestDto.setVariables(values);
			camundaProcessInstanceRequestDto.setBusinessKey(contractTemplate.getId());
			camundaProcessInstanceRequestDto.setWithVariablesInReturn(false);
			return camundaProcessInstanceRequestDto;
		} catch (Exception ex) {
			log.error("Error en la llamada hacia el BPM ", ex);
			throw new BussinessServiceException("Error al llamar al bpm ",ex.getCause());

		}
	}

	private void callKaleido(String idContrato, String keyStatus, String status, String comment, String username ,String bearerToken) {
		log.info("call Kaleido con idContract " + idContrato + " keyStatus: " + keyStatus);
		//llamada Kaleido
		KaleidoRequestDto kaleidoRequestDto = new KaleidoRequestDto();
		kaleidoRequestDto.setComment(comment);
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateString = dtf.format(now);
		kaleidoRequestDto.setFecha(dateString);
		kaleidoRequestDto.setId(idContrato);
		kaleidoRequestDto.setKeyStatus(keyStatus);
		kaleidoRequestDto.setStatus(status);
		kaleidoRequestDto.setUser(username);

		try {
			KaleidoResponseDto kaleidoResponseDto = kaleidoServiceFeign.posData(bearerToken,kaleidoRequestDto);
			if (Objects.nonNull(kaleidoResponseDto) && Objects.nonNull(kaleidoResponseDto.getHeaders())) {
				if (Objects.nonNull(kaleidoResponseDto.getHeaders().getType()) && !kaleidoResponseDto.getHeaders().getType().equals("TransactionSuccess")) {
					throw new BussinessServiceException("NO se puedo insertar una instancia Kaleido");
				}
			}

		} catch (Exception ex) {
			log.error("Error en la llamada hacia Kaleido ", ex);
			throw new BussinessServiceException("Kaleido no disponible",ex.getCause());
		}
	}
	
	public List<ContractTemplate> findByTemplateTipoContrato(String tipoContrato) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<ContractTemplate> contracts = contractTemplateRepository.findByTemplateTipoContrato(tipoContrato);
		return contracts;
	}


}
