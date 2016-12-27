//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DispensingDrugOrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("DispensingDrug")
public class DispensingDrugOrderExecute extends OrderExecute {

	private int count;

	@Override
	public List<ChargeRecord> createChargeRecords() {
		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();

		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setCount(count);
		chargeRecord.setPrice(this.getChargeItem().getPrice());
		chargeRecord.setAmount(count * this.getChargeItem().getPrice());
		chargeRecord.setChargeItem(this.getChargeItem());

		chargeRecords.add(chargeRecord);

		return chargeRecords;
	}

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		DrugOrderType type = (DrugOrderType) this.getOrder().getType();
		try {
			type.getDrugType().send(count);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
