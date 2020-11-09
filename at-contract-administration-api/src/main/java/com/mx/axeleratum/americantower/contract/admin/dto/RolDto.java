package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class RolDto {
    private String id;    
    private String name;
    private String description;
    private AutorizacionesDto secciones;
}
