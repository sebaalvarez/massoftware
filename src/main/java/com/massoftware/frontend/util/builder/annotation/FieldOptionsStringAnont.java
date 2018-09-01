package com.massoftware.frontend.util.builder.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface FieldOptionsStringAnont {

	String[] values() ;
	
	String[] captions() default {} ;
	
	String defaultValue() default "";
	
	boolean horizontal() default true;

}