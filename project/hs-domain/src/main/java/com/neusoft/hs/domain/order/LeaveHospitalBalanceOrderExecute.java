package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

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

		Visit visit = this.getVisit();
		try {
			visit.balance(user);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}
}
