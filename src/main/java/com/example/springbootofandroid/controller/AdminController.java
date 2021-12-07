package com.example.springbootofandroid.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootofandroid.entity.Admin;
import com.example.springbootofandroid.entity.Student;
import com.example.springbootofandroid.entity.Time;
import com.example.springbootofandroid.service.AdminService;
import com.example.springbootofandroid.service.StudentService;
import com.example.springbootofandroid.service.TimeService;
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

    /*
    * 登陆验证
    * */
    @PostMapping("/login")
    public Boolean login(Admin admin){
        if (adminService.getOne(Wrappers.<Admin>lambdaQuery()
                        .eq(Admin::getUsername,admin.getUsername())
                        .eq(Admin::getPassword,admin.getPassword()))!=null)
            return true;
        return false;
    }

    /*
    * 添加一个管理员
    * */
    @PostMapping("/save")
    public void save(Admin admin){
        admin.setUuid(IdUtil.simpleUUID());
        adminService.save(admin);
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
    public List<Student> getAll(){
        return studentService.getAll();
    }

    /*
    * 添加一条学生信息
    * */
    @PostMapping("/saveStu")
    public void save(Student student){
        student.setUuid(IdUtil.simpleUUID());
        student.setUsername(student.getNumber());
        student.setPassword(student.getNumber());
        studentService.save(student);
    }

    /*
    * 开启今日签到
    * */
    @PostMapping("/start")
    public void start(){
        List<String> uuidList = studentService.getAllUuid();
        List<Time> timeList = new ArrayList<Time>();
        for (String uuid : uuidList) {
            Time time = new Time();
            time.setDate(DateUtil.date());
            time.setStudentUuid(uuid);
            timeList.add(time);
        }

        timeService.saveBatch(timeList);
    }

    /*
    * 查看某个学生的详情
    * */
    @GetMapping("/detail/{uuid}")
    public Student detail(@PathVariable String uuid){
        return studentService.getOne(uuid);
    }

    /*
    * 修改学生信息
    * */
    @PostMapping("/update")
    public void update(Student student){
        studentService.updateById(student);
    }

    /*
    * 删除学生信息，及其签到情况
    * */
    @PostMapping("/delete")
    public void delete(@RequestParam String uuid){
        studentService.removeById(uuid);
        timeService.remove(Wrappers.<Time>lambdaQuery().eq(Time::getStudentUuid,uuid));
    }

    /*
    * 先根据日期筛选，然后根据签到时间升序（未签到的则显示在前面），最后根据学号升序
    * */
    @GetMapping("/byDate")
    public List<Student> byDate(String date){
        return studentService.getAllByDate(date);
    }

}

