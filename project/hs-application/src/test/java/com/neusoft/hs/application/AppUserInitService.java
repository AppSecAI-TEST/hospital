package com.neusoft.hs.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.application.user.User2Impl;
import com.neusoft.hs.application.user.UserService;

@Service
public class AppUserInitService {
	
	@Autowired
	private UserService userService;
	
	public void createUsers(){
		
		userService.clearUsers();
		
		User2Impl user;
		
		user = new User2Impl();

		user.setId("admin");
		user.setName("管理员用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("filler222");
		user.setName("填报用户222");
		user.setOrgId("org111");
		user.setDeptId("dept222");// 填报部门
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("filler333");
		user.setName("填报用户333");
		user.setOrgId("org111");
		user.setDeptId("dept333");// 填报部门
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("evaluater888");
		user.setName("考核用户888");
		user.setOrgId("org111");
		user.setDeptId("dept888");// 考核部门
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("auditFiller222");
		user.setName("填报审核用户222");
		user.setOrgId("org111");
		user.setDeptId("dept222");// 填报部门
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("auditFiller333");
		user.setName("填报审核用户333");
		user.setOrgId("org111");
		user.setDeptId("dept333");// 填报部门
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("admin");
		user.setName("管理员用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("admin");
		user.setName("管理员用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("admin");
		user.setName("管理员用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		
		userService.createUser(user);
		
		user = new User2Impl();

		user.setId("admin");
		user.setName("管理员用户");
		user.setOrgId("org111");
		user.setDeptId("dept111");
		
		userService.createUser(user);
		
	}

}
