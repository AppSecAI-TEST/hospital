package com.neusoft.hs.data.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.data.batch.ChargeItemBatchDataService;
import com.neusoft.hs.data.batch.OrgBatchDataService;
import com.neusoft.hs.data.batch.UserBatchDataService;
import com.neusoft.hs.data.batch.VisitBatchDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BatchDataClearTest {

	@Autowired
	private OrgBatchDataService orgBatchDataService;

	@Autowired
	private UserBatchDataService userBatchDataService;

	@Autowired
	private ChargeItemBatchDataService chargeItemBatchDataService;

	@Autowired
	private VisitBatchDataService visitBatchDataService;

	@Test
	public void clear() {
		visitBatchDataService.clear();
		chargeItemBatchDataService.clear();
		userBatchDataService.clear();
		orgBatchDataService.clear();
	}
}
