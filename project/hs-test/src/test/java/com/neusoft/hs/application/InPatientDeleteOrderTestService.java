package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.pharmacy.DrugOrderType;
import com.neusoft.hs.domain.pharmacy.DrugOrderTypeApp;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class InPatientDeleteOrderTestService extends InPatientTestService {

	@Override
	protected void treatment() throws HsException {

		Pageable pageable;
		List<OrderExecute> executes;
		List<Order> orders;
		int startedCount;
		int resolveCount;
		Date sysDate;

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 10:55", dayCount));

		// 开立药品临时医嘱
		Order drug001Order = new TemporaryOrder();
		drug001Order.setVisit(visit001);
		drug001Order.setName("药品001");
		drug001Order.setPlanStartDate(DateUtil.getSysDate());
		drug001Order.setCount(2);

		DrugOrderType drugOrderType = new DrugOrderType();
		drugOrderType.setDrugTypeSpec(drugTypeSpec001);

		drug001Order.setTypeApp(new DrugOrderTypeApp(drugOrderType,
				oralOrderUseMode));

		orderAppService.create(drug001Order, user002);

		orderAppService.delete(drug001Order.getId(), user002);

		pageable = new PageRequest(0, 15);
		orders = orderAppService.getNeedVerifyOrders(user003, pageable);

		assertTrue(orders.size() == 0);

		// DateUtil.setSysDate(DateUtil.createMinute("2016-12-28 11:00"));
		//
		// // 核对医嘱
		// for (Order order : orders) {
		// orderAppService.verify(order.getId(), user003);
		// }
	}

	@Override
	public void execute() throws HsException {

		this.intoWard();

		this.treatment();
	}

}
