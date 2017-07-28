package com.neusoft.hs.data.init;

import java.util.Random;

import com.neusoft.hs.data.batch.OrgBatchDataService;
import com.neusoft.hs.data.batch.UserBatchDataService;

public class TempTest {

	public static void main(String[] args) {
		Random random = new Random();
		
		for (int i = 0; i < UserBatchDataService.UserCount; i++) {
			System.out.println(random.nextInt(OrgBatchDataService.DeptCount - 1));
		}
	}

}
