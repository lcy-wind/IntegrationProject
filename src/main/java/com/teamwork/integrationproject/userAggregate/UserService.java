package com.teamwork.integrationproject.userAggregate;

import com.teamwork.integrationproject.userAggregate.entiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class UserService
{

    @Autowired
    private IUserRepository userRepository;

    @Scheduled(cron = "0/1 * * * * *")
    public void test()
    {
        System.out.println( "hello word!");
    }

    public User selectUserId(int id)
    {
      return userRepository.selectById(id);
    }
}
