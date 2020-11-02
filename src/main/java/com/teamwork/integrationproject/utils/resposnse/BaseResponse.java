package com.teamwork.integrationproject.utils.resposnse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * Author Anyho(wuh@infoepoh.com)
 * Time   2019/12/8 21:06 星期日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse
{
    private String message;
    @Builder.Default
    private ResultCode code = ResultCode.SUCCESS;

    public void setCode(ResultCode code)
    {
        this.code = code;
        if (code != null && StringUtils.isEmpty(message))
        {
            message = code.getMsg();
        }
    }

    public boolean isSuccess()
    {
        return code == ResultCode.SUCCESS;
    }
}
