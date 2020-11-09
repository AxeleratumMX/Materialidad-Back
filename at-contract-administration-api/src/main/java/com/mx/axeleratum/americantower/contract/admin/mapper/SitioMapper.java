package com.mx.axeleratum.americantower.contract.admin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.admin.dto.SitioDto;
import com.mx.axeleratum.americantower.contract.core.model.Sitio;

@Mapper(componentModel = "spring")
public interface SitioMapper {

	Sitio toSitio(SitioDto sitioDto);

	SitioDto toSitioDto(Sitio sitio);

	List<SitioDto> toListSitioDto(List<Sitio> template);

	List<Sitio> toListSitio(List<SitioDto> templatesDto);

}
