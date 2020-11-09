package com.mx.axeleratum.americantower.contract.core.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users") 
public class User {
    @Id
    private String id;
    
    @NotNull(message = "Debe ingresar un correo electrónico")
    @Size(min = 1,message = "Debe ingresar un correo electrónico")
    private String correo;
    
    @NotNull(message = "Debe ingresar un nombre de usuario")
    @Size(min = 1,message = "Debe ingresar un nombre de usuario")
    private String name;
    
    @NotNull(message = "Debe ingresar un apellido")
    @Size(min = 1,message = "Debe ingresar un apellido") 
    private String apellidos;
    
    @NotNull(message = "Debe ingresar un teléfono")
    @Size(min = 1,message = "Debe ingresar un teléfono")
    private String telefono;
    
    @NotNull(message = "Debe ingresar un username")
    @Size(min = 1,message = "Debe ingresar un username")
    @Indexed(unique=true)
    private String username;
    
    private String password;
    
    @NotNull(message = "Debe ingresar un rol")
    @Size(min = 1,message = "Debe ingresar un rol")
    private String role;
    
    @NotNull(message = "Debe ingresar una organización")
    @Size(min = 1,message = "Debe ingresar un apellido")
    private String organizacion;
    
    private String idPhoto;
}
