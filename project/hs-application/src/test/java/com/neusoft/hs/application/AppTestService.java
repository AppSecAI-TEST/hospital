package com.neusoft.hs.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.application.cashier.CashierAppService;
import com.neusoft.hs.application.register.RegisterAppService;
import com.neusoft.hs.domain.Application;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Org;
import com.neusoft.hs.domain.organization.OrganizationDomainService;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.organization.Unit;
import com.neusoft.hs.domain.organization.UserDomainService;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class AppTestService {

	@Autowired
	private RegisterAppService registerAppService;

	@Autowired
	private CashierAppService cashierAppService;

	@Autowired
	private OrganizationDomainService organizationDomainService;

	@Autowired
	private UserDomainService userDomainService;

	@Autowired
	private VisitDomainService visitDomainService;

	private Org org;// 哈医大二院
	private Dept dept111;// 住院处
	private Dept dept222;// 收费处
	private Dept dept333;// 药房
	private Dept dept000;// 内泌五

	private Staff user111;// 住院处送诊人-曹操
	private Staff user222;// 收费处-张飞
	private Staff user333;// 药房摆药岗位-赵云
	private Staff user001;// 内泌五接诊护士-大乔
	private Doctor user002;// 内泌五医生-貂蝉
	private Nurse user003;// 内泌五护士-小乔

	private Visit visit001;

	public void testInit() {
		// 初始化Context
		ApplicationContext applicationContext = SpringApplication
				.run(Application.class);
		ApplicationContextUtil.setApplicationContext(applicationContext);

		clear();

		initData();
	}

	public void clear() {

		// 清空患者一次住院
		visitDomainService.clear();
		// 清空用户信息
		userDomainService.clear();
		// 清空组织机构信息
		organizationDomainService.clear();

	}

	@Transactional(rollbackFor = Exception.class)
	public void initData() {

		initOrgs();

		initUsers();

	}

	private void initOrgs() {

		List<Unit> units = new ArrayList<Unit>();

		org = new Org();
		org.setId("org000");
		org.setName("哈医大二院");

		units.add(org);

		dept111 = new Dept();
		dept111.setId("dept111");
		dept111.setName("住院处");
		dept111.setParent(org);

		units.add(dept111);

		dept222 = new Dept();
		dept222.setId("dept222");
		dept222.setName("收费处");
		dept222.setParent(org);

		units.add(dept222);

		dept333 = new Dept();
		dept333.setId("dept333");
		dept333.setName("药房");
		dept333.setParent(org);

		units.add(dept333);

		dept000 = new Dept();
		dept000.setId("dept000");
		dept000.setName("内泌五");
		dept000.setParent(org);

		units.add(dept000);

		organizationDomainService.create(units);
	}

	private void initUsers() {

		List<AbstractUser> users = new ArrayList<AbstractUser>();

		user111 = new Staff();

		user111.setId("staff111");
		user111.setName("住院处送诊人-曹操");
		user111.setDept(new Dept("dept111"));

		users.add(user111);

		user222 = new Staff();

		user222.setId("staff222");
		user222.setName("收费处-张飞");
		user222.setDept(new Dept("dept222"));

		users.add(user222);

		user333 = new Staff();

		user333.setId("staff333");
		user333.setName("药房摆药岗位-赵云");
		user333.setDept(new Dept("dept333"));

		users.add(user333);

		user001 = new Staff();

		user001.setId("staff001");
		user001.setName("内泌五接诊护士-大乔");
		user001.setDept(new Dept("dept000"));

		users.add(user001);

		user002 = new Doctor();

		user002.setId("doctor002");
		user002.setName("内泌五医生-貂蝉");
		user002.setDept(new Dept("dept000"));

		users.add(user002);

		user003 = new Nurse();

		user003.setId("nurse003");
		user003.setName("内泌五护士-小乔");
		user003.setDept(new Dept("dept000"));

		users.add(user003);

		userDomainService.create(users);
	}

	/**
	 * 
	 * @throws HsException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void execute() throws HsException {

		// 创建测试患者
		visit001 = new Visit();
		visit001.setName("测试患者001");
		visit001.setRespDept(dept000);
		visit001.setRespDoctor(user002);
		// 送诊
		registerAppService.register(visit001, user111);

		cashierAppService.initAccount(visit001.getId(), 2000F, user222);

	}

}
