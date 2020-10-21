package com.teamwork.integrationproject.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 导出excel工具类
 */
public class XssExcelUtils
{
    private static Logger log = LoggerFactory.getLogger(XssExcelUtils.class);
    private static Row contentRow;
    private static Cell cell;
    /**
     * 获取日期
     */
    public static Date getCellForDate(Sheet sheet, int rowNum, int cellnum) {
        Date date = null;
        try {
            Cell cell = sheet.getRow(rowNum).getCell(cellnum);
            date = cell.getDateCellValue();
        } catch (Exception e) {
            log.error("getCellForDate error:", e);
        }
        return date;
    }
    /**
     * 获取单元格数据--字符串
     * 如果单元格内没有数据，返回空字符串
     * @return String
     */
    public static String getCellForString(Sheet sheet, int rowNum, int cellnum) {
        String str = "";
        try {
            Cell cell = sheet.getRow(rowNum).getCell(cellnum);
            if (cell.getCellTypeEnum() == CellType.FORMULA) {// 公式
                str = cell.getStringCellValue();
            } else if (cell.getCellTypeEnum() == CellType.BLANK) { // 是否为空型
                str = "";
            } else if (cell.getCellTypeEnum() == CellType.STRING) {// 是否为字符串型
                str = cell.getStringCellValue();
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {// 是否为数值型
                str = String.valueOf((long) cell.getNumericCellValue());
            } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {// 是否为布尔型
                str = Boolean.toString(cell.getBooleanCellValue());
            }
        } catch (Exception e) {
            log.error("getCellForString error:", e);
        }
        if (StringUtils.isEmpty(str)) {
            str = "";
        }
        return str.trim();
    }

    /**
     * 获取单元格数据--数字
     * 如果单元格内没有数据，或其它情况返回0
     * @return BigDecimal
     */
    public static BigDecimal getCellForBigDecimal(Sheet sheet, int rowNum, int cellnum) {
        BigDecimal temp = null;
        try {
            Cell cell = sheet.getRow(rowNum).getCell(cellnum);
            if (cell.getCellTypeEnum() == CellType.FORMULA) {// 公式
                temp = BigDecimal.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellTypeEnum() == CellType.STRING) {// 是否为字符串型
                temp = BigDecimal.valueOf(Double.valueOf(cell.getStringCellValue().trim()));
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {// 是否为数值型
                temp = BigDecimal.valueOf(cell.getNumericCellValue());
            }
        } catch (Exception e) {
            log.error("getCellForBigDecimal error:", e);
        }
        if (temp == null) {
            temp = new BigDecimal(0);
        }
        temp.setScale(2, BigDecimal.ROUND_HALF_UP);
        return temp;
    }

    /**
     * 获取单元格数据--数字
     * 如果单元格内没有数据，或其它情况返回null
     * @return BigDecimal
     */
    public static BigDecimal getCellForBigDecimalNull(Sheet sheet, int rowNum, int cellnum) {
        BigDecimal temp = null;
        try {
            Cell cell = sheet.getRow(rowNum).getCell(cellnum);
            if(cell ==null){
                return temp;
            }else{
                if (cell.getCellTypeEnum() == CellType.FORMULA) {// 公式
                    temp = BigDecimal.valueOf(cell.getNumericCellValue());
                } else if (cell.getCellTypeEnum() == CellType.STRING) {// 是否为字符串型
                    temp = BigDecimal.valueOf(Double.valueOf(cell.getStringCellValue().trim()));
                } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {// 是否为数值型
                    temp = BigDecimal.valueOf(cell.getNumericCellValue());
                }
            }
        } catch (Exception e) {
            log.error("getCellForBigDecimalNull error:", e);
        }
        return temp;
    }

    /**
     * 添加单元格
     * 根据行、列、类型、样式、值构造单元格
     */
    public static void addColInRow(Row row, int col, CellType cellType, CellStyle style, Object value) {
        cell = row.createCell(col);
        cell.setCellStyle(style);
        if (value != null) {
            cell.setCellType(cellType);
            if (CellType.STRING == cellType) {
                cell.setCellValue((String) value);
            } else if (CellType.NUMERIC == cellType) {
                if (value instanceof java.sql.Date) {
                    cell.setCellValue((java.sql.Date)value);
                } else if(value instanceof BigDecimal){
                    BigDecimal bigvalue = (BigDecimal)value;
                    double tempvalue = bigvalue.doubleValue();
                    cell.setCellValue(tempvalue);
                }  else if(value instanceof Integer){
                    Integer bigvalue = (Integer)value;
                    cell.setCellValue(bigvalue.doubleValue());
                } else if(value instanceof Long){
                    Long longValue=(Long)value;
                    cell.setCellValue(longValue.doubleValue());
                }else {
                    cell.setCellValue((Double.parseDouble(value==null||value==""?"0":value.toString())));
                }
            } else {
                cell.setCellValue((String) value);
            }
        }
    }

    /**
     * 添加单元格
     * 根据行、列、类型、样式、值构造单元格
     */
    public static void addColInRowProportation(Row row, int col, CellType cellType, CellStyle style, Object value) {
        cell = row.createCell(col);
        cell.setCellStyle(style);
        if (value != null) {
            if(value instanceof String){
                String valueStr = (String)value;
                boolean contains = valueStr.contains("%");
                if(contains==true){
                    cell.setCellType(CellType.STRING);
                }else{
                    cell.setCellType(cellType);
                }
            }else{
                cell.setCellType(cellType);
            }
            if (CellType.STRING == cellType) {
                if(value instanceof BigDecimal){
                    String ss = value.toString();
                    cell.setCellValue(ss);
                }else{
                    cell.setCellValue((String) value);
                }
            } else if (CellType.NUMERIC == cellType) {
                if (value instanceof java.sql.Date) {
                    cell.setCellValue((java.sql.Date)value);
                } else if(value instanceof BigDecimal){
                    BigDecimal bigvalue = (BigDecimal)value;
                    double tempvalue = bigvalue.doubleValue();
                    cell.setCellValue(tempvalue);
                }  else if(value instanceof Integer){
                    Integer bigvalue = (Integer)value;
                    cell.setCellValue(bigvalue.doubleValue());
                } else if(value instanceof Long){
                    Long longValue=(Long)value;
                    cell.setCellValue(longValue.doubleValue());
                }else if(value instanceof Double){
                    Double doubleValue = (Double)value;
                    cell.setCellValue(doubleValue.doubleValue());
                }else if(value==null||value==""){
                    cell.setCellValue(value==null||value==""?"0":value.toString());
                }else{
                    String valueStr2 = (String)value;
                    if("%".equals(valueStr2)){
                        cell.setCellValue("100%");
                    }else{
                        cell.setCellValue((String) value);
                    }
                }
            } else {

                cell.setCellValue((String) value);
            }
        }
    }

    /**
     * 设置单元格
     * 根据行、列、类型、样式、值构造单元格
     */
    public static void setColInRow(Row row, int col, CellType cellType, Object value) {
        cell = row.getCell(col);
        if (value != null) {
            if (CellType.STRING == cellType) {
                cell.setCellValue((String) value);
            } else if (CellType.NUMERIC == cellType) {
                if (value instanceof java.sql.Date) {
                    cell.setCellValue((java.sql.Date)value);
                } else if(value instanceof BigDecimal){
                    BigDecimal bigvalue = (BigDecimal)value;
                    double tempvalue = bigvalue.doubleValue();
                    cell.setCellValue(tempvalue);
                }  else if(value instanceof Integer){
                    Integer bigvalue = (Integer)value;
                    cell.setCellValue(bigvalue.doubleValue());
                } else if(value instanceof Long){
                    Long longValue=(Long)value;
                    cell.setCellValue(longValue.doubleValue());
                }else {
                    cell.setCellValue((Double) value);
                }
            } else {
                cell.setCellValue((String) value);
            }
        }
    }
    /**
     * 给单元格赋值，前提单元格对象已存在
     * @param row
     * @param col
     * @param cellType
     * @param value
     */
    public static void setCellValue(Row row, int col, CellType cellType, Object value) {
        Cell cell = row.getCell(col);
        if (value != null) {
            if (CellType.STRING == cellType) {
                cell.setCellValue((String) value);
            } else if (CellType.NUMERIC == cellType) {
                if (value instanceof java.sql.Date) {
                    cell.setCellValue((java.sql.Date)value);
                } else if(value instanceof BigDecimal){
                    cell.setCellValue(((BigDecimal)value).doubleValue());
                }  else if(value instanceof Integer){
                    cell.setCellValue(((Integer)value).doubleValue());
                } else if(value instanceof Long){
                    cell.setCellValue(((Long)value).doubleValue());
                }else {
                    cell.setCellValue((Double)value);
                }
            } else {
                cell.setCellValue((String) value);
            }
        }
    }
    /**
     * 删除行row
     * @param sheet
     * @param rowIndex
     */
    public static void removeRow(Sheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if(rowIndex >= 0 && rowIndex < lastRowNum){
            while (rowIndex <= lastRowNum) {
                sheet.removeRow(sheet.getRow(rowIndex++));
            }
        }
    }
    /**
     * 给单元格赋值，前提单元格对象已存在
     * @param row
     * @param cellStyle
     * @param cellnum
     * @param date
     */
    public static void setCellForDate(Row row, CellStyle cellStyle, int cellnum, Date date) {
        Cell cell;
        cell = row.createCell(cellnum);
        cell.setCellStyle(cellStyle);
        if (date != null)
            cell.setCellValue(date);
    }
    /**
     * 为单元格添加公式
     * @param row 行
     * @param col 列
     * @param style 单元格样式
     * @param cellFormula 公式
     */
    public static void addCellFormula(Row row, int col, CellStyle style, String cellFormula) {
        Cell cell = row.createCell(col);
        cell.setCellStyle(style);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula(cellFormula);
    }

    /**
     * 构造表头样式：单元格黑边、字体加粗、居中、背景色yellow
     */
    public static CellStyle createHeadStyle(Workbook wb, short color) {
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setAlignment(HorizontalAlignment.CENTER);//居中
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(color);
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontName("Arial");
        headStyle.setFont(font);
        headStyle.setWrapText(true);//自动换行
        return headStyle;
    }

    /**
     * 内容样式：单元格黑边、左对齐
     */
    public static CellStyle createContentStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(HorizontalAlignment.LEFT);//左对齐
        Font font = wb.createFont();
        font.setFontName("Arial");
        style.setFont(font);
        style.setWrapText(true);//自动换行
        return style;
    }
    /**
     * 设置对齐方式
     * @param wb
     * @param align
     * @return
     */
    public static CellStyle createAlignStyle(Workbook wb, short align){
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
//        style.setAlignment(align);//设置对齐方式
        style.setWrapText(true);//自动换行
        return style;
    }

    /**
     * 合并单元格
     */
    public static void addMergedRegion(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
        sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
    }

    /**
     * 根据内容创建sheet-固定模式
     * 	sheet名称
     * 	标题List：数组（0-key,1-name,2-width,3-type）--不允许为空
     * 	内容
     * 	宽度
     */
    public static void createSheetByContent(Workbook wb, String sheetName, List<String[]> titleList, List<Map<String, Object>> contentList) {
        Sheet sheet = wb.createSheet(sheetName);
        String[] title = null;
        /**创建标题**/
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(30);
        CellStyle headStyle = createHeadStyle(wb, IndexedColors.YELLOW.getIndex());//单元格样式
        CellStyle headStyle1 = createHeadStyle(wb, IndexedColors.LIME.getIndex());//单元格样式
        for (int i = 0; i < titleList.size(); i++) {
            title = titleList.get(i);
            if(title.length>4 && title[4]!=null && "1".equals(title[4])){
                addColInRow(titleRow, i, CellType.STRING, headStyle1, title[1]);
            }else{
                addColInRow(titleRow, i, CellType.STRING, headStyle, title[1]);
            }
            sheet.setColumnWidth(i, Integer.parseInt(title[2]));
        }
        /**生成内容**/
        CellStyle bodyStyle = createContentStyle(wb);
        if (contentList!=null && contentList.size()>0) {
            int contentIndex = 1;
            for (Map<String, Object> content : contentList) {
                contentRow = sheet.createRow(contentIndex++);
                for (int i = 0; i < titleList.size(); i++) {
                    title = titleList.get(i);
                    addColInRow(contentRow, i, CellType.valueOf(title[3]), bodyStyle, content.get(title[0]));
                }
            }
        }
    }

    /**
     * 根据内容创建sheet-不创建标题行
     * 	sheet名称
     * 	标题List：数组（0-key,1-name,2-width,3-type）--不允许为空
     * 	内容
     * 	宽度
     */
    public static void createSheetByContent(Workbook wb, Sheet sheet, List<String[]> titleList, List<Map<String, Object>> contentList, int contentIndex) {
        String[] title = null;
        /**创建标题**/
        for (int i = 0; i < titleList.size(); i++) {
            title = titleList.get(i);
            sheet.setColumnWidth(i, Integer.parseInt(title[2]));
        }
        /**生成内容**/
        CellStyle bodyStyle = createContentStyle(wb);
        if (contentList!=null && contentList.size()>0) {
            for (Map<String, Object> content : contentList) {
                contentRow = sheet.createRow(contentIndex++);
                for (int i = 0; i < titleList.size(); i++) {
                    title = titleList.get(i);
                    addColInRow(contentRow, i, CellType.valueOf(title[3]), bodyStyle, content.get(title[0]));
                }
            }
        }
    }
    /**
     *  适用于：最后一行是百分号的数据导出
     * 	根据内容创建sheet-不创建标题行  sheet名称
     * 	标题List：数组（0-key,1-name,2-width,3-type）--不允许为空
     * 	内容
     * 	宽度
     */
    public static void createSheetByContentForProportation(Workbook wb, Sheet sheet, List<String[]> titleList, List<Map<String, Object>> contentList, int contentIndex) {
        String[] title = null;
        /**创建标题**/
        for (int i = 0; i < titleList.size(); i++) {
            title = titleList.get(i);
            sheet.setColumnWidth(i, Integer.parseInt(title[2]));
        }
        /**生成内容**/
        CellStyle bodyStyle = createContentStyle(wb);
        if (contentList!=null && contentList.size()>0) {
            for (Map<String, Object> content : contentList) {
                contentRow = sheet.createRow(contentIndex++);
                for (int i = 0; i < titleList.size(); i++) {
                    title = titleList.get(i);
                    addColInRowProportation(contentRow, i, CellType.valueOf(title[3]), bodyStyle, content.get(title[0]));
                }
            }
        }
    }

    /**
     *  导出表格(新)
     */
    public static void writeFile(HttpServletResponse response, String fileName, Workbook workbook)
    {
        OutputStream out = null;
        try
        {
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                workbook.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
