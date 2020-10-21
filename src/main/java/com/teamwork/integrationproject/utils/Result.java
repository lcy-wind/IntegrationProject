package com.teamwork.integrationproject.utils;

import com.teamwork.integrationproject.common.resposnse.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装结果集<p>
 * <p>
 * 代码描述<p>
 * <p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved. <p>
 *
 * @author haochaojie
 * @since 2020/8/5 16:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> extends BaseResponse {
    private T data;
}
