//package com.teamwork.integrationproject.common.repository.orm;
//
//import lombok.Builder;
//import lombok.Data;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.math.BigDecimal;
//import java.sql.Time;
//
//
//@Data
//@Builder
//public class FieldOrmInfo
//{
//    private String columnName;
//    private Field field;
//
//    public Object getValue(Object obj) throws Exception
//    {
//        field.setAccessible(true);
//        Object fieldObj = field.get(obj);
//        if (field.getType().isEnum())
//        {
//            Method nameMethod = field.getType().getMethod("getValue", null);
//            return nameMethod.invoke(fieldObj);
//        }
//
//        return fieldObj;
//    }
//
//    public void setValue(SqlRowSet rowSet, Object obj) throws Exception
//    {
//        Object fieldValue = translateFieldValue(rowSet);
//        field.setAccessible(true);
//        field.set(obj, fieldValue);
//    }
//
//    private Object translateFieldValue(SqlRowSet rowSet) throws Exception
//    {
//        //SQLFeatureNotSupportedException: Not supported yet.
//        // Object value = rowSet.getObject(columnName, field.getType());
//        Class targetType = field.getType();
//        Object value = rowSet.getObject(columnName);
//        if (value == null)
//        {
//            return null;
//        }
//
//        if (targetType.isEnum())
//        {
//            Integer enumValue = rowSet.getInt(columnName);
//            Method getValueMethod = targetType.getMethod("getValue", null);
//            Object[] valueArray = targetType.getEnumConstants();
//            for (Object obj : valueArray)
//            {
//                if (enumValue.equals(getValueMethod.invoke(obj, null)))
//                {
//                    return obj;
//                }
//            }
//
//            throw new IllegalArgumentException(String.format("wrong value: %s for enum type: %s", value, targetType));
//        }
//        if (targetType == byte.class || targetType == Byte.class)
//        {
//            return rowSet.getByte(columnName);
//        }
//        if (targetType == short.class || targetType == Short.class)
//        {
//            return rowSet.getShort(columnName);
//        }
//        if (targetType == int.class || targetType == Integer.class)
//        {
//            return rowSet.getInt(columnName);
//        }
//        if (targetType == long.class || targetType == Long.class)
//        {
//            return rowSet.getLong(columnName);
//        }
//        if (targetType == float.class || targetType == Float.class)
//        {
//            return rowSet.getFloat(columnName);
//        }
//        if (targetType == double.class || targetType == Double.class)
//        {
//            return rowSet.getDouble(columnName);
//        }
//        if (targetType == boolean.class || targetType == Boolean.class)
//        {
//            return rowSet.getBoolean(columnName);
//        }
//        if (targetType == BigDecimal.class)
//        {
//            return rowSet.getBigDecimal(columnName);
//        }
//        if (targetType == String.class)
//        {
//            return rowSet.getString(columnName);
//        }
//        if (targetType == java.util.Date.class
//                || targetType == java.sql.Date.class
//                || targetType == Time.class
//                || targetType == java.sql.Timestamp.class)
//        {
//            return translateDate(targetType, value);
//        }
//
//        throw new IllegalArgumentException("Not supported yet: " + targetType);
//    }
//
//    private Object translateDate(Class targetType, Object value) throws Exception
//    {
//        if (value == null)
//        {
//            return null;
//        }
//
//        Class<?> valClass = value.getClass();
//        if (valClass == targetType)
//        {
//            return value;
//        }
//
//        long time = -1;
//        if (valClass == java.util.Date.class)
//        {
//            time = ((java.util.Date) value).getTime();
//        }
//
//        if (valClass == java.sql.Date.class)
//        {
//            time = ((java.sql.Date) value).getTime();
//        }
//
//        if (valClass == Time.class)
//        {
//            time = ((Time) value).getTime();
//        }
//
//        if (valClass == java.sql.Timestamp.class)
//        {
//            time = ((java.sql.Timestamp) value).getTime();
//        }
//
//        if (valClass == oracle.sql.TIMESTAMP.class)
//        {
//            time = ((oracle.sql.TIMESTAMP) value).dateValue().getTime();
//        }
//
//        if (time == -1)
//        {
//            throw new IllegalArgumentException("unsupported date class: " + valClass);
//        }
//
//        Constructor constructor = targetType.getConstructor(long.class);
//        return constructor.newInstance(time);
//    }
//}
