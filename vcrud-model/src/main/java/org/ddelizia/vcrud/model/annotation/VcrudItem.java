package org.ddelizia.vcrud.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VcrudItem {

    public static final String ROOT="/";

    public String group();

    public String parent();

    public String label();

}
