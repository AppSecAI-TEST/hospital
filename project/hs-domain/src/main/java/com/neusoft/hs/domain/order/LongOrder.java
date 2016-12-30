//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\LongOrder.java

package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("Long")
public class LongOrder extends Order {

	@NotEmpty(message = "频次不能为空")
	@Column(name = "frequency_type", length = 32)
	private String frequencyType;

	@Column(name = "plan_end_date")
	private Date planEndDate;

	public static final int ResolveDays = 2;

	public static final String FrequencyType_Day = "每天";

	public String getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(String frequencyType) {
		this.frequencyType = frequencyType;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	@Override
	public void updateState() {
		// TODO Auto-generated method stub

	}

	public void stop() throws OrderExecuteException {
		for (OrderExecute execute : this.getOrderExecutes()) {
			execute.stop();
		}
		this.setState(State_Stoped);
		this.setPlanEndDate(DateUtil.getSysDate());
	}
}
