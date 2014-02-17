package org.ddelizia.vcrud.core.security.exception;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String message){
        super(message);
    }
}
