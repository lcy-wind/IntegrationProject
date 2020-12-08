package com.teamwork.integrationproject.common.export;

import com.teamwork.integrationproject.utils.excel.XssExcelUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/10/21 11:02
 * 导出模板制作
 */
public class ExportTemplate
{
    public static void exportStudent(HttpServletResponse response, String fileName, List<Map<String,Object>> dataList) {
        //导出数据量
        Workbook wb = new SXSSFWorkbook(500);
        List<String[]> title = new ArrayList<>();
        title.add(new String[]{"name","姓名","5000", CellType.STRING+""});
        title.add(new String[]{"age","年龄","5000", CellType.NUMERIC+""});
        title.add(new String[]{"grade","年级","5000", CellType.STRING+""});
        XssExcelUtils.createSheetByContent(wb,fileName,title,dataList);
        XssExcelUtils.writeFile(response,fileName+".xlsx",wb);
    }

}