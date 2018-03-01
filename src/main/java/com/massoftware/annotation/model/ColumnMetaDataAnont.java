package com.massoftware.annotation.model;

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
public @interface ColumnMetaDataAnont {

	int attSize() default -1;

	String simpleStringTraslateFilterMode() default "CONTAINS_WORDS_AND"; // SimpleStringTraslateFilter.CONTAINS_WORDS_AND;

	boolean pidFilteringStart() default false;

	boolean hidden() default false;

}
