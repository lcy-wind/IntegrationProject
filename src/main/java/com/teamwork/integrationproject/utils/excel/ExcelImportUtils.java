package com.teamwork.integrationproject.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Excel导入工具类
 */
public class ExcelImportUtils
{

    private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtils.class);
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读取excel文件，解析后返回
     * firstCellNum 从第几列开始读
     * lastCellNum  从第几列结束
     */
    public static List<List<String>> readExcel(MultipartFile file, int firstCellNum, int lastCellNum) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<List<String>> list = new ArrayList<List<String>>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();

                //取标题行   判断标题数量是否足够
                Row rows = sheet.getRow(0);
                //获得当前行的列数
                List<String> cellss = new ArrayList<>();
                int lastCellNum1 = rows.getLastCellNum();
                for (int cellNum = firstCellNum; cellNum < lastCellNum1; cellNum++) {
                    Cell cell = rows.getCell(cellNum);
                    cellss.add(getCellValue(cell));
                }
                if (cellss.stream().allMatch(StringUtils::isEmpty)) {
                    continue;
                }
                //如果少于所传固定列数 则提示excel列数据多或者少
                if (cellss.size()!= lastCellNum+1){
                    throw new RuntimeException("列数据异常，多或者少，请核对后再进行导入");
                }

                //循环除了第一行的所有行
                //为了过滤到第一行因为我的第一行是数据库的列
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    if (firstCellNum<0){
                        firstCellNum = row.getFirstCellNum();
                    }
                    if (lastCellNum<0){
                        lastCellNum = row.getLastCellNum();
                    }
                    //获得当前行的列数
                    List<String> cells = new ArrayList<>();
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum <= lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells.add(getCellValue(cell));
                    }
                    if (cells.stream().allMatch(StringUtils::isEmpty)) {
                        continue;
                    }
                    logger.info("data: "+cells.toString());
                    list.add(cells);
                }
            }
        }
        return list;
    }

    /**
     * 判断文件是否存在
     *
     * @throws IOException
     */
    private static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            throw new IOException(fileName + "不是excel文件");
        }
    }

    /**
     * 获取Excel的Workbook
     *
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    /**
     * 获取Excel单元格值
     *
     * @return
     */
    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellTypeEnum()) {
            //数字(日期时间也是数字类型)
            case NUMERIC:
                //如果是日期
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    cellValue = standardDateFormat(cell);
                } else {
                    //非日期类，把数字当成String来读，避免出现1读成1.0的情况
                    cell.setCellType(CellType.STRING);
                    cellValue = String.valueOf(cell.getStringCellValue());
                }
                break;
            //字符串
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //Boolean
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //公式
            case FORMULA:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //空值
            case BLANK:
                cellValue = "";
                break;
            //故障
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 将日期转换为标准时间字符串输出
     *
     * @param cell
     * @return
     */
    private static String standardDateFormat(Cell cell) {
        SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int dformat = cell.getCellStyle().getDataFormat();
        SimpleDateFormat tempDateFormat = null;
        if (Arrays.asList(14, 178, 192, 194, 208, 196, 210).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if (Arrays.asList(190, 191).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (Arrays.asList(177, 182, 185).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        } else if (Arrays.asList(183, 186).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("yyyy年MM月");
        } else if (Arrays.asList(183, 200, 201, 202, 203).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("HH:mm");
        } else if (Arrays.asList(204, 205, 206, 207, 208).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("HH时mm分");
        } else if (Arrays.asList(184, 187).contains(dformat)) {
            tempDateFormat = new SimpleDateFormat("MM月dd日");
        } else {
            tempDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        String format = tempDateFormat.format(cell.getDateCellValue());
        Date parse = null;
        try {
            parse = tempDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return standardDateFormat.format(parse);
    }
}
