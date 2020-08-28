package com.teamwork.integrationproject.utils;

import org.slf4j.LoggerFactory;


public class LogHelper
{
    public static void debug(Object context, String s)
    {
        LoggerFactory.getLogger(context.getClass()).debug(s);
    }

    public static void debug(Object context, String s, Object o)
    {
        LoggerFactory.getLogger(context.getClass()).debug(s, o);
    }


    public static void debug(Object context, String s, Object o, Object o1)
    {
        LoggerFactory.getLogger(context.getClass()).debug(s, o, o1);
    }


    public static void debug(Object context, String s, Object... objects)
    {
        LoggerFactory.getLogger(context.getClass()).debug(s, objects);
    }


    public static void debug(Object context, String s, Throwable throwable)
    {
        LoggerFactory.getLogger(context.getClass()).debug(s, throwable);
    }


    public static void info(Object context, String s)
    {
        LoggerFactory.getLogger(context.getClass()).info(s);
    }


    public static void info(Object context, String s, Object o)
    {
        LoggerFactory.getLogger(context.getClass()).info(s, o);
    }


    public static void info(Object context, String s, Object o, Object o1)
    {
        LoggerFactory.getLogger(context.getClass()).info(s, o, o1);
    }


    public static void info(Object context, String s, Object... objects)
    {
        LoggerFactory.getLogger(context.getClass()).info(s, objects);
    }


    public static void info(Object context, String s, Throwable throwable)
    {
        LoggerFactory.getLogger(context.getClass()).info(s, throwable);
    }

    public static void error(Object context, String s)
    {
        LoggerFactory.getLogger(context.getClass()).error(s);
    }


    public static void error(Object context, String s, Object o)
    {
        LoggerFactory.getLogger(context.getClass()).error(s, o);
    }


    public static void error(Object context, String s, Object o, Object o1)
    {
        LoggerFactory.getLogger(context.getClass()).error(s, o, o1);
    }


    public static void error(Object context, String s, Object... objects)
    {
        LoggerFactory.getLogger(context.getClass()).error(s, objects);
    }


    public static void error(Object context, String s, Throwable throwable)
    {
        LoggerFactory.getLogger(context.getClass()).error(s, throwable);
    }

    public static void error(Class clazz, String s, Throwable throwable)
    {
        LoggerFactory.getLogger(clazz).error(s, throwable);
    }

    public static void error(Class clazz, String s, Object... objects)
    {
        LoggerFactory.getLogger(clazz).error(s, objects);
    }

    public static void error(Class clazz, String s, Object o, Object o1)
    {
        LoggerFactory.getLogger(clazz).error(s, o, o1);
    }
}
