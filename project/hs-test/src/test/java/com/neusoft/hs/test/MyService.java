package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDAO;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.bean.MyBeanFactory;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class MyService {

	@Autowired
	@Qualifier(value="myDao")
	private OrderExecuteDAO orderExecuteDao1;
	
	@Autowired
	@Qualifier(value="myDao")
	private OrderExecuteDAO orderExecuteDaox;
	
	@Autowired
	private MyService0 myService0;

	private String id = "a2852102-eaf9-45f3-8caa-d55a77057606";

	public void testUpdate() throws HsException {

		OrderExecute orderExecute1 = orderExecuteDao1.find(id);
		//assertTrue(orderExecute1.getState().equals("1"));

//		OrderExecuteDAO orderExecuteDao = ApplicationContextUtil
//				.getApplicationContext().getBean(OrderExecuteDAO.class);
		
		OrderExecuteDAO orderExecuteDao = (OrderExecuteDAO)ApplicationContextUtil
				.getApplicationContext().getBean("myDao");
		
		OrderExecuteDAO orderExecuteDao3 = (OrderExecuteDAO) ApplicationContextUtil
		.getApplicationContext().getAutowireCapableBeanFactory().getBean("myDao");
		
		OrderExecuteDAO orderExecuteDao2 = (OrderExecuteDAO)MyBeanFactory.getBean("myDao");

		OrderExecute orderExecute2 = orderExecuteDao.find(id);

		orderExecute2.setStartDate(DateUtil.getSysDate());
		orderExecute2.setState("a");

		orderExecute2.save();

		OrderExecute orderExecute = orderExecuteDao1.find(id);

		assertTrue(orderExecute.getState().equals("a"));

	}
}
