//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\PharmacyDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.visit.Visit;

@Service
@Transactional(rollbackFor = Exception.class)
public class PharmacyDomainService {

	@Autowired
	private DrugTypeSpecRepo drugTypeSpecRepo;

	@Autowired
	private PharmacyRepo pharmacyRepo;

	@Autowired
	private DrugTypeRepo drugTypeRepo;

	@Autowired
	private DrugUseModeRepo orderUseModeRepo;

	@Autowired
	private DrugUseModeAssistMaterialRepo orderUseModeAssistMaterialRepo;
	
	@Autowired
	private PrescriptionRepo prescriptionRepo;

	public List<DrugType> findByDrugTypeSpec(DrugTypeSpec drugTypeSpec) {
		return drugTypeRepo.findByDrugTypeSpec(drugTypeSpec);
	}

	public void createDrugTypeSpecs(List<DrugTypeSpec> drugTypeSpecs) {
		drugTypeSpecRepo.save(drugTypeSpecs);
	}

	public void clearDrugTypeSpecs() {
		drugTypeSpecRepo.deleteAll();
	}

	public void createPharmacys(List<Pharmacy> pharmacys) {
		pharmacyRepo.save(pharmacys);
	}

	public void clearPharmacys() {
		pharmacyRepo.deleteAll();
	}

	public void createDrugTypes(List<DrugType> drugTypes) {
		drugTypeRepo.save(drugTypes);
	}

	public void clearDrugTypes() {
		drugTypeRepo.deleteAll();
	}

	public DrugTypeSpec findDrugTypeSpec(String drugTypeSpecId) {
		return drugTypeSpecRepo.findOne(drugTypeSpecId);
	}

	public void clearOrderUseModes() {
		orderUseModeRepo.deleteAll();
	}

	public void createOrderUseModeAssistMaterials(
			List<DrugUseModeAssistMaterial> orderUseModeAssistMaterials) {
		orderUseModeAssistMaterialRepo.save(orderUseModeAssistMaterials);
	}

	public void createOrderUseModeAssistMaterial(
			DrugUseModeAssistMaterial orderUseModeAssistMaterial) {
		orderUseModeAssistMaterialRepo.save(orderUseModeAssistMaterial);
	}

	public void createOrderUseModes(List<DrugUseMode> orderUseModes) {
		orderUseModeRepo.save(orderUseModes);
	}
	

	public List<Prescription> findPrescriptions(Visit visit) {
		return prescriptionRepo.findByVisit(visit);
	}

	public Prescription findThePrescription(Order order) {
		return prescriptionRepo.findByOrdersIn(order);
	}

	/**
	 * @roseuid 5850F60001AB
	 */
	private void send() {

	}

	/**
	 * @roseuid 585100AC0073
	 */
	private void unSend() {

	}
}
