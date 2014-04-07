package org.ddelizia.vcrud.core.exception;

import org.ddelizia.vcrud.core.basic.model.VcrudItem;

/**
 * @author ddelizia
 * @since 17/02/14 11:32
 */
public class ModelExistsException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ModelExistsException(Class<? extends VcrudItem> aClass, String message) {
        super(aClass.getName() + " already in db: "+message);
    }
}
