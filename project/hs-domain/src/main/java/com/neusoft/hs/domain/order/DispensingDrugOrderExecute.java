//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DispensingDrugOrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.context.annotation.Primary;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("DispensingDrug")
public class DispensingDrugOrderExecute extends OrderExecute {

	private int count;

	private float price;

	@Override
	public List<ChargeRecord> createChargeRecords() {
		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();

		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setCount(count);
		chargeRecord.setPrice(price);
		chargeRecord.setAmount(count * price);
		
		chargeRecords.add(chargeRecord);

		return chargeRecords;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
