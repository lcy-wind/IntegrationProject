package com.teamwork.integrationproject.actualCombatImport;

import com.alibaba.excel.EasyExcel;
import com.teamwork.integrationproject.utils.log.LogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2021/1/5 11:36
 */
@RestController
@RequestMapping("/v1")
public class DemoController {

    // 如果使用 spring管理  必须在数据入参时
    // 要标记为spring管理的类  否则插入会报空指针   一定要加！！！
    @Autowired
    private DemoDAO demoDAO;

    @PostMapping("impExcel")
    public String actualCombatImport(MultipartFile file) throws IOException {
        long start = System.currentTimeMillis();
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener(demoDAO)).sheet().doRead();
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000);
        return "success";
    }
}
