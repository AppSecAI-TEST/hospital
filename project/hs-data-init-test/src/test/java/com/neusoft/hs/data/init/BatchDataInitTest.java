package com.neusoft.hs.data.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.data.batch.OrderBatchDataService;
import com.neusoft.hs.data.batch.OrgBatchDataService;
import com.neusoft.hs.data.batch.UserBatchDataService;
import com.neusoft.hs.data.batch.VisitBatchDataService;
import com.neusoft.hs.platform.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BatchDataInitTest {

	@Autowired
	private OrgBatchDataService orgBatchDataService;

	@Autowired
	private UserBatchDataService userBatchDataService;
	
	@Autowired
	private VisitBatchDataService visitBatchDataService;
	
	@Autowired
	private OrderBatchDataService orderBatchDataService;

	@Test
	public void init() throws HsException {
		orgBatchDataService.init();
		userBatchDataService.init();
		visitBatchDataService.init();
		orderBatchDataService.init();
	}
}
