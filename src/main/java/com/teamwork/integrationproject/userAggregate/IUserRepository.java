package com.teamwork.integrationproject.userAggregate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.integrationproject.userAggregate.entiy.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends BaseMapper<User>
{

}
