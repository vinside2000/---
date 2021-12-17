package com.example.springbootofandroid.service;

import com.example.springbootofandroid.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootofandroid.entity.Time;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author vinside
 * @since 2021-11-30
 */
public interface StudentService extends IService<Student> {

    List<String> getAllUuid();

    List<Student> getAll();

    Student getOne(Serializable uuid);

    List<Student> getAllByDate(Serializable date);

    Student check(String username, String password);

    Time getAttendance(Serializable uuid,Serializable date);

    List<Student> getStu();
}
