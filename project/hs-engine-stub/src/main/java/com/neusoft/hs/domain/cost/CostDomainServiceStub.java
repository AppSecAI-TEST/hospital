package com.neusoft.hs.domain.cost;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.Staff;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class CostDomainServiceStub implements CostDomainService {

	@Override
	public List<Visit> getNeedInitAccount(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeBill createChargeBill(Visit visit, float balance,
			AbstractUser user) throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChargeRecord addCost(Visit visit, float balance, AbstractUser user)
			throws HsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createVisitChargeItem(Visit visit, ChargeItem item,
			Date startDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public ChargeResult charging(OrderExecute execute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unCharging(OrderExecute execute, boolean isBackCost, Nurse nurse)
			throws OrderExecuteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrderExecute> getNeedBackChargeOrderExecutes(Staff user,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(List<ChargeItem> chargeItems) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearChargeItems() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearCostRecords() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearChargeBill() {
		// TODO Auto-generated method stub

	}

}
