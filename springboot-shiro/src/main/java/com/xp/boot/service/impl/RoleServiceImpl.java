package com.xp.boot.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xp.boot.mapper.RoleMapper;
import com.xp.boot.pojo.Role;
import com.xp.boot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List<String> queryRolesByUserId(Integer userId) {
		List<Role> list = this.roleMapper.queryRolesByUserId(userId);
		List<String> data=new ArrayList<>();
		for (Role role : list) {
			data.add(role.getRolename());
		}
		return data;
	}
}
