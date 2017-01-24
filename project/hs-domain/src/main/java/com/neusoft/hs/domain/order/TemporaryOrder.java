//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\TemporaryOrder.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.pharmacy.DrugOrderType;

@Entity
@DiscriminatorValue("Temporary")
public class TemporaryOrder extends Order {

	@Override
	public void updateState(OrderExecute orderExecute) {
		this.setState(Order.State_Finished);
		this.setStateDesc("已完成");
	}

	@Override
	public void resolve(DrugOrderType drugOrderType) throws OrderException {
		// 分解执行条目
		this.getUseMode().resolve(this, drugOrderType);
		if (this.getResolveOrderExecutes().size() == 0) {
			throw new OrderException(this, "没有分解出执行条目");
		}
		// 设置执行时间
		for (OrderExecute execute : this.getResolveOrderExecutes()) {
			execute.fillPlanDate(this.getPlanStartDate(),
					this.getPlanStartDate());
		}

	}
}
