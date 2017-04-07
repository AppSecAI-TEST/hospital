package com.neusoft.hs.domain.inpatientdept;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;

@Entity
@DiscriminatorValue("Nursing")
public class NursingOrderType extends OrderType {

	@NotEmpty(message = "护理类型不能为空")
	@Column(name = "nursing_type", length = 32)
	private String nursingType;

	@Override
	public void resolveOrder(OrderTypeApp orderTypeApp) {

		Order order = orderTypeApp.getOrder();
		// 分解两天的护理执行条目
		LongOrder longOrder = (LongOrder) order;
		for (int day = 0; day < LongOrder.ResolveDays; day++) {
			// 计算执行时间
			List<Date> executeDates = longOrder.calExecuteDates(day);

			for (Date executeDate : executeDates) {
				OrderExecuteTeam executeTeam = this.create(order, executeDate);
				// 设置执行时间
				for (OrderExecute execute : executeTeam.getExecutes()) {
					execute.fillPlanDate(executeDate, executeDate);
				}
				// 收集执行条目
				order.addExecuteTeam(executeTeam);
			}
		}

	}

	private OrderExecuteTeam create(Order order, Date startDate) {

		NursingOrderExecute execute = new NursingOrderExecute();

		OrderExecuteTeam team = new OrderExecuteTeam();
		team.addOrderExecute(execute);

		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(nursingType);
		execute.addChargeItem(this.getChargeItem());

		execute.setExecuteDept(order.getBelongDept());
		execute.setChargeDept(order.getBelongDept());
		execute.setState(OrderExecute.State_NeedExecute);
		execute.setChargeState(OrderExecute.ChargeState_NoCharge);
		execute.setCostState(OrderExecute.CostState_NoCost);

		return team;
	}

	public String getNursingType() {
		return nursingType;
	}

	public void setNursingType(String nursingType) {
		this.nursingType = nursingType;
	}

}
