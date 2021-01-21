package com.teamwork.integrationproject.common.repository.orm;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column
{
    String value();
}
