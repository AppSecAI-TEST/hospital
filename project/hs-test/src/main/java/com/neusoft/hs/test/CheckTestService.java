package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CheckTestService extends AppTestService {

	private Visit visit001;

	private Visit visit002;

	private Visit visit003;

	private Visit visit004;

	@Override
	public void execute() throws HsException {

		float balance;

		List<Order> orders;

		balance = this.visitDomainService.find(visit001.getId())
				.getChargeBill().getConsume();

		assertTrue(balance == 2235F);

		balance = this.visitDomainService.find(visit002.getId())
				.getChargeBill().getConsume();

		assertTrue(balance == 531F);

		balance = this.visitDomainService.find(visit003.getId())
				.getChargeBill().getConsume();

		assertTrue(balance == 7F);

		balance = this.visitDomainService.find(visit004.getId())
				.getChargeBill().getConsume();

		assertTrue(balance == 464F);

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		List<OrderExecute> orderExecutes;

		orderExecutes = orderExecuteDomainService.findByState(
				OrderExecute.State_Finished, pageable);

		assertTrue(orderExecutes.size() == 93);

		orderExecutes = orderExecuteDomainService.findByState(
				OrderExecute.State_Canceled, pageable);

		assertTrue(orderExecutes.size() == 4);

		orderExecutes = orderExecuteDomainService.findByState(
				OrderExecute.State_Stoped, pageable);

		assertTrue(orderExecutes.size() == 4);

		orderExecutes = orderExecuteDomainService.findByChargeState(
				OrderExecute.ChargeState_Charge, pageable);

		assertTrue(orderExecutes.size() == 49);

		orderExecutes = orderExecuteDomainService.findByChargeState(
				OrderExecute.ChargeState_BackCharge, pageable);

		assertTrue(orderExecutes.size() == 2);

		orderExecutes = orderExecuteDomainService.findByChargeState(
				OrderExecute.ChargeState_NoCharge, pageable);

		assertTrue(orderExecutes.size() == 5);

		orders = orderAdminDomainService.findAll(pageable);

		assertTrue(orders.size() == 19);

		orders = orderDomainService.find(visit001, pageable);

		assertTrue(orders.size() == 12);

	}

	public Visit getVisit001() {
		return visit001;
	}

	public void setVisit001(Visit visit001) {
		this.visit001 = visit001;
	}

	public Visit getVisit002() {
		return visit002;
	}

	public void setVisit002(Visit visit002) {
		this.visit002 = visit002;
	}

	public Visit getVisit003() {
		return visit003;
	}

	public void setVisit003(Visit visit003) {
		this.visit003 = visit003;
	}

	public Visit getVisit004() {
		return visit004;
	}

	public void setVisit004(Visit visit004) {
		this.visit004 = visit004;
	}

}
