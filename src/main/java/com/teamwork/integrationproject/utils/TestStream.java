package com.teamwork.integrationproject.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用模块名称<p>
 * <p>
 * 代码描述<p>
 * <p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 * <p>
 * Company: 阿里云<p>
 *
 * @author haochaojie
 * @since 2020/5/27 8:49

 */

public class TestStream {
    /**
     * 引入日志
     */
    private  static  Logger logger = LogManager.getLogger(TestStream.class);
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("张三", 22, "1995-11-02", 57));
        list.add(new Student("李四", 24, "1997-12-02", 53));
        list.add(new Student("王五", 22, "1995-11-03", 56));
        list.add(new Student("马六", 21, "1994-09-09", 51));
        System.out.println("排序前" + list);
        //先按年龄降序排列，再按体重升序排列
        List<Student> collect = list.stream().sorted(Comparator.comparing(Student::getAge).reversed().thenComparing(Student::getWeigh)).collect(Collectors.toList());
        System.out.println("排序后" + collect);
        //stream()遍历
        list.stream().forEach(l -> {
            System.out.println("输出信息" + l);
        });
        //根据年龄进行分组
        Map<Integer, List<Student>> map = list.stream().collect(Collectors.groupingBy(Student::getAge));
        map.forEach((k, v) -> {
            System.out.println("k = " + k + "--------" + "v = " + v);
        });
        //分组后合计
        Map<Integer, Long> mapCount = list.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.counting()));
        System.out.println("分组数量" + mapCount);
        //获取集合中对象的年龄(升序)，获取前4个(获取第一个为最小年龄)，并返回集合
        List<Integer> collect1 = list.stream().map(Student::getAge).sorted().limit(2).collect(Collectors.toList());
        System.out.println(collect1);
        //stream()中的filter筛选
        List<Student> ageThan22 = list.stream().filter(s -> s.getAge() >= 22).collect(Collectors.toList());
        System.out.println(ageThan22);
        //stream()中的去重distinct
        list.add(new Student("张三", 22, "1995-11-02", 57));
        System.out.println("去重前" + list);
        List<Student> distinctList = list.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后" + distinctList);
        //limit返回限定元素
        List<Student> limitList = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).limit(1).collect(Collectors.toList());
        System.out.println(limitList);
        //skip去除前n个元素(通过age降序排列，并去除第一个元素)
        List<Student> skipList = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).skip(1).collect(Collectors.toList());
        System.out.println(skipList);
        //stream()获取集合中所有对象的name属性(使用toSet达到去重效果)
//        list.add(new Student("张三", 22, "1995-11-02", 57));
//        List<String> nameList = list.stream().map(Student::getName).collect(Collectors.toList());
        Set<String> nameList = list.stream().map(Student::getName).collect(Collectors.toSet());
        System.out.println("名字集合" + nameList);
        //stream()中的split拆分
        List<String> splitName = nameList.stream().map(n -> n.split("")).flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println(splitName);
        //stream()检测是否全部满足条件allMatch
        boolean allMatch = list.stream().allMatch(s -> s.getAge() > 23);
        System.out.println(allMatch);
        //stream()检测任意满足条件
        boolean anyMatch = list.stream().anyMatch(s -> s.getAge() > 23);
        System.out.println(anyMatch);
        //stream()流中是没有匹配给定条件(没有为true，有为false)
        boolean noneMatch = list.stream().noneMatch(s -> s.getName().contains("哈哈"));
        System.out.println(noneMatch);
        //stream()中找到第一个findFirst
        Optional<Student> first = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).findFirst();
        list.stream().max(Comparator.comparing(Student::getAge));
        System.out.println(first);
        //stream()中找到任意一个元素
        Optional<Student> findAny = list.stream().findAny();
        System.out.println(findAny);
        //stream()中计算数量count
        long count = list.stream().count();
        System.out.println(count);
        //最大值，最小值maxBy(),minBy()
        Optional<Student> maxAge = list.stream().max(Comparator.comparing(Student::getAge));
        System.out.println(maxAge);
        //stream中求和sum和平均avg
        int sumAge = list.stream().mapToInt(Student::getAge).sum();
        System.out.println("求和：" + sumAge);
        Double avgAge = list.stream().collect(Collectors.averagingDouble(Student::getAge));
        System.out.println("平均" + avgAge);



        //stream中的分区

        try {
            Map<Boolean, List<Student>> partMap = list.stream().collect(Collectors.partitioningBy(s -> s.getAge() >= 22));
            list.forEach(System.out::println);
            //占位符方式打印日志
            logger.info("haha{}----{}","196", "197");
            logger.info("haha{}----{}","196");
            //直接打印日志
            logger.info("直接打印");
        } catch (Exception e) {
            logger.error(e);
        } finally {
            logger.info("hahaha");
        }
    }

}
