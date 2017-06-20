//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\PharmacyDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PharmacyAdminService {

	@Autowired
	private DrugTypeSpecRepo drugTypeSpecRepo;

	@Autowired
	private PharmacyRepo pharmacyRepo;

	@Autowired
	private DispenseDrugWinRepo dispenseDrugWinRepo;

	@Autowired
	private DrugTypeRepo drugTypeRepo;

	@Autowired
	private DrugUseModeRepo orderUseModeRepo;

	@Autowired
	private DrugUseModeAssistMaterialRepo orderUseModeAssistMaterialRepo;

	@Autowired
	private PrescriptionRepo prescriptionRepo;

	@Autowired
	private AssistMaterialRepo assistMaterialRepo;

	@Autowired
	private DispensingDrugBatchRepo dispensingDrugBatchRepo;

	@Autowired
	private DispensingDrugOrderRepo dispensingDrugOrderRepo;

	@Autowired
	private DrugTypeConsumeRecordRepo drugTypeConsumeRecordRepo;

	public List<Pharmacy> findPharmacy(Pageable pageable) {
		return pharmacyRepo.findAll(pageable).getContent();
	}

	public List<DrugUseMode> findDrugUseMode(Pageable pageable) {
		return orderUseModeRepo.findAll(pageable).getContent();
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

	public void createDispenseDrugWins(List<DispenseDrugWin> dispenseDrugWins) {
		dispenseDrugWinRepo.save(dispenseDrugWins);
	}

	public void clearDispenseDrugWins() {
		dispenseDrugWinRepo.deleteAll();
	}

	public void createDrugTypes(List<DrugType> drugTypes) {
		drugTypeRepo.save(drugTypes);
	}

	public void clearDrugTypes() {
		drugTypeRepo.deleteAll();
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

	public void clearPrescriptions() {
		prescriptionRepo.deleteAll();
	}

	public void createAssistMaterials(List<AssistMaterial> assistMaterials) {
		assistMaterialRepo.save(assistMaterials);
	}

	public void clearAssistMaterial() {
		assistMaterialRepo.deleteAll();
	}

	public void clearDispensingDrugOrder() {
		dispensingDrugOrderRepo.deleteAll();
	}

	public void createDispensingDrugBatchs(List<DispensingDrugBatch> batchs) {
		dispensingDrugBatchRepo.save(batchs);
	}

	public void clearDispensingDrugBatch() {
		dispensingDrugBatchRepo.deleteAll();
	}

	public void clearDrugTypeConsumeRecords() {
		drugTypeConsumeRecordRepo.deleteAll();
	}
}
