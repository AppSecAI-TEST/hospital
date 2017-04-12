package com.neusoft.hs.test.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecuteDAO;

@Service
@Transactional(rollbackFor = Exception.class)
public class MyService0 {
	
	@Autowired
	private OrderExecuteDAO orderExecuteDaoy;

}
