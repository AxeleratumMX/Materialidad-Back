package com.mx.axeleratum.americantower.contract.core.model;

public enum ContactType {

    CONTACTO("Contacto"),
    DUENIO("Dueño"),
    FIRMANTE("Firmante");


    private String contactType;

    ContactType(String contactType) {
        this.contactType = contactType;
    }
}
