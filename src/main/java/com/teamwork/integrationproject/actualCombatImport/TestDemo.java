package com.teamwork.integrationproject.actualCombatImport;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2021/1/27 11:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDemo {

    @ExcelProperty("哈哈哈哈你能成功吗")
    private String kk;
}
