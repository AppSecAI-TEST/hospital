package com.neusoft.hs.data.init;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neusoft.hs.data.batch.OrgBatchDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BatchDataClearTest{

	@Autowired
	private OrgBatchDataService orgBatchDataService;

	@Test
	public void clear() {
		orgBatchDataService.clear();
	}
}
