package com.neusoft.hs.domain.cost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostAdminDomainService {

	@Autowired
	private ChargeItemRepo chargeItemRepo;

	@Autowired
	private CostRecordRepo costRecordRepo;

	@Autowired
	private ChargeBillRepo chargeBillRepo;

	public void create(List<ChargeItem> chargeItems) {
		chargeItemRepo.save(chargeItems);
	}

	public void clearChargeItems() {
		chargeItemRepo.deleteAll();
	}

	public void clearCostRecords() {
		costRecordRepo.deleteAll();
	}

	public void clearChargeBill() {
		chargeBillRepo.deleteAll();
	}

}
