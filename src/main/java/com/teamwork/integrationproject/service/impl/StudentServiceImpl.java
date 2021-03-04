package com.teamwork.integrationproject.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teamwork.integrationproject.actualCombatImport.DemoData;
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
import java.net.URLEncoder;
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
        List<StudentDto> studentDtos = new ArrayList<>();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("模板测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        try {
            PageHelper.startPage(1, 50);
            List<Student> students = studentMapper.selectList(null);
            PageInfo<Student> totalPage = new PageInfo<>(students);
            for (int i = 0; i <= totalPage.getPages(); i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(0, "模板1").head(StudentDto.class).build();
                PageHelper.startPage(i, 50);
                List<Student> studentList = studentMapper.selectStudentPage();
                PageInfo<Student> pageInfo = new PageInfo<>(studentList);
                List<Student> list = pageInfo.getList();
                studentDtos.addAll(list.stream().map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);
                    return studentDto;
                }).collect(Collectors.toList()));
                excelWriter.write(studentDtos, writeSheet);
                studentDtos.clear();
            }
            for (int i = 0; i <= totalPage.getPages(); i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(1, "模板2").head(StudentDto.class).build();
                PageHelper.startPage(i, 50);
                List<Student> studentList = studentMapper.selectStudentPage();
                PageInfo<Student> pageInfo = new PageInfo<>(studentList);
                List<Student> list = pageInfo.getList();
                studentDtos.addAll(list.stream().map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);
                    return studentDto;
                }).collect(Collectors.toList()));
                excelWriter.write(studentDtos, writeSheet);
                studentDtos.clear();
            }

        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        return null;
    }

        @Override
        public void addStudentList(List<Student> studentList) {
            studentMapper.addStudentList(studentList);
        }
    }
