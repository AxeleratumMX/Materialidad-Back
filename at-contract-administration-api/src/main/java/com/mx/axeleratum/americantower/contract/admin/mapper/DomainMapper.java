package com.mx.axeleratum.americantower.contract.admin.mapper;


import com.mx.axeleratum.americantower.contract.admin.dto.DomainDto;
import com.mx.axeleratum.americantower.contract.admin.dto.DomainValueDto;
import com.mx.axeleratum.americantower.contract.core.model.Domain;
import com.mx.axeleratum.americantower.contract.core.model.DomainValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DomainMapper {

    @Mapping( target = "name", source = "key.value")
    DomainDto toDomainDto(Domain domain);

    @Mapping( source = "name", target = "key")
    Domain toDomain(DomainDto domainDto);

    DomainValueDto toDomainValueDto(DomainValue domainValue);

    DomainValue toDomainValue(DomainValueDto domainValueDto);

    List<DomainValueDto> toDomainValueDtoList(Collection<DomainValue> domainValues);



}
