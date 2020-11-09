package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;


public enum ContractStatusType {

    BORRADOR("Borrador"),
    REVISION("Revision"),
    FIRMA("Firma"),
    ACTIVO("Activo"),
    CANCELADO("Cancelado");


    private String contractType;

    ContractStatusType(String contactType) {
        this.contractType = contactType;
    }

    public String getContractType() {
        return contractType;
    }
}
