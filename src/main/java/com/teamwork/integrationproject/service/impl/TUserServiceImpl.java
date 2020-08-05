package com.teamwork.integrationproject.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teamwork.integrationproject.entity.TUser;
import com.teamwork.integrationproject.mapper.TUserMapper;

import com.teamwork.integrationproject.service.ITUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haoChaoJie
 * @since 2020-08-05
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
