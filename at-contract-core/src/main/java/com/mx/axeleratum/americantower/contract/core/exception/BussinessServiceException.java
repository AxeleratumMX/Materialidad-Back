package com.mx.axeleratum.americantower.contract.core.exception;

import com.mx.axeleratum.americantower.contract.core.model.ErrorList;
import lombok.Data;

@Data()
public class BussinessServiceException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 364077127441485500L;
	
	private ErrorList errorList;

    public BussinessServiceException() {
    }

    public BussinessServiceException(String message) {
        super(message);
    }

    public BussinessServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
