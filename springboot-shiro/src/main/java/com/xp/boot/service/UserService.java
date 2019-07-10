package com.xp.boot.service;


import com.xp.boot.pojo.User;

public interface UserService {

	/**
	 * 根据用户名查询用户
	 */
	User queryUserByUserName(String userName);
}
