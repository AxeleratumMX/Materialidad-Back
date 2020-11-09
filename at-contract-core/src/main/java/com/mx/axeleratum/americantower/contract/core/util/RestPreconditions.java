package com.mx.axeleratum.americantower.contract.core.util;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.mx.axeleratum.americantower.contract.core.exception.BussinessServiceException;
import com.mx.axeleratum.americantower.contract.core.exception.MyResourceNotFoundException;

/**
 * Simple static methods to be called at the start of your own methods to verify
 * correct arguments and state. If the Precondition fails, an {@link HttpStatus}
 * code is thrown
 */
public class RestPreconditions {

	private RestPreconditions() {
		throw new AssertionError();
	}

	// API

	public static void checkFound(final boolean expression) {
		if (!expression) {
			throw new MyResourceNotFoundException();
		}
	}

	public static <T> T checkFound(final T resource, String message) {
		if (resource == null) {
			throw new MyResourceNotFoundException(message);
		}

		return resource;
	}
	
	private static String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
	    return constraintViolations.stream()
	      .map( cv -> cv == null ? "null" : /*cv.getPropertyPath() + ": " +*/ cv.getMessage() )
	      .collect( Collectors.joining( ", " ) );
	 }

	public static <T> T checkEntity(final T resource) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(resource);

		if (constraintViolations.size() > 0) {
			throw new BussinessServiceException(toString(constraintViolations));
		}
		return resource;
	}

	public static void checkIsTrue(Boolean resource, String message) {
		if (resource == false) {
			throw new BussinessServiceException(message);
		}
	}

}