package com.neusoft.hs.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.inspect.InspectApply;
import com.neusoft.hs.domain.inspect.InspectResult;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderDomainService;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestUtil {

	@Autowired
	private OrderDomainService orderDomainService;

	@Autowired
	private PharmacyDomainService pharmacyDomainService;

	public void testInspectResult(String orderId, int count) {

		Order order = orderDomainService.find(orderId);

		List<InspectResult> results = ((InspectApply) order.getApply())
				.getInspectResults();

		assertTrue(results.size() == count);

	}

	public DrugType getDrugType(DrugType drugType) {
		return pharmacyDomainService.findTheDrugType(drugType.getId());
	}

}
