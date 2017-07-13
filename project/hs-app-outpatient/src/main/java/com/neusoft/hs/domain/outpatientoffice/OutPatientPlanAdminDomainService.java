package com.neusoft.hs.domain.outpatientoffice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutPatientPlanAdminDomainService {

	@Autowired
	private OutPatientPlanRecordRepo outpatientPlanRecordRepo;

	@Autowired
	private OutPatientRoomRepo outPatientRoomRepo;

	@Autowired
	private VoucherTypeRepo voucherTypeRepo;

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

	public List<VoucherType> findVoucherTypes(Pageable pageable) {
		return voucherTypeRepo.findAll(pageable).getContent();
	}

	public List<OutPatientRoom> findOutPatientRooms(Pageable pageable) {
		return outPatientRoomRepo.findAll(pageable).getContent();
	}
}
