package com.mx.axeleratum.americantower.contract.admin.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.admin.dto.OrganizationDto;
import com.mx.axeleratum.americantower.contract.core.model.Organization;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

	OrganizationDto toOrganizationDto(Organization organization);

	Organization toOrganization(OrganizationDto organizationDto);

	List<OrganizationDto> toListToOrganizationDto(List<Organization> users);
}
