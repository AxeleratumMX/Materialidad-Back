package com.mx.axeleratum.americantower.contract.core.exception;

import java.util.HashSet;
import java.util.Set;

import com.mx.axeleratum.americantower.contract.core.model.ErrorList;

import lombok.Data;

@Data()
public class OperationException extends RuntimeException{
	
	private Set<String> messages = new HashSet<String>();

    /**
	 * 
	 */
	private static final long serialVersionUID = 364077127441485500L;
	
	private ErrorList errorList;

    public OperationException() {
    }

    public OperationException(String message) {
        super(message);
        this.messages.add(message);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
        this.messages.add(message);
    }
    
    public void addMessage(String message) {
    	this.messages.add(message);
    } 
}
