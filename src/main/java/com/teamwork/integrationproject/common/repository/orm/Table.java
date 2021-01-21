package com.teamwork.integrationproject.common.repository.orm;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Table
{
    String value();
}
