package com.teamwork.integrationproject.userAggregate;

import com.teamwork.integrationproject.common.props.ApiProperties;
import com.teamwork.integrationproject.userAggregate.entiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class UserService
{

    @Autowired
    private IUserRepository userRepository;

    public User selectUserId(int id)
    {
      return userRepository.selectById(id);
    }
}
