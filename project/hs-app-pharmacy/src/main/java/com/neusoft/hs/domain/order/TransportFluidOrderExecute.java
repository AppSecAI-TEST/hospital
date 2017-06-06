package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.cost.ChargeRecord;

@Entity
@DiscriminatorValue("TransportFluid")
public class TransportFluidOrderExecute extends DrugOrderExecute {

	@Override
	public List<ChargeRecord> createChargeRecords() {
		if (this.getDrugType().getDrugTypeSpec().isTransportFluidCharge()) {
			return super.createChargeRecords();
		} else {
			return new ArrayList<ChargeRecord>();
		}
	}
}
