package com.mx.axeleratum.americantower.contract.admin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.admin.dto.PassthruDto;
import com.mx.axeleratum.americantower.contract.core.model.Passthru;

@Mapper(componentModel = "spring")
public interface PassthruMapper {

	Passthru toPassthru(PassthruDto passthruDto);

	PassthruDto toPassthruDto(Passthru Passthru);

	List<PassthruDto> toListPassthruDto(List<Passthru> template);

	List<Passthru> toListPassthru(List<PassthruDto> templatesDto);

}
