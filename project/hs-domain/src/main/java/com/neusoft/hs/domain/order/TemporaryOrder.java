//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\TemporaryOrder.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Temporary")
public class TemporaryOrder extends Order {

	@Override
	public void updateState(OrderExecute orderExecute) {
		this.setState(Order.State_Finished);
		this.setStateDesc("已完成");
	}
}
