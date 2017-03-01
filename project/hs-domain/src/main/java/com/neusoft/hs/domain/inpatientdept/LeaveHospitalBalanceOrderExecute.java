package com.neusoft.hs.domain.inpatientdept;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.InPatientVisit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("LeaveHospitalBalance")
public class LeaveHospitalBalanceOrderExecute extends OrderExecute {

	@Override
	public List<ChargeRecord> createChargeRecords() {

		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();

		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setAmount(-this.getVisit().getChargeBill().getBalance());
		chargeRecord.setHaveCost(false);
		chargeRecord.setHaveCharge(false);

		chargeRecords.add(chargeRecord);

		return chargeRecords;

	}

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		super.doFinish(user);

		InPatientVisit visit = (InPatientVisit)this.getVisit();
		try {
			visit.balance(user);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}

		Order order = this.getOrder();
		if (order instanceof TemporaryOrder) {
			TemporaryOrder temporaryOrder = (TemporaryOrder) order;
			temporaryOrder.setExecuteDate(DateUtil.getSysDate());
			temporaryOrder.setExecuteUser(user);
		}
	}
}
