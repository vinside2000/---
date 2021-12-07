package com.example.springbootofandroid.service.impl;

import com.example.springbootofandroid.entity.Admin;
import com.example.springbootofandroid.mapper.AdminMapper;
import com.example.springbootofandroid.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
