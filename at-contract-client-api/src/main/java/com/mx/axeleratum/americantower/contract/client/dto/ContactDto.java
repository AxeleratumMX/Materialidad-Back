package com.mx.axeleratum.americantower.contract.client.dto;

import com.mx.axeleratum.americantower.contract.core.model.ContactType;
import lombok.Data;

@Data
public class ContactDto {
    private String id;
    private ContactType tipoContacto;
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String email;
    private String name;
    private String telefono;
    private String celular;
}
