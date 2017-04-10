package com.neusoft.hs.domain.inpatientdept;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitOutHospitalEvent;
import com.neusoft.hs.platform.bean.ApplicationContextUtil;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("OutHospitalBalance")
public class OutHospitalBalanceOrderExecute extends OrderExecute {

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		super.doFinish(params, user);

		Visit visit = this.getVisit();
		try {
			visit.balance(user);
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}

		// 发出患者出院事件
		ApplicationContextUtil.getApplicationContext().publishEvent(
				new VisitOutHospitalEvent(visit));
	}
}
