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
import com.neusoft.hs.platform.log.LogUtil;
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
	 * 创建病历夹
	 * 
	 * @throws HsException
	 * @roseuid 584DFD470092
	 */
	public ChargeBill createChargeBill(Visit visit, float balance,
			AbstractUser user) throws HsException {
		ChargeBill chargeBill = visit.initAccount(balance, user);
		return chargeBill;
	}

	/**
	 * 预存费用
	 * 
	 * @param visit
	 * @param balance
	 * @param user
	 * @return
	 * @throws HsException
	 */
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
		chargeRecord.setType(ChargeRecord.Type_PreCharge);

		ChargeBill chargeBill = visit.getChargeBill();
		chargeBill.addChargeRecord(chargeRecord);
		if (chargeBill.getChargeMode()
				.equals(ChargeBill.ChargeMode_NoPreCharge)) {
			chargeBill.setChargeMode(ChargeBill.ChargeMode_PreCharge);
		}
		chargeBill.setBalance(chargeBill.getBalance() + balance);

		LogUtil.log(this.getClass(), "用户[{}]给账户[{}]续费{}", user.getId(),
				chargeBill.getId(), balance);

		return chargeRecord;
	}

	/**
	 * 创建自动收费项目
	 * 
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

		LogUtil.log(this.getClass(), "系统给患者一次就诊[{}]增加收费项目{}", visit.getName(),
				item.getId());
	}

	/**
	 * 根据医嘱执行条目创建费用条目
	 * 
	 * @param execute
	 * @roseuid 584FBC02036D
	 */
	public ExecuteResult charging(OrderExecute execute) {

		ExecuteResult result = new ExecuteResult();
		result.setExecuteId(execute.getId());
		// 生成收费项目
		List<ChargeRecord> chargeRecords = execute.createChargeRecords();
		if (chargeRecords.size() > 0) {
			Date sysDate = DateUtil.getSysDate();
			// 处理费用
			if (!execute.getChargeState().equals(
					OrderExecute.ChargeState_Charge)) {

				Float amount = 0F;
				// 设置数据
				for (ChargeRecord chargeRecord : chargeRecords) {
					chargeRecord.setOrderExecute(execute);
					chargeRecord.setCreateDate(sysDate);
					chargeRecord.setChargeDept(execute.getChargeDept());
					chargeRecord.setBelongDept(execute.getBelongDept());
					chargeRecord.setType(ChargeRecord.Type_ShouldCharge);
				}
				// 生成费用记录
				amount = execute.getVisit().getChargeBill()
						.charging(chargeRecords);
				// 修改执行条目状态
				if (execute.getChargeState().equals(
						OrderExecute.ChargeState_NoCharge)) {
					execute.setChargeState(OrderExecute.ChargeState_Charge);
				}

				result.setCharge(amount);
				LogUtil.log(this.getClass(), "系统:医嘱执行条目[{}]产生费用{}",
						execute.getId(), amount);
			}
			// 记录成本
			if (execute.getCostState().equals(OrderExecute.CostState_NoCost)) {

				Float amount = 0F;

				List<CostRecord> costRecords = new ArrayList<CostRecord>();
				CostRecord costRecord;
				for (ChargeRecord chargeRecord : chargeRecords) {
					costRecord = chargeRecord.createCostRecord();
					if (costRecord != null) {
						costRecords.add(costRecord);
					}
					amount += costRecord.getCost();
				}
				if (costRecords.size() > 0) {
					costRecordRepo.save(costRecords);
					execute.setCostState(OrderExecute.CostState_Cost);
				}

				result.setCost(amount);
				LogUtil.log(this.getClass(), "系统:医嘱执行条目[{}]产生成本{}",
						execute.getId(), amount);
			}

		}

		return result;
	}

	/**
	 * 取消医嘱执行条目对应的费用条目
	 * 
	 * @param user
	 * @param execute
	 * @throws OrderExecuteException
	 * @roseuid 5850BC9B0098
	 */
	public void unCharging(OrderExecute execute, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException {

		List<ChargeRecord> chargeRecords = execute.getChargeRecords();
		Float amount = execute.getVisit().getChargeBill()
				.unCharging(chargeRecords);
		execute.setChargeState(OrderExecute.ChargeState_BackCharge);

		if (isBackCost) {
			CostRecord costRecord;
			execute.setCostState(OrderExecute.CostState_NoCost);
			for (ChargeRecord chargeRecord : chargeRecords) {
				costRecord = chargeRecord.getCostRecord();
				if (costRecord != null) {
					costRecord.setState(CostRecord.State_Back);
				}
			}
		}

		LogUtil.log(this.getClass(), "护士[{}]将医嘱执行条目[{}]产生的费用{}撤回",
				nurse.getId(), execute.getId(), amount);
	}

	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		return orderExecuteDomainService.getNeedBackChargeOrderExecutes(user,
				pageable);
	}

	/**
	 * @roseuid 58533C8201B8
	 */
	private void balance() {

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
