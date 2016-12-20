package com.neusoft.hs.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.domain.Application;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationDomainService;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.Unit;
import com.neusoft.hs.domain.organization.UserDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.user.User;
import com.neusoft.hs.platform.user.UserImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class AppTestService {

	@Autowired
	private RegisterAppService registerAppService;

	public static void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);

		clear();

		initData();
	}

	public static void clear() {

		// 清空组织机构信息
		ApplicationContextUtil.getApplicationContext()
				.getBean(OrganizationDomainService.class).clear();

	}

	public static void initData() {

		initOrgs();

	}

	private static void initOrgs() {

		List<Unit> units = new ArrayList<Unit>();

		Org org = new Org();
		org.setId("org000");
		org.setName("哈医大二院");

		units.add(org);

		Dept dept111 = new Dept();
		dept111.setId("dept111");
		dept111.setName("住院处");
		dept111.setParent(org);

		units.add(dept111);

		Dept dept222 = new Dept();
		dept222.setId("dept222");
		dept222.setName("收费处");
		dept222.setParent(org);

		units.add(dept222);

		Dept dept333 = new Dept();
		dept333.setId("dept333");
		dept333.setName("药房");
		dept333.setParent(org);

		units.add(dept333);

		Dept dept000 = new Dept();
		dept000.setId("dept000");
		dept000.setName("内泌五");
		dept000.setParent(org);

		units.add(dept000);

		ApplicationContextUtil.getApplicationContext()
				.getBean(OrganizationDomainService.class).create(units);
	}

	private static void initUsers() {

		List<AbstractUser> users = new ArrayList<AbstractUser>();

		Staff user111 = new Staff();
		
		user111.setId("staff111");
		user111.setName("住院处送诊人-曹操");
		user111.setDept(new Dept("dept111"));

		users.add(user111);
		
		Staff user222 = new Staff();
		
		user222.setId("staff222");
		user222.setName("收费处-张飞");
		user222.setDept(new Dept("dept222"));

		users.add(user222);

		ApplicationContextUtil.getApplicationContext()
				.getBean(UserDomainService.class).create(users);
	}

	/**
	 * 
	 * @throws HsException
	 */
	public void execute() {

		User registerUser111 = new UserImpl();
		registerUser111.setId("user111");
		registerUser111.setName("住院处送诊用户111");
		registerUser111.setOrgId("org000");
		registerUser111.setDeptId("dept111");

		Visit visit = new Visit();
		visit.setName("测试患者aaa");
		// visit.setRespDept("dept222");

		registerAppService.register(visit, registerUser111);

	}

}
