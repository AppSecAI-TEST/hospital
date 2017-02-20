package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.treatment.ListTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;

@Entity
@DiscriminatorValue("TemporaryOrderList")
public class TemporaryOrderListTreatmentItemSpec extends TreatmentItemSpec {

	@Override
	public TreatmentItem getTheItem(Visit visit) {

		TreatmentItem item = new TreatmentItem();
		item.setTreatmentItemSpec(this);
		item.setVisit(visit);

		List<ListTreatmentItemValue> values = new ArrayList<ListTreatmentItemValue>();

		ListTreatmentItemValue value;

		List<TemporaryOrder> orders = ApplicationContextUtil
				.getApplicationContext().getBean(OrderRepo.class)
				.findTemporaryOrderByVisit(visit);

		for (TemporaryOrder order : orders) {
			value = new ListTreatmentItemValue();
			
			value.putData("createDate", order.getCreateDate());
			value.putData("creator", order.getCreator());
			value.putData("name", order.getName());
			//value.putData("executeDate", order.gete);

			item.addValue(value);

			values.add(value);
		}

		return item;
	}
}
