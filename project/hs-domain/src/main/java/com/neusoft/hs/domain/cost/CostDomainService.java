//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\CostDomainService.java

package com.neusoft.hs.domain.cost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostDomainService {

	@Autowired
	private ChargeItemRepo chargeItemRepo;

	@Autowired
	private CostRecordRepo costRecordRepo;
	
	@Autowired
	private VisitDomainService visitDomainService;

	public List<Visit> getNeedInitAccount(Pageable pageable) {
		return visitDomainService.findByState(Visit.State_NeedInitAccount,
				pageable);
	}

	/**
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(String visitId, float balance,
			AbstractUser user) throws HsException {
		Visit visit = visitDomainService.find(visitId);
		if (visit == null) {
			throw new HsException("visitId=[" + visitId + "]不存在");
		}

		return visit.initAccount(balance, user);
	}

	/**
	 * @param visit
	 * @roseuid 584E2630028C
	 */
	public void createVisitChargeItem(Visit visit, ChargeItem item,
			Date startDate) {

		VisitChargeItem visitChargeItem = new VisitChargeItem();

		visitChargeItem.setChargeItem(item);
		visitChargeItem.setVisit(visit);
		visitChargeItem.setState(VisitChargeItem.State_Normal);
		visitChargeItem.setStartDate(startDate);

		visitChargeItem.save();
	}

	/**
	 * @param execute
	 * @roseuid 584FBC02036D
	 */
	public void charging(OrderExecute execute) {

		List<ChargeRecord> chargeRecords = execute.createChargeRecords();

		execute.getVisit().getChargeBill().charging(chargeRecords);

		execute.setChargeState(OrderExecute.ChargeState_Charge);

		List<CostRecord> costRecords = new ArrayList<CostRecord>();
		for (ChargeRecord chargeRecord : chargeRecords) {
			costRecords.add(chargeRecord.createCostRecord());
		}
		costRecordRepo.save(costRecords);
		
		execute.setCostState(OrderExecute.CostState_Cost);

	}

	/**
	 * @roseuid 5850BD170158
	 */
	public void unCharging() {

	}

	/**
	 * @roseuid 58533C8201B8
	 */
	public void balance() {

	}

	public void create(List<ChargeItem> chargeItems) {
		chargeItemRepo.save(chargeItems);
	}

	public void clearChargeItems() {
		chargeItemRepo.deleteAll();
	}

}
