//package com.teamwork.integrationproject.common.repository.orm;
//
//import lombok.Builder;
//import lombok.Data;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Data
//@Builder
//public class EntityOrmInfo
//{
//    private String tableName;
//    private FieldOrmInfo keyFieldInfo;
//    private List<FieldOrmInfo> fieldInfoList;
//
//    public static <M> EntityOrmInfo from(Class<M> clazz)
//    {
//        Table table = clazz.getAnnotation(Table.class);
//        if (table == null)
//        {
//            throw new IllegalArgumentException("class should be annotated with @Table!");
//        }
//
//        Field[] fields = clazz.getDeclaredFields();
//        if (fields == null || fields.length < 1)
//        {
//            throw new IllegalArgumentException("no declared fields found!");
//        }
//
//        List<FieldOrmInfo> fieldInfoList = new ArrayList<>();
//        FieldOrmInfo keyFieldInfo = null;
//        for (Field field : fields)
//        {
//            Column column = field.getAnnotation(Column.class);
//            Id id = field.getAnnotation(Id.class);
//            if (column == null && id == null)
//            {
//                continue;
//            }
//
//            if (id != null)
//            {
//                if (keyFieldInfo != null)
//                {
//                    throw new IllegalArgumentException("field annotated with @Id have already exists!");
//                }
//
//                keyFieldInfo = FieldOrmInfo.builder().field(field).columnName(id.value()).build();
//                continue;
//            }
//
//            fieldInfoList.add(FieldOrmInfo.builder().field(field).columnName(column.value()).build());
//        }
//
//        if (keyFieldInfo == null)
//        {
//            throw new IllegalArgumentException("no field annotated with @Id found!");
//        }
//
//        if (fieldInfoList.size() < 1)
//        {
//            throw new IllegalArgumentException("no fields annotated with @Column found!");
//        }
//
//        return EntityOrmInfo.builder().tableName(table.value())
//                .keyFieldInfo(keyFieldInfo).fieldInfoList(fieldInfoList).build();
//    }
//}
