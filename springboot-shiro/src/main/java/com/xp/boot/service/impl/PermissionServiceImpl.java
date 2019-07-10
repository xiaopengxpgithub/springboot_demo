package com.xp.boot.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xp.boot.mapper.PermissionMapper;
import com.xp.boot.pojo.Permission;
import com.xp.boot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public List<String> queryPermissionByUserId(Integer userId) {
		List<Permission> list = permissionMapper.queryPermissionsByUserId(userId);
		List<String> data=new ArrayList<>();
		
		for (Permission permission : list) {
			data.add(permission.getPercode());
		}
		return data;
	}
}
