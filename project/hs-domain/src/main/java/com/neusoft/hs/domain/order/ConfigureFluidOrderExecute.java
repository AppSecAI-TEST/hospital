package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("ConfigureFluid")
public class ConfigureFluidOrderExecute extends OrderExecute {

	private int count;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Override
	public List<ChargeRecord> createChargeRecords() {
		List<ChargeRecord> chargeRecords = super.createChargeRecords();

		for (ChargeRecord chargeRecord : chargeRecords) {
			chargeRecord.setCount(count);
		}

		return chargeRecords;
	}

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		try {
			drugType.send(count);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	@Override
	protected void doCancel() throws OrderExecuteException {
		try {
			drugType.unSend(count);
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

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}
}
