//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\PharmacyDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PharmacyDomainService {

	@Autowired
	private DrugTypeSpecRepo drugTypeSpecRepo;

	@Autowired
	private PharmacyRepo pharmacyRepo;

	@Autowired
	private DrugTypeRepo drugTypeRepo;

	/**
	 * @roseuid 584F43B3013B
	 */
	public void find() {

	}

	/**
	 * @roseuid 5850F60001AB
	 */
	public void send() {

	}

	/**
	 * @roseuid 585100AC0073
	 */
	public void unSend() {

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

}
