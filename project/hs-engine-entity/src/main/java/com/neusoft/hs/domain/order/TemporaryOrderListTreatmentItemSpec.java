package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.neusoft.hs.domain.treatment.ListTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemDAO;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("TemporaryOrderList")
public class TemporaryOrderListTreatmentItemSpec extends TreatmentItemSpec {

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "domain_treatment_spec_state", joinColumns = @JoinColumn(name = "spec_id"))
	@Column(name = "state")
	private List<String> states;

	@Override
	public TreatmentItem getTheItem(Visit visit) throws TreatmentException {

		TreatmentItem treatmentItem = this.getService(TreatmentItemDAO.class)
				.findTheTreatmentItem(visit, this);
		if (treatmentItem == null) {
			treatmentItem = this.createTreatmentItem(visit);
			this.getService(TreatmentItemDAO.class).save(treatmentItem);
		}

		return treatmentItem;
	}

	public TreatmentItem createTreatmentItem(Visit visit)
			throws TreatmentException {

		if (this.states == null || this.states.size() == 0) {
			throw new TreatmentException("临时医嘱列表没有配置过滤状态");
		}

		TreatmentItem treatmentItem = new TreatmentItem();
		treatmentItem.setTreatmentItemSpec(this);
		treatmentItem.setVisit(visit);

		ListTreatmentItemValue value;

		Sort sort = new Sort(Direction.ASC, "planStartDate");
		PageRequest pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

		List<TemporaryOrder> orders = ApplicationContextUtil
				.getApplicationContext().getBean(OrderRepo.class)
				.findTemporaryOrder(visit, states, pageable);

		try {
			for (TemporaryOrder order : orders) {
				value = new ListTreatmentItemValue();

				value.putData("name", order.getName());
				value.putData("count", order.getCount() == null ? null : order
						.getCount().toString());
				value.putData("executeDate",
						DateUtil.toString(order.getExecuteDate()));
				value.putData("creator", order.getCreator().getName());
				value.putData("createDate",
						DateUtil.toString(order.getCreateDate()));

				treatmentItem.addValue(value);
			}
		} catch (HsException e) {
			throw new TreatmentException(e);
		}

		return treatmentItem;
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
