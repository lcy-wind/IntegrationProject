package com.teamwork.integrationproject.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.teamwork.integrationproject.entity.TUser;
import com.teamwork.integrationproject.utils.Result;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haoChaoJie
 * @since 2020-08-05
 */

public interface ITUserService extends IService<TUser> {

    Result insertInto (TUser tUser);
}
