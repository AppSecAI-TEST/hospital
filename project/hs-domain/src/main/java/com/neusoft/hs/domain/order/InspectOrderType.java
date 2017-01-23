package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Inspect")
public class InspectOrderType extends OrderType {

	@Override
	public void resolveOrder(Order order) throws OrderException {

		InspectApply inspectApply = (InspectApply) order.getApply();
		for (InspectApplyItem inspectApplyItem : inspectApply
				.getInspectApplyItems()) {
			OrderExecuteTeam team = new OrderExecuteTeam();
			// 安排检查
			InspectArrangeOrderExecute arrange = new InspectArrangeOrderExecute();
			arrange.setOrder(order);
			arrange.setVisit(order.getVisit());
			arrange.setBelongDept(order.getBelongDept());
			arrange.setType(OrderExecute.Type_Arrange_Inspect);
			arrange.setInspectApplyItem(inspectApplyItem);

			arrange.setPlanStartDate(order.getPlanStartDate());
			arrange.setPlanEndDate(order.getPlanStartDate());

			arrange.setExecuteDept(inspectApplyItem.getInspectDept());
			arrange.setState(OrderExecute.State_NeedSend);
			arrange.setChargeState(OrderExecute.ChargeState_NoCharge);
			arrange.setCostState(OrderExecute.CostState_NoCost);

			team.addOrderExecute(arrange);

			// 完成检查
			InspectConfirmOrderExecute confirm = new InspectConfirmOrderExecute();
			confirm.setOrder(order);
			confirm.setVisit(order.getVisit());
			confirm.setBelongDept(order.getBelongDept());
			confirm.setType(OrderExecute.Type_Confirm_Inspect);
			confirm.setInspectApplyItem(inspectApplyItem);

			confirm.setExecuteDept(inspectApplyItem.getInspectDept());
			confirm.setState(OrderExecute.State_NeedExecute);
			confirm.setChargeState(OrderExecute.ChargeState_NoCharge);
			confirm.setCostState(OrderExecute.CostState_NoCost);

			confirm.addChargeItem(inspectApplyItem.getInspectItem()
					.getChargeItem());

			team.addOrderExecute(confirm);

			order.addExecutes(team.getExecutes());
		}
	}
}
