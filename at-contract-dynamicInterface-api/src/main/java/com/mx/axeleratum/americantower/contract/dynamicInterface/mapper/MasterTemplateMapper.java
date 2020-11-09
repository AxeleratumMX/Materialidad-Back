package com.mx.axeleratum.americantower.contract.dynamicInterface.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.MasterTemplateDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;


@Mapper(componentModel = "spring")
public interface MasterTemplateMapper {
	MasterTemplate toMaterTemplate(MasterTemplateDto templateDto);

	MasterTemplateDto toMasterTemplateDto(MasterTemplate template);

	List<MasterTemplateDto> toListMasterTemplateDto(List<MasterTemplate> template);

	List<MasterTemplate> toListMasterTemplate(List<MasterTemplateDto> templatesDto);
}
