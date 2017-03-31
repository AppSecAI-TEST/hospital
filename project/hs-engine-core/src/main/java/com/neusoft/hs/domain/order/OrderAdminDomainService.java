package com.neusoft.hs.domain.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private AssistMaterialRepo assistMaterialRepo;

	@Autowired
	private PrescriptionRepo prescriptionRepo;

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

	public void clearPrescriptions() {
		prescriptionRepo.deleteAll();
	}

	public void clearOrderFrequencyTypes() {
		orderFrequencyTypeRepo.deleteAll();
	}

	public void createAssistMaterials(List<AssistMaterial> assistMaterials) {
		assistMaterialRepo.save(assistMaterials);
	}

}
