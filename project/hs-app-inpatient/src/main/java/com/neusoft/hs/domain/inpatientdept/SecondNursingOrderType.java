package com.neusoft.hs.domain.inpatientdept;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteTeam;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;

@Entity
@DiscriminatorValue("SecondNursing")
public class SecondNursingOrderType extends OrderType {

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

		SecondNursingOrderExecute execute = new SecondNursingOrderExecute();

		OrderExecuteTeam team = new OrderExecuteTeam();
		team.addOrderExecute(execute);

		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_SecondNursing);
		execute.addChargeItem(this.getChargeItem());

		execute.setExecuteDept(order.getBelongDept());
		execute.setChargeDept(order.getBelongDept());
		execute.setState(OrderExecute.State_NeedExecute);
		execute.setChargeState(OrderExecute.ChargeState_NoCharge);
		execute.setCostState(OrderExecute.CostState_NoCost);

		return team;
	}

}
