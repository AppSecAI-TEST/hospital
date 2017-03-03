//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutpatientPlanDomainService.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientPlanDomainService {

	@Autowired
	private OutPatientPlanRecordRepo outpatientPlanRecordRepo;

	@Autowired
	private OutPatientRoomRepo outPatientRoomRepo;

	@Autowired
	private VoucherTypeRepo voucherTypeRepo;

	/**
	 * @roseuid 58B7C812025D
	 */
	public void createPlanRecord(OutPatientPlanRecord planRecord) {
		outpatientPlanRecordRepo.save(planRecord);
	}

	/**
	 * @roseuid 58B7C82C00AD
	 */
	public void listPlanRecord() {

	}

	public void clearPlanRecord() {
		outpatientPlanRecordRepo.deleteAll();
	}

	public void createRooms(List<OutPatientRoom> rooms) {
		outPatientRoomRepo.save(rooms);
	}

	public void clearRoom() {
		outPatientRoomRepo.deleteAll();
	}

	public void createVoucherTypes(List<VoucherType> voucherTypes) {
		voucherTypeRepo.save(voucherTypes);
	}

	public void clearVoucherType() {
		voucherTypeRepo.deleteAll();
	}
}
