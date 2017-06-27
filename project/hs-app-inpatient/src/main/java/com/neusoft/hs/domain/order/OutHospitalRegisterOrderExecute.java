package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.OrderStopedEvent;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitOutWardedEvent;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("OutHospitalRegister")
public class OutHospitalRegisterOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

		Visit visit = this.getVisit();

		for (Order order : visit.getOrders()) {
			if (!this.getOrder().getId().equals(order.getId())) {
				if (order.getState().equals(Order.State_Executing)) {
					if (order instanceof LongOrder) {
						((LongOrder) order).stop();
						// 发出停止长嘱事件
						ApplicationContextUtil.getApplicationContext()
								.publishEvent(new OrderStopedEvent(visit));
					} else {
						throw new OrderExecuteException(this,
								"医嘱[%s]状态处于执行中，不能办理出院登记", order.getName());
					}
				}
			}
		}
		try {
			visit.leaveWard(user);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}

		// 发出患者离开病房事件
		ApplicationContextUtil.getApplicationContext().publishEvent(
				new VisitOutWardedEvent(visit));
	}

}
