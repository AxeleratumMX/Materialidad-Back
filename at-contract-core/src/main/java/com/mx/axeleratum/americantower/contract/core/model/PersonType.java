package com.mx.axeleratum.americantower.contract.core.model;

public enum PersonType {

    EMPRESA("Empresa"),
    PERSONA("Persona");

    private String tipoPersona;

    PersonType(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
}
