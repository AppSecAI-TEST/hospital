package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteDAO;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class MyService {

	@Autowired
	private OrderExecuteDAO orderExecuteDao1;

	@Autowired
	private OrderExecuteDAO orderExecuteDaox;

	@Autowired
	private MyService0 myService0;

	@Autowired
	private ApplicationContext applicationContext;

	private String id = "402812f55b426f81015b427057d10017";

	public void testUpdate() throws HsException {

		OrderExecute orderExecute1 = orderExecuteDao1.find(id);
		// assertTrue(orderExecute1.getState().equals("1"));

		// OrderExecuteDAO orderExecuteDao = ApplicationContextUtil
		// .getApplicationContext().getBean(OrderExecuteDAO.class);

		OrderExecuteDAO orderExecuteDao = (OrderExecuteDAO) ApplicationContextUtil
				.getApplicationContext().getBean("orderExecuteDAO");

		OrderExecute orderExecute2 = orderExecuteDao.find(id);

		orderExecute2.setStartDate(DateUtil.getSysDate());
		orderExecute2.setState("b");

		orderExecute2.save();

		OrderExecute orderExecute = orderExecuteDao1.find(id);

		assertTrue(orderExecute.getState().equals("b"));

	}
}
