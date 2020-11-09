package com.mx.axeleratum.americantower.contract.client.dto.entry;

import com.mx.axeleratum.americantower.contract.core.model.Contact;
import com.mx.axeleratum.americantower.contract.core.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ClientDtoEntry {
    private String description;
    private List<User> users;
    private String idCustomer;
    private String tipoPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private String rfc;
    private String codigoPostal;
    private String colonia;
    private String municipalidad;
    private String estado;
    private String status;
    private List<Contact> contactList;
}
