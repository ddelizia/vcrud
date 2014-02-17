package org.ddelizia.vcrud.core.exception;

import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends RuntimeException {

    private static final int status = HttpStatus.BAD_REQUEST.value();
    private String errorMessage;
    private String developerMessage;
    private Set<? extends ConstraintViolation<?>> violations;

    public ValidationException() {
        errorMessage = "Validation Error";
        developerMessage = "The data passed in the request was invalid. Please check and resubmit";
    }

    public ValidationException(String message) {
        super();
        errorMessage = message;
    }

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        this();
        this.violations = violations;
    }
}
