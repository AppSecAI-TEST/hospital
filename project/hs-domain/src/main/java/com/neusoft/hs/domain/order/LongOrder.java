//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\LongOrder.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Column(name = "end_date")
	public Date endDate;

	public static final int ResolveDays = 2;

	public static final String FrequencyType_Day = "每天";

	public static final String FrequencyType_9H15H = "每天2次/早9/下3";

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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void updateState(OrderExecute orderExecute) {
		if (orderExecute.isLast()) {
			this.setState(Order.State_Finished);
			this.setStateDesc("已完成");
		}
	}

	public void stop() throws OrderExecuteException {
		for (OrderExecute execute : this.getOrderExecutes()) {
			execute.stop();
		}
		this.setState(State_Stoped);
		this.setEndDate(DateUtil.getSysDate());
	}

	/*
	 * 以天为单位计算频次对应的时间
	 */
	public List<Date> calExecuteDates(int numDays) {
		List<Date> dates = new ArrayList<Date>();
		// 分解的日期
		Date sysDateStart = DateUtil.getSysDateStart();
		Date currentDate = DateUtil.addDay(sysDateStart, numDays);
		// 如果已分解完毕就不再分解
		OrderExecute lastOrderExecute = this.getLastOrderExecute();
		if (lastOrderExecute != null) {
			if (lastOrderExecute.isLast()
					|| lastOrderExecute.getPlanStartDate().after(currentDate)) {
				return dates;
			}
		}

		Date date;
		// 分解
		if (frequencyType.equals(LongOrder.FrequencyType_9H15H)) {

			date = DateUtil.addHour(currentDate, 9);

			if (date.before(this.planEndDate)
					&& date.after(this.getPlanStartDate())) {
				dates.add(date);
			}

			date = DateUtil.addHour(currentDate, 15);

			if (date.before(this.planEndDate)
					&& date.after(this.getPlanStartDate())) {
				dates.add(date);
			}
		}
		return dates;
	}
}
