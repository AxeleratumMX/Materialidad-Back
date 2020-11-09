package com.mx.axeleratum.americantower.contract.dynamicInterface.mapper;

import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.ContractValueDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.PartialContractTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TemplateHeaderDto;
import com.mx.axeleratum.americantower.contract.core.model.ContractTemplate;
import com.mx.axeleratum.americantower.contract.core.model.ContractValueTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;
import com.mx.axeleratum.americantower.contract.core.model.Template;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring" ,  uses = ClientIdToClientName.class)
public interface DynamicInterfaceMapper {


	DynamicInterfaceMapper INSTANCE = Mappers.getMapper(DynamicInterfaceMapper.class);

	Template toTemplate(TemplateDto templateDto);

	TemplateDto toTemplateDto(Template template);

	List<TemplateDto> toListTemplateDto(List<Template> template);

	List<Template> toTemplateList(List<TemplateDto> templatesDto);

	List<TemplateDto> toTemplateDtoList(List<Template> templates);

	@Mapping(source = "nameTemplate", target = "tipoContrato")
	Template toTemplateFromMaster(MasterTemplate template);

	@Mapping(source = "template.clientId", target = "clientId")
	@Mapping(source = "template.tipoContrato", target = "tipoContrato")
	PartialContractTemplateDto toPartialContractTemplateDto(ContractTemplate contractTemplate);

	List<ContractValueTemplate> toListContractValueTemplate(List<ContractValueDto> contractValues);
	List<ContractValueDto> toListContractValueDto(List<ContractValueTemplate> contractValues);


	@Mapping(source = "clientId", target = "client", qualifiedByName = "clientIdToClientName")
	TemplateHeaderDto toTemplateHeaderDto(Template template);

	List<TemplateHeaderDto> toTemplateHeaderDtos(List<Template> templates);

//	@Mapping(target = "tipoContrato", ignore = true)
//	@Mapping(target = "clientId", ignore = true)
//	@IterableMapping(qualifiedByName="mapWithoutValueContent")
//	TemplateDto toTemplateFromMasterIgnoreContent(MasterTemplate template);
//	
//	@Named("mapWithoutValueContent")
//    @Mapping(target = "content", ignore = true)
//	ContractValueDto mapWithouValueContent(ContractValueTemplate source);
}
