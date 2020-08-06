package com.teamwork.integrationproject.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teamwork.integrationproject.entity.TUser;
import com.teamwork.integrationproject.mapper.TUserMapper;

import com.teamwork.integrationproject.service.ITUserService;
import com.teamwork.integrationproject.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private TUserMapper tUserMapper;
    //mybatis-plus基本用法
    @Override
    public Result insertInto(TUser tUser) {
        int i= tUserMapper.insert(tUser);
        if (i > 0) {
            return  Result.success();
        } else {
            return Result.error("失败");
        }
    }

    @Override
    public List<TUser> qryList() {
        QueryWrapper<TUser> t = new QueryWrapper<>();
        List<TUser> tUsers = tUserMapper.selectList(t);
        return tUsers;
    }
}
