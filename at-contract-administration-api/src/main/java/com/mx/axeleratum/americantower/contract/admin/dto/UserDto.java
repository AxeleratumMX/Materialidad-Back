package com.mx.axeleratum.americantower.contract.admin.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;    
    private String correo;
    private String name;
    private String apellidos;
    private String telefono;
    private String username;
    private String role;
    private String organizacion;
    private String idPhoto;
}
