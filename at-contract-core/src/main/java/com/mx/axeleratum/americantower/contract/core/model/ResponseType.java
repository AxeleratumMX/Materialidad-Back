package com.mx.axeleratum.americantower.contract.core.model;

public enum ResponseType {

    ACCEPT("Aceptar"),
    REJECT("Rechazar"),
    POSTPONE("Postponer"),
    APPROVE("Aprobar");

    private String response;

    ResponseType(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
