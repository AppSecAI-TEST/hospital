package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.neusoft.hs.domain.treatment.ListTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;

@Entity
@DiscriminatorValue("TemporaryOrderList")
public class TemporaryOrderListTreatmentItemSpec extends TreatmentItemSpec {

	@ElementCollection
	@CollectionTable(name = "domain_treatment_spec_state", joinColumns = @JoinColumn(name = "spec_id"))
	@Column(name = "state")
	private List<String> states;

	@Override
	public TreatmentItem getTheItem(Visit visit) throws TreatmentException {
		
		if(this.states == null || this.states.size() == 0){
			throw new TreatmentException("临时医嘱列表没有配置过滤状态");
		}

		TreatmentItem item = new TreatmentItem();
		item.setTreatmentItemSpec(this);
		item.setVisit(visit);

		ListTreatmentItemValue value;

		Sort sort = new Sort(Direction.ASC, "planStartDate");
		PageRequest pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

		List<TemporaryOrder> orders = ApplicationContextUtil
				.getApplicationContext().getBean(OrderRepo.class)
				.findTemporaryOrder(visit, states, pageable);

		for (TemporaryOrder order : orders) {
			value = new ListTreatmentItemValue();

			value.putData("createDate", order.getCreateDate());
			value.putData("creator", order.getCreator().getName());
			value.putData("name", order.getName());
			value.putData("executeDate", order.getExecuteDate());

			item.addValue(value);
		}

		return item;
	}

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
		this.states = states;
	}

	public void addState(String state) {
		if (this.states == null) {
			this.states = new ArrayList<String>();
		}
		this.states.add(state);
	}

}
