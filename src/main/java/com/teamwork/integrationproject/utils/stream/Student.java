package com.teamwork.integrationproject.utils.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
 * @since 2020/5/27 8:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {

    private String name;

    private Integer age;

    private String birth;

    private Integer weigh;


}
