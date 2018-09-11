package com.massoftware.frontend.custom.windows.builder.annotation;

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
public @interface FieldColumnMetaDataAnont {

	int attSize() default -1;

//	 SimpleStringTraslateFilter.CONTAINS_WORDS_AND;
	String simpleStringTraslateFilterMode() default "CONTAINS_WORDS_AND"; 

	boolean pidFilteringStart() default false;
	
	boolean ascOrderByStart() default true;

	boolean hidden() default false;

}
