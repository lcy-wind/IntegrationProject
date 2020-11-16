package com.teamwork.integrationproject.controller;

import com.teamwork.integrationproject.common.export.ExportTemplate;
//import com.teamwork.integrationproject.common.importExcel.ImportExcelUtil;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2020/10/21 11:00
 */
@RestController
@RequestMapping("v1")
public class ExportController {

    @Autowired
    private StudentService studentService;

    //针对表数据进行导出
    @PostMapping("/exportStudentList")
    public void exportStudentList(HttpServletRequest request, HttpServletResponse response) {
        List<StudentDto> studentDtoList = studentService.selectStudentList();
        List<Map<String, Object>> dataList = new ArrayList<>();
        studentDtoList.forEach(student -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",student.getName());
            map.put("grade",student.getGrade());
            map.put("age",student.getAge());
            dataList.add(map);
        });
        ExportTemplate.exportStudent(response,"学生表导出",dataList);
    }

//    @PostMapping("/ImportExcelUtiltest")
//    public void ImportExcelUtiltest(MultipartFile multipartFile,Class<?> clazz)
//    {
//        try
//        {
//            List<List<Object>> lists = ImportExcelUtil.importExcelMultipartFile(multipartFile, 1, 0, clazz);
//
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException("导入异常",e);
//        }
//    }

    @PostMapping("/ImportExcelUtiltest")
    public void importAlarmEvents(@RequestBody MultipartFile file) {
        Class<?> clazz = null;
        try {
            // 从Excel第一行起到最后一行结束,
            List<List<String>> excelData = ImportExcelUtil.readExcel(file);
            if (excelData.isEmpty()) {
                throw new RuntimeException("excel为空");
            }
//            //将Excel中数据，转为你的实体类
//            List<List<EventDTO>> alarmList = new ArrayList<>();
//            for (List<?> list : excelData) {
//                alarmList.add((List<EventDTO>) list);
//            }
//            Map<String, Object> res = eventService.importEvent(alarmList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}