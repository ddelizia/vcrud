package org.ddelizia.vcrud.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VcrudProperty {

    public String group() default "";
	
	public boolean showOnCollection() default false;

    public boolean showOnCombo() default false;

    public boolean showOnResultTable() default false;

}
