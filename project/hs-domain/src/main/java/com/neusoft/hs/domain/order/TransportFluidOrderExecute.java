package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.pharmacy.DrugType;

@Entity
@DiscriminatorValue("TransportFluid")
public class TransportFluidOrderExecute extends OrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Override
	public List<ChargeRecord> createChargeRecords() {
		if (drugType.getDrugTypeSpec().isTransportFluidCharge()) {
			return super.createChargeRecords();
		} else {
			return new ArrayList<ChargeRecord>();
		}
	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}

}
