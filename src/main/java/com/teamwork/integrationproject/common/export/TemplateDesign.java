package com.teamwork.integrationproject.common.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import lombok.Data;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2021/3/1 16:30
 */
@Data
public class TemplateDesign
{
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty({"主标题","姓名"})
    private String username;
    @ExcelProperty({"主标题", "年龄"})
    private Integer age;
    @ExcelProperty({"主标题", "班级"})
    private String grade;
}
