package net.moopa.apc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Moopa on 28/06/2017.
 * blog: leeautumn.net
 *
 * @autuor : Moopa
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApcHttpParam {
    public String params() default "[]";
}
