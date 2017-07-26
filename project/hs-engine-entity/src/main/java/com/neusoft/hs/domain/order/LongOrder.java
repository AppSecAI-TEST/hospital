//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\LongOrder.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 长期医嘱条目
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Long")
public class LongOrder extends Order {

	@NotNull(message = "频次不能为空")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "frequency_type_id")
	private OrderFrequencyType frequencyType;

	@Column(name = "plan_end_date")
	private Date planEndDate;

	@Column(name = "end_date")
	public Date endDate;

	public static final String Category = "L";

	/**
	 * 住院长嘱自动分解的天数，1为今天，2为今明两天。。。。
	 */
	public static final int ResolveDays = 2;

	public OrderFrequencyType getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(OrderFrequencyType frequencyType) {
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
			this.setEndDate(DateUtil.getSysDate());
			this.setStateDesc("已完成");
		}
	}

	/**
	 * 停止长嘱
	 * 
	 * @throws OrderExecuteException
	 */
	public void stop() throws OrderExecuteException {
		for (OrderExecute execute : this.getOrderExecutes()) {
			execute.stop();
		}
		this.setState(State_Stoped);
		this.setEndDate(DateUtil.getSysDate());
	}

	/**
	 * 以天为单位计算频次对应的时间
	 * 
	 * @param numDays
	 * @return
	 */
	public List<Date> calExecuteDates(int numDays) {
		// 分解的日期
		Date sysDateStart = DateUtil.getSysDateStart();
		Date currentDate = DateUtil.addDay(sysDateStart, numDays);
		Visit visit = (Visit) this.getVisit();
		// 如果分解时间大于患者计划出院时间将不分解医嘱
		if (visit.getPlanLeaveWardDate() != null
				&& (visit.getPlanLeaveWardDate().compareTo(currentDate) == 0 || visit
						.getPlanLeaveWardDate().before(currentDate))) {
			return new ArrayList<Date>();
		}
		// 如果已分解完毕就不再分解
		OrderExecute lastOrderExecute = this.getLastOrderExecute();
		if (lastOrderExecute != null) {
			if (lastOrderExecute.isLast()
					|| lastOrderExecute.getPlanStartDate().compareTo(
							currentDate) == 0
					|| lastOrderExecute.getPlanStartDate().after(currentDate)) {
				return new ArrayList<Date>();
			}
		}

		// 分解
		return frequencyType.calExecuteDates(this, currentDate);
	}

	@Override
	public void compsiteMatch(Order order) throws OrderException {
		super.compsiteMatch(order);

		LongOrder longOrder = (LongOrder) order;

		if (this.planEndDate == null && longOrder.planEndDate != null) {
			throw new OrderException(this, "计划结束时间不同");
		}

		if (this.planEndDate != null && longOrder.planEndDate == null) {
			throw new OrderException(this, "计划结束时间不同");
		}

		if (this.planEndDate != null && longOrder.planEndDate != null
				&& !this.planEndDate.equals(longOrder.planEndDate)) {
			throw new OrderException(this, "计划结束时间不同");
		}

		if (!this.frequencyType.equals(longOrder.frequencyType)) {
			throw new OrderException(this, "频次不同");
		}
	}

	@Override
	protected void setCategory(OrderExecute orderExecute) {
		orderExecute.setOrderCategory(Category);
	}
}
