package com.example.springbootofandroid.service.impl;

import com.example.springbootofandroid.entity.Student;
import com.example.springbootofandroid.mapper.StudentMapper;
import com.example.springbootofandroid.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinside
 * @since 2021-11-30
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public List<String> getAllUuid() {
        return studentMapper.getAllUuid();
    }

    @Override
    public List<Student> getAll() {
        return studentMapper.getAll();
    }

    @Override
    public Student getOne(String uuid) {
        return studentMapper.getOne(uuid);
    }

    @Override
    public List<Student> getAllByDate(String date) {
        return studentMapper.getAllByDate(date);
    }

    @Override
    public Student check(String username, String password) {
        return studentMapper.check(username,password);
    }

}
