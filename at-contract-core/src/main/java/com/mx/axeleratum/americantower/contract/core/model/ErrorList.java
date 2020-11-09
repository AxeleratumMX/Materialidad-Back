package com.mx.axeleratum.americantower.contract.core.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorList {
    public ErrorList() {
        errors = new ArrayList<>();
    }
    public static ErrorList addError(Error error) {
        ErrorList errorList = new ErrorList();
        errorList.getErrors().add(error);
        return errorList;
    }
    List<Error> errors;
}
