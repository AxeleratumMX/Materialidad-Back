package com.mx.axeleratum.americantower.contract.admin.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import com.mx.axeleratum.americantower.contract.admin.dto.RolDto;
import com.mx.axeleratum.americantower.contract.core.model.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolDto toRolDto(Rol rol);
    

    Rol toRol(RolDto rolDto);

	List<RolDto> toListRolDto(List<Rol> rols);
}
