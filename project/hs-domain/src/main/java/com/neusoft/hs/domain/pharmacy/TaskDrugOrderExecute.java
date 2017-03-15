//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DispensingDrugOrderExecute.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("TaskDrug")
public class TaskDrugOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		Order order = this.getOrder();
		if (order instanceof TemporaryOrder) {
			TemporaryOrder temporaryOrder = (TemporaryOrder) order;
			temporaryOrder.setExecuteDate(DateUtil.getSysDate());
			temporaryOrder.setExecuteUser(user);
		}
	}

}
