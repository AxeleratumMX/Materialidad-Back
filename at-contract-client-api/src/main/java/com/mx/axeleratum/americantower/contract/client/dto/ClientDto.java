package com.mx.axeleratum.americantower.contract.client.dto;

import com.mx.axeleratum.americantower.contract.core.model.ClientType;
import com.mx.axeleratum.americantower.contract.core.model.PersonType;
import lombok.Data;

import java.util.List;

@Data
public class ClientDto {
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
    private List<ContactDto> contactos;}
