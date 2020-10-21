package com.teamwork.integrationproject.dto;

import com.teamwork.integrationproject.utils.resposnse.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用类类型返回值
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericTypeResponse<T>  extends BaseResponse
{
    private T data;
}
