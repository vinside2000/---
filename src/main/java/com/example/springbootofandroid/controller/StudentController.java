package com.example.springbootofandroid.controller;


import cn.hutool.Hutool;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springbootofandroid.entity.Admin;
import com.example.springbootofandroid.entity.Student;
import com.example.springbootofandroid.entity.Time;
import com.example.springbootofandroid.service.StudentService;
import com.example.springbootofandroid.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinside
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TimeService timeService;

    /*
    * 签到
    * */
    @PostMapping("/attendance")
    public String attendance(@RequestParam String uuid){
        String date = DateUtil.today();
        Time time = timeService.getOne(Wrappers.<Time>lambdaQuery().eq(Time::getStudentUuid,uuid)
                .eq(Time::getDate, DateUtil.parse(date))
                .eq(Time::getStatus,0));

        if (time != null){
            time.setAttendanceTime(DateUtil.date());
            time.setStatus(1);
            timeService.updateById(time);
            return "签到成功";
        }
        return "签到失败";
    }

    /*
     * 登陆验证
     * */
    @PostMapping("/login")
    public Boolean login(Student student){
        if (studentService.getOne(Wrappers.<Student>lambdaQuery()
                .eq(Student::getUsername,student.getUsername())
                .eq(Student::getPassword,student.getPassword()))!=null)
            return true;
        return false;
    }

    /*
     * 修改信息
     * */
    @PostMapping("/update")
    public void update(Student student){
        studentService.updateById(student);
    }
}

