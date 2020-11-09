package com.mx.axeleratum.americantower.contract.core.model;

public enum ErrorCodes {

    INCORRECT_PARAMS(100, "error-100"),
    BUSSINESS_ERROR(200, "error-200"),
    RUNTIME_ERROR(300, "error-300");

    private final int value;
    private final String reasonPhrase;

    ErrorCodes(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public int getValue() {
        return value;
    }
}
