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
import com.neusoft.hs.domain.order.OrderExecuteDomainService;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostDomainService {

	@Autowired
	private ChargeItemRepo chargeItemRepo;

	@Autowired
	private CostRecordRepo costRecordRepo;

	@Autowired
	private ChargeBillRepo chargeBillRepo;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private OrderExecuteDomainService orderExecuteDomainService;

	public List<Visit> getNeedInitAccount(Pageable pageable) {
		return visitDomainService.findByState(Visit.State_NeedInitAccount,
				pageable);
	}

	/**
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(Visit visit, float balance,
			AbstractUser user) throws HsException {
		ChargeBill chargeBill = visit.initAccount(balance, user);
		return chargeBill;
	}

	public ChargeRecord addCost(Visit visit, float balance, AbstractUser user)
			throws HsException {

		if (!visit.isInitedAccount()) {
			throw new HsException("visitName=[" + visit.getName() + "]未初始化账户");
		}

		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setAmount(balance);
		chargeRecord.setCreateDate(DateUtil.getSysDate());
		chargeRecord.setHaveCost(false);
		chargeRecord.setChargeDept(user.getDept());

		ChargeBill chargeBill = visit.getChargeBill();
		chargeBill.addChargeRecord(chargeRecord);
		if (chargeBill.getChargeMode()
				.equals(ChargeBill.ChargeMode_NoPreCharge)) {
			chargeBill.setChargeMode(ChargeBill.ChargeMode_PreCharge);
		}
		chargeBill.setBalance(chargeBill.getBalance() + balance);

		return chargeRecord;
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

		// 生成收费项目
		List<ChargeRecord> chargeRecords = execute.createChargeRecords();
		if (chargeRecords.size() > 0) {
			Date sysDate = DateUtil.getSysDate();
			boolean haveCharge = false;
			// 设置数据
			for (ChargeRecord chargeRecord : chargeRecords) {
				chargeRecord.setOrderExecute(execute);
				chargeRecord.setCreateDate(sysDate);
				chargeRecord.setChargeDept(execute.getExecuteDept());

				if (chargeRecord.isHaveCharge()) {
					haveCharge = true;
				}
			}
			// 生成费用记录
			execute.getVisit().getChargeBill().charging(chargeRecords);
			// 修改执行条目状态
			if (haveCharge) {
				execute.setChargeState(OrderExecute.ChargeState_Charge);
			}
			// 记录成本
			List<CostRecord> costRecords = new ArrayList<CostRecord>();
			CostRecord costRecord;
			for (ChargeRecord chargeRecord : chargeRecords) {
				costRecord = chargeRecord.createCostRecord();
				if (costRecord != null) {
					costRecords.add(costRecord);
				}
			}
			if (costRecords.size() > 0) {
				costRecordRepo.save(costRecords);
				execute.setCostState(OrderExecute.CostState_Cost);
			}
		}
	}

	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		return orderExecuteDomainService.getNeedBackChargeOrderExecutes(user,
				pageable);
	}

	/**
	 * @param user
	 * @param executeId
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(String executeId, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException {

		OrderExecute execute = orderExecuteDomainService.find(executeId);
		if (execute == null) {
			throw new OrderExecuteException(null, "executeId=[" + executeId
					+ "]不存在");
		}

		List<ChargeRecord> chargeRecords = execute.getChargeRecords();
		execute.getVisit().getChargeBill().unCharging(chargeRecords);
		execute.setChargeState(OrderExecute.ChargeState_BackCharge);

		if (isBackCost) {
			execute.setCostState(OrderExecute.CostState_NoCost);
			for (ChargeRecord chargeRecord : chargeRecords) {
				chargeRecord.getCostRecord().setState(CostRecord.State_Back);
			}
		}
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

	public void clearCostRecords() {
		costRecordRepo.deleteAll();
	}

	public void clearChargeBill() {
		chargeBillRepo.deleteAll();
	}

}
