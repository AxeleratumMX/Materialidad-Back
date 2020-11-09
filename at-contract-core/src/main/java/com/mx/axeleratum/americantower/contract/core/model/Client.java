package com.mx.axeleratum.americantower.contract.core.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "clients")
public class Client {
    @Id
    private String id;
    private String idCliente;
    private ClientType tipoCliente;
    private PersonType tipoPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String razonSocial;
    private String direccion;
    private String codigoPostal;
    private String colonia;
    private String municipalidad;
    private String estado;
    private String telefono;
    private String celular;
    private String email;
    private String rfc;
    private String description;
    private String statusCliente;
    @DBRef(lazy = true)
    private List<Contact> contactos;
}
