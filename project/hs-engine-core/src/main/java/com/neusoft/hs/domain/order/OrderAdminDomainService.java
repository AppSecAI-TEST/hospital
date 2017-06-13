package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderAdminDomainService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderTypeRepo orderTypeRepo;

	@Autowired
	private OrderTypeAppRepo orderTypeAppRepo;

	@Autowired
	private CompsiteOrderRepo compsiteOrderRepo;

	@Autowired
	private OrderFrequencyTypeRepo orderFrequencyTypeRepo;

	public List<Order> findAll(Pageable pageable) {
		return orderRepo.findAll(pageable).getContent();
	}

	public void createOrderTypes(List<OrderType> orderTypes) {
		orderTypeRepo.save(orderTypes);
	}

	public void createOrderFrequencyTypes(
			List<OrderFrequencyType> orderFrequencyTypes) {
		orderFrequencyTypeRepo.save(orderFrequencyTypes);
	}

	public void clearOrders() {
		orderRepo.deleteAll();
	}

	public void clearOrderTypes() {
		orderTypeRepo.deleteAll();
	}

	public void clearOrderTypeApps() {
		orderTypeAppRepo.deleteAll();
	}

	public void clearCompsiteOrdes() {
		compsiteOrderRepo.deleteAll();
	}

	public void clearOrderFrequencyTypes() {
		orderFrequencyTypeRepo.deleteAll();
	}
}
