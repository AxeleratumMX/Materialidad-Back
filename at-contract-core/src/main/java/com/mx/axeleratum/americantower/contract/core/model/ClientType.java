package com.mx.axeleratum.americantower.contract.core.model;

public enum ClientType {
     
    CLIENTE("Cliente"),
    BENEFICIARIO("Beneficiario");

    private String tipoCliente;

    ClientType(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}
