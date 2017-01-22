package com.neusoft.hs.application;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.InspectApply;
import com.neusoft.hs.domain.order.InspectResult;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestUtil {

	@Autowired
	private OrderDomainService orderDomainService;

	public void testInspectResult(String orderId) {

		Order order = orderDomainService.find(orderId);

		List<InspectResult> results = ((InspectApply) order.getApply())
				.getInspectResults();

		assertTrue(results.size() == 1);

	}

}
