package com.neusoft.hs.domain.order;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.visit.Visit;

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

	public List<Order> find(Visit visit, Pageable pageable) {
		List<Order> orders = orderRepo.findByVisit(visit, pageable);

		for (Order order : orders) {
			Hibernate.initialize(order.getOrderType());
		}

		return orders;
	}

	public List<OrderType> findOrderType(Pageable pageable) {
		return orderTypeRepo.findAll(pageable).getContent();
	}

	public List<OrderFrequencyType> findFrequencyType(Pageable pageable) {
		return orderFrequencyTypeRepo.findAll(pageable).getContent();
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
