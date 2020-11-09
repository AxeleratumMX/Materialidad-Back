package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "contacts")
public class Contact {
    @Id
    private String id;
    private ContactType tipoContacto;
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String email;
    private String name;
    private String telefono;
    private String celular;
    @DBRef
    private User user;
}
