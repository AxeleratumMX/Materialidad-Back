package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

@Data
public class Error {
    private String code;
    private String message;
    private String detail;

}
