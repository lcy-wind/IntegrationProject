package com.teamwork.integrationproject.userAggregate;

import com.teamwork.integrationproject.userAggregate.entiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService
{

    @Autowired
    private IUserRepository userRepository;

    public String test()
    {
        return "hello word!";
    }

    public User selectUserId(int id)
    {
      return userRepository.selectById(id);
    }
}
