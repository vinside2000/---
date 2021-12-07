package com.example.springbootofandroid.mapper;

import com.example.springbootofandroid.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vinside
 * @since 2021-11-30
 */
public interface StudentMapper extends BaseMapper<Student> {

    /*
    * 取得所有学生的uuid
    * */
    @Select("select uuid from student")
    List<String> getAllUuid();

    /*
    * 先根据日期降序，再根据签到时间升序（未签到的则显示在前面），最后根据学号升序
    * */
    @Select("select uuid,name,number,student_class,attendance_time,status from student s,time t where s.uuid = t.student_uuid order by date desc,attendance_time,number")
    List<Student> getAll();

    /*
    * 根据uuid查询一个学生
    * */
    @Select("select * from student where uuid = #{uuid}")
    Student getOne(String uuid);

    /*
    * 先根据日期筛选，然后根据签到时间升序（未签到的则显示在前面），最后根据学号升序
    * */
    @Select("select uuid,name,number,student_class,attendance_time,status from student s,time t where s.uuid = t.student_uuid and t.date = #{date} order by attendance_time,number")
    List<Student> getAllByDate(String date);
}
