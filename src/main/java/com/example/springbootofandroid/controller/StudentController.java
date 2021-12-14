package com.example.springbootofandroid.controller;


import cn.hutool.Hutool;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springbootofandroid.entity.Admin;
import com.example.springbootofandroid.entity.Student;
import com.example.springbootofandroid.entity.Time;
import com.example.springbootofandroid.service.StudentService;
import com.example.springbootofandroid.service.TimeService;
import com.example.springbootofandroid.utils.Result;
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

    Result result = new Result();

    /*
    * 签到
    * */
    @PostMapping("/attendance")
    public Result attendance(@RequestParam String uuid){
        String date = DateUtil.today();
        Time time = timeService.getOne(Wrappers.<Time>lambdaQuery().eq(Time::getStudentUuid,uuid)
                .eq(Time::getDate, DateUtil.parse(date))
                .eq(Time::getStatus,0));

        if (time != null){
            time.setAttendanceTime(DateUtil.date());
            time.setStatus(1);
            timeService.updateById(time);
            result.setSuccess("签到成功！",JSON.toJSONString(time));
            return result;
        }
        result.setInfo("签到失败！",null);
        return result;
    }

    /*
     * 登陆验证
     * */
    @PostMapping("/login")
    public Result login(String json){
        Student student = null;
        try {
            student = JSON.parseObject(json,Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        Student _student = studentService.check(student.getUsername(),student.getPassword());
        if (_student != null){
            result.setSuccess("登陆成功！",JSON.toJSONString(_student));
            return result;
        }
        result.setInfo("帐号或密码错误！",null);
        return result;
    }

    /*
     * 修改信息
     * */
    @PostMapping("/update")
    public Result update(String json){
        Student student = null;
        try {
            student = JSON.parseObject(json,Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        studentService.updateById(student);
        result.setSuccess("修改成功",null);
        return result;
    }
}

