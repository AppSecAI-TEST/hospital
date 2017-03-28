package com.neusoft.hs.domain.inpatientdept;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;
import com.neusoft.hs.platform.util.DateUtil;

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
				OrderExecute execute = this.create(order, executeDate);
				// 设置执行时间
				execute.fillPlanDate(executeDate, executeDate);
				// 收集执行条目
				order.addExecute(execute);
			}
		}

	}

	private SecondNursingOrderExecute create(Order order, Date startDate) {

		SecondNursingOrderExecute execute = new SecondNursingOrderExecute();

		execute.setId(UUID.randomUUID().toString());
		execute.setTeamId(UUID.randomUUID().toString());

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

		return execute;
	}

}
