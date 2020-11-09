package com.mx.axeleratum.americantower.contract.dynamicInterface.mapper;


import com.mx.axeleratum.americantower.contract.core.model.Contact;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.*;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplateGroup;
import com.mx.axeleratum.americantower.contract.core.model.ContractValueTemplate;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" ,  uses = ClientIdToClientName.class)
public interface TemplateContractMapper {
	
	TemplateContractMapper INSTANCE = Mappers.getMapper( TemplateContractMapper.class );
 
	@Mapping(source="templateInstance" , target="template")
	ContractTemplate toContractTemplate(ContractTemplateDto contractTemplateDto);

	@InheritInverseConfiguration
	ContractTemplateDto toContratTemplateDto(ContractTemplate contractTemplate);

	List<ContractTemplateDto> toListContractTemplateDto(List<ContractTemplate> template);

	List<ContractTemplate>  toContractTemplateList(List<ContractTemplateDto> contractTemplateDto);

	List<ContractTemplateDto> toContractTemplateDtoList(List<ContractTemplate> templates);

	@Mapping(source="template.clientId" , target="clientId")
	@Mapping(source="template.tipoContrato" , target="tipoContrato")
	@Mapping(source="template.estado" , target="estado")
	PartialContractTemplateDto toPartialContractTemplateDto(ContractTemplate contractTemplate);

	@Mapping(source="user.name" , target="name")
	FirmanteDto toFirmanteDto(Contact contact);

	List<FirmanteDto> toFirmanteDtoList(List<Contact> contact);

	@Mapping(source = "template.clientId", target = "client", qualifiedByName = "clientIdToClientName")
	@Mapping(source="template.tipoContrato" , target="tipoContrato")
	@Mapping(source="template.name" , target="name")
	@Mapping(source="template.abstractType" , target="abstractType")
	@Mapping(source="template.leaseType" , target="leaseType")
	@Mapping(source="template.acuerdo" , target="acuerdo")
	@Mapping(source="template.estado" , target="estado")
	@Mapping(source="template.tipoContratoOracle" , target="tipoContratoOracle")
	@Mapping(source="template.subTipoContratoOracle" , target="subTipoContratoOracle")
	@Mapping(source="template.idActivo" , target="idActivo")
	@Mapping(source="template.version" , target="version")
	ContractTemplateHeaderDto toContractTemplateHeaderDto(ContractTemplate template);

	List<ContractTemplateHeaderDto> toContractTemplateHeaderDtos(List<ContractTemplate> templates);
	
	List<ContractValueDto> toListContractValueDto(List<ContractValueTemplate> values);

	ContractTemplateGroup toContractTemplateGroup(ContractTemplateGroupDto contractTemplateGroupDto);
	
	ContractTemplateGroupDto toContractTemplateGroupDto(ContractTemplateGroup contractTemplateGroup);
 }
