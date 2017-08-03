package com.neusoft.hs.data.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.CostAdminDomainService;

@Service
public class ChargeItemBatchDataService {

	@Autowired
	private CostAdminDomainService costAdminDomainService;

	public final static int ChargeItemCount = 100;

	public void init() {

		List<ChargeItem> chargeItems = new ArrayList<ChargeItem>();

		ChargeItem chargeItem;

		for (int i = 0; i < ChargeItemCount; i++) {

			chargeItem = new ChargeItem();
			chargeItem.setId("chargeItem-test-" + i);
			chargeItem.setCode("chargeItem-code-" + i);
			chargeItem.setName("chargeItem-name-" + i);
			chargeItem.setPrice(20F);
			chargeItem.setChargingMode(ChargeItem.ChargingMode_Day);

			chargeItems.add(chargeItem);
		}
		costAdminDomainService.create(chargeItems);
	}

	public void clear() {
		for (int i = 0; i < ChargeItemCount; i++) {
			costAdminDomainService.delete("chargeItem-test-" + i);
		}
	}
}
