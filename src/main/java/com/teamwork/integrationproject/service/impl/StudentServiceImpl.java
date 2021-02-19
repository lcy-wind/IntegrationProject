package com.teamwork.integrationproject.service.impl;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teamwork.integrationproject.dto.StudentDto;
import com.teamwork.integrationproject.entity.Student;
import com.teamwork.integrationproject.mapper.StudentMapper;
import com.teamwork.integrationproject.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public void kkk()
    {
        Student student = studentMapper.selectById(1);
        System.out.println(student);
    }

    @Override
    public List<StudentDto> selectStudentList()
    {
        List<Student> students = studentMapper.selectList(null);
        List<StudentDto> studentDtoList= new ArrayList<>();
        //集合判断
        if (!CollectionUtils.isEmpty(students)){
            studentDtoList = students.stream().map(student -> {
                StudentDto studentDto = new StudentDto();
                //属性拷贝
                BeanUtils.copyProperties(student, studentDto);
                return studentDto;
            }).collect(Collectors.toList());
        }
        return studentDtoList;
    }

    @Override
    public List<StudentDto> selectStudentListPage(HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            // 设置EXCEL名称
            String fileName = new String(("SystemExcel").getBytes(), "UTF-8");
            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("系统列表sheet1");
            Table table = new Table(0);
            List<List<String>> titles = new ArrayList<List<String>>();
            titles.add(Arrays.asList("系统名称"));
            titles.add(Arrays.asList("系统标识"));
            titles.add(Arrays.asList("状态"));
            table.setHead(titles);
            List<StudentDto> studentDtos = new ArrayList<>();
            PageHelper.startPage(1, 50);
            List<Student> students = studentMapper.selectList(null);
            PageInfo<Student> totalPage = new PageInfo<>(students);
            for (int i = 0; i <= totalPage.getPages(); i++) {
                PageHelper.startPage(i, 50);
                List<Student> studentList = studentMapper.selectStudentPage();
                PageInfo<Student> pageInfo = new PageInfo<>(studentList);
                List<Student> list = pageInfo.getList();
                studentDtos.addAll(list.stream().map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);
                    return studentDto;
                }).collect(Collectors.toList()));
                writer.write0(studentDtos, sheet, table);
                System.out.println("-----------------------------");
                studentDtos.clear();
            }
            // 下载EXCEL
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO-8859-1") + ".xlsx");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void addStudentList(List<Student> studentList) {
        studentMapper.addStudentList(studentList);
    }
}
