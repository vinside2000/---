package com.example.springbootofandroid.service.impl;

import com.example.springbootofandroid.entity.Time;
import com.example.springbootofandroid.mapper.TimeMapper;
import com.example.springbootofandroid.service.TimeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vinside
 * @since 2021-11-30
 */
@Service
public class TimeServiceImpl extends ServiceImpl<TimeMapper, Time> implements TimeService {

}
