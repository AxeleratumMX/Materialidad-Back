package com.mx.axeleratum.americantower.contract.core.exception;

import com.mx.axeleratum.americantower.contract.core.model.Error;
import com.mx.axeleratum.americantower.contract.core.model.ErrorCodes;
import com.mx.axeleratum.americantower.contract.core.model.ErrorList;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex, WebRequest request) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        Error error = new Error();
        error.setCode(ErrorCodes.INCORRECT_PARAMS.getReasonPhrase());
        error.setMessage(ex.getMessage());
        ErrorList errorList = ErrorList.addError(error);
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(value = { BussinessServiceException.class })
    protected ResponseEntity<Object> handleBussinessServiceException(RuntimeException ex, WebRequest request) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        //String bodyOfResponse = ex.getMessage();
        Error error = new Error();
        error.setCode(ErrorCodes.BUSSINESS_ERROR.getReasonPhrase());
        error.setMessage(ex.getMessage());
        ErrorList errorList = ErrorList.addError(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorList);
    }

    @ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<Object> handleNullPointerException(RuntimeException ex, WebRequest request) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        //String bodyOfResponse = ex.getMessage();
        Error error = new Error();
        error.setCode(ErrorCodes.BUSSINESS_ERROR.getReasonPhrase());
        error.setMessage(ex.getMessage());
        ErrorList errorList = ErrorList.addError(error);
        return ResponseEntity.badRequest().body(errorList);
    }
    
    @ExceptionHandler(value = { MyResourceNotFoundException.class })
    protected ResponseEntity<Object> myResourceNotFoundException(RuntimeException ex, WebRequest request) {
    	log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
        //String bodyOfResponse = ex.getMessage();
        Error error = new Error();
        error.setCode(ErrorCodes.BUSSINESS_ERROR.getReasonPhrase());
        error.setMessage(ex.getMessage());
        ErrorList errorList = ErrorList.addError(error);
        return ResponseEntity.badRequest().body(errorList);
    }
 }