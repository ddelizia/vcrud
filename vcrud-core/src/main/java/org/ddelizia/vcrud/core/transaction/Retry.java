package org.ddelizia.vcrud.core.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ddelizia
 * @since 09/03/14 13:32
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retry {

	Class<? extends Exception>[] on();

	int times() default 1;
}
