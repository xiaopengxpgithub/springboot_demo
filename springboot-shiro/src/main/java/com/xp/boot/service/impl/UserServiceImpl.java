package com.xp.boot.service.impl;

import com.xp.boot.mapper.UserMapper;
import com.xp.boot.pojo.User;
import com.xp.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUserByUserName(String userName) {
		return userMapper.queryUserByUserName(userName);
	}

}
