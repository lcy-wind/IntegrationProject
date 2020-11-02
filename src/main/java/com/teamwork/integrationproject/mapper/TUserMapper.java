package com.teamwork.integrationproject.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.integrationproject.entity.TUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haoChaoJie
 * @since 2020-08-05
 */
@Repository
public interface TUserMapper extends BaseMapper<TUser> {

    @Select("select * from t_user where u_username = '张三'")
    TUser selectUser();
}
