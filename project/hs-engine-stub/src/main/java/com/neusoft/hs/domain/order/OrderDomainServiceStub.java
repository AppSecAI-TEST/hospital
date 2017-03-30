package com.neusoft.hs.domain.order;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
@Service
public class OrderDomainServiceStub implements OrderDomainService {

	@Override
	public List<Order> create(OrderCreateCommand orderCommand, Doctor doctor)
			throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getNeedVerifyOrders(Nurse nurse, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order verify(Order order, Nurse nurse) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int resolve() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cancel(Order order, Doctor doctor) throws OrderException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Order order, Doctor doctor) throws OrderException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Prescription> findPrescriptions(Visit visit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prescription findThePrescription(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order find(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Order> find(List<String> orderIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrderTypes(List<OrderType> orderTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createOrderFrequencyTypes(
			List<OrderFrequencyType> orderFrequencyTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearOrders() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearOrderTypes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearOrderTypeApps() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearCompsiteOrdes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearPrescriptions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearOrderFrequencyTypes() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createAssistMaterials(List<AssistMaterial> assistMaterials) {
		// TODO Auto-generated method stub

	}

}
