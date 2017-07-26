package com.neusoft.hs.test;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.order.CompsiteOrder;
import com.neusoft.hs.domain.order.DrugOrderTypeApp;
import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.OrderCreateCommand;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Service
public class InPatientCompsiteOrderTestService extends InPatientTestService {

	@Override
	protected void treatment() throws HsException {

		Date sysDate;
		Date startDate;

		DateUtil.setSysDate(DateUtil.createMinute("2016-12-29 10:10", dayCount));

		// 创建药品002/003长期医嘱
		sysDate = DateUtil.getSysDate();
		startDate = DateUtil.getSysDateStart();

		// 创建药品002长期医嘱
		sysDate = DateUtil.getSysDate();
		startDate = DateUtil.getSysDateStart();

		LongOrder drug002Order = new LongOrder();
		drug002Order.setVisit(visit001);
		drug002Order.setName("头孢3");
		drug002Order.setCount(2);
		drug002Order.setFrequencyType(orderFrequencyType_9H15H);
		drug002Order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		drug002Order.setPlanStartDate(sysDate);
		drug002Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug002Order.setOrderType(drugOrderType002);

		drug002Order.setTypeApp(new DrugOrderTypeApp(deptbbb,
				infusionOrderUseModeToInPatient));

		orderAppService.create(drug002Order, user002);

		// 创建药品003长期医嘱
		LongOrder drug003Order = new LongOrder();
		drug003Order.setVisit(visit001);
		drug003Order.setName("5%葡萄糖");
		drug003Order.setCount(1);
		drug003Order.setFrequencyType(orderFrequencyType_9H15H);
		drug003Order.setPlaceType(OrderCreateCommand.PlaceType_InPatient);

		drug003Order.setPlanStartDate(sysDate);
		drug003Order.setPlanEndDate(DateUtil.addDay(sysDate, 2));

		drug003Order.setOrderType(drugOrderType003);

		drug003Order.setTypeApp(new DrugOrderTypeApp(deptbbb,
				infusionOrderUseModeToInPatient));

		orderAppService.create(drug003Order, user002);

		CompsiteOrder drug002003Order = new CompsiteOrder();
		drug002003Order.addOrder(drug002Order);
		drug002003Order.addOrder(drug003Order);

		orderAppService.compsite(drug002Order.getId(), drug003Order.getId(),
				user002);
	}

	@Override
	public void execute() throws HsException {

		this.createVisit001();

		this.createVisit004();

		this.intoWard();

		this.treatment();
	}

}
