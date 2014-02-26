package org.ddelizia.vcrud.core.basic.service;

import org.ddelizia.vcrud.core.basic.repository.GenericRepository;
import org.ddelizia.vcrud.core.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 11:30
 * To change this template use File | Settings | File Templates.
 */
public class AbstractService {

    @Autowired
    @Qualifier(GenericRepository.DEFAULT_BEAN_NAME)
    private GenericRepository genericRepository;

    @Autowired
    @Qualifier("validator")
    private Validator validator;

    protected void validate(Object request) {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ValidationException(constraintViolations);
        }
    }

    public GenericRepository getGenericRepository() {
        return genericRepository;
    }

    public void setGenericRepository(GenericRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
