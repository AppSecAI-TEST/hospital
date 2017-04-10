//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\PharmacyDomainService.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class PharmacyAdminService {

	@Autowired
	private PrescriptionRepo prescriptionRepo;
	

	@Autowired
	private AssistMaterialRepo assistMaterialRepo;

	public void clearPrescriptions() {
		prescriptionRepo.deleteAll();
	}
	
	public void createAssistMaterials(List<AssistMaterial> assistMaterials) {
		assistMaterialRepo.save(assistMaterials);
	}
	
	public void clearAssistMaterial(){
		assistMaterialRepo.deleteAll();
	}
}
