package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("SecondNursing")
public class SecondNursingOrderType extends OrderType {

	@Override
	public List<OrderExecute> resolveOrder(Order order) {

		List<OrderExecute> executes = new ArrayList<OrderExecute>();
		// 分解两天的护理执行条目
		OrderExecute lastExecute = order.getLastOrderExecute();
		if (lastExecute == null) {
			OrderExecute execute = this.create(order, order.getPlanStartDate());
			executes.add(execute);
			lastExecute = execute;
		}

		Date needResolveDate = DateUtil.addDay(DateUtil.getSysDate(),
				LongOrder.ResolveDays);
		while (lastExecute.getPlanEndDate().before(needResolveDate)) {
			OrderExecute execute = this.create(order,
					lastExecute.getPlanEndDate());
			executes.add(execute);
			lastExecute = execute;
		}
		return executes;
	}

	private OrderExecute create(Order order, Date startDate) {

		OrderExecute execute = new OrderExecute();

		execute.setId(UUID.randomUUID().toString());
		execute.setTeamId(UUID.randomUUID().toString());

		execute.setOrder(order);
		execute.setVisit(order.getVisit());
		execute.setBelongDept(order.getBelongDept());
		execute.setType(OrderExecute.Type_SecondNursing);
		execute.setChargeItem(this.getChargeItem());

		execute.setExecuteDept(order.getBelongDept());
		execute.setState(OrderExecute.State_NeedExecute);
		execute.setChargeState(OrderExecute.ChargeState_NoCharge);
		execute.setCostState(OrderExecute.CostState_NoCost);

		execute.setPlanStartDate(startDate);
		execute.setPlanEndDate(DateUtil.addDay(startDate, 1));

		return execute;
	}

}