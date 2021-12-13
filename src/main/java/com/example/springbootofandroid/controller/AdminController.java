package com.example.springbootofandroid.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootofandroid.entity.Admin;
import com.example.springbootofandroid.entity.Student;
import com.example.springbootofandroid.entity.Time;
import com.example.springbootofandroid.service.AdminService;
import com.example.springbootofandroid.service.StudentService;
import com.example.springbootofandroid.service.TimeService;
import com.example.springbootofandroid.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vinside
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TimeService timeService;

    Result result = new Result();

    /*
    * 登陆验证
    * */
    @PostMapping("/login")
    public Result login(String json_admin){
        Admin admin = null;
        try {
            admin = JSON.parseObject(json_admin,Admin.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        Admin _admin = adminService.getOne(Wrappers.<Admin>lambdaQuery()
                                   .eq(Admin::getUsername,admin.getUsername())
                                   .eq(Admin::getPassword,admin.getPassword()));
        if (_admin != null){
            result.setSuccess("登陆成功！",JSON.toJSONString(_admin));
            return result;
        }
        result.setInfo("帐号或密码错误！",null);
        return result;
    }

    /*
    * 添加一个管理员
    * */
    @PostMapping("/save")
    public Result save(String json_admin){
        Admin admin = null;
        try {
            admin = JSON.parseObject(json_admin,Admin.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        admin.setUuid(IdUtil.simpleUUID());
        adminService.save(admin);
        result.setSuccess("添加成功",null);
        return result;
    }

    /*
    * 带条件的分页查询所有学生
    * */
    @GetMapping("/getAllBP")
    public IPage<Student> getAllByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search){
        return studentService.page(new Page<>(pageNum,pageSize),Wrappers.<Student>lambdaQuery().like(Student::getName,search));
    }

    /*
    * 查询所有学生，先根据日期降序，再根据签到时间升序（未签到的则显示在前面），最后根据学号升序
    * */
    @GetMapping("/getAll")
    public Result getAll(){
        result.setSuccess("",JSON.toJSONString(studentService.getAll()));
        return result;
    }

    /*
    * 添加一条学生信息
    * */
    @PostMapping("/saveStu")
    public Result saveStu(String json_student){
        Student student = null;
        try {
            student = JSON.parseObject(json_student,Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        student.setUuid(IdUtil.simpleUUID());
        student.setUsername(student.getNumber());
        student.setPassword(student.getNumber());
        studentService.save(student);
        result.setSuccess("添加成功",null);
        return result;
    }

    /*
    * 开启今日签到
    * */
    @PostMapping("/start")
    public Result start(){
        List<String> uuidList = studentService.getAllUuid();
        List<Time> timeList = new ArrayList<Time>();
        for (String uuid : uuidList) {
            Time time = new Time();
            time.setDate(DateUtil.date());
            time.setStudentUuid(uuid);
            timeList.add(time);
        }
        timeService.saveBatch(timeList);
        result.setSuccess("签到成功开启",null);
        return result;
    }

    /*
    * 查看某个学生的详情
    * */
    @GetMapping("/detail/{uuid}")
    public Result detail(@PathVariable String uuid){
        result.setSuccess("查询成功",JSON.toJSONString(studentService.getOne(uuid)));
        return result;
    }

    /*
    * 修改学生信息
    * */
    @PostMapping("/update")
    public Result update(String json_student){
        Student student = null;
        try {
            student = JSON.parseObject(json_student,Student.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        studentService.updateById(student);
        result.setSuccess("修改成功",null);
        return result;
    }

    /*
    * 删除学生信息，及其签到情况
    * */
    @PostMapping("/delete")
    public Result delete(@RequestParam String uuid){
        studentService.removeById(uuid);
        timeService.remove(Wrappers.<Time>lambdaQuery().eq(Time::getStudentUuid,uuid));
        result.setSuccess("删除成功!",null);
        return result;
    }

    /*
    * 先根据日期筛选，然后根据签到时间升序（未签到的则显示在前面），最后根据学号升序
    * */
    @GetMapping("/byDate")
    public Result byDate(String date){
        result.setSuccess("查询成功",JSON.toJSONString(studentService.getAllByDate(date)));
        return result;
    }

}

