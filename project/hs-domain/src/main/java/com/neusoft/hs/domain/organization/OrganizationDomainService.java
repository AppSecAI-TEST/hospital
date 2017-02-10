package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationDomainService {

	@Autowired
	private UnitRepo unitRepo;

	public void create(List<Unit> units) {
		unitRepo.save(units);
	}

	public void clear() {
		unitRepo.deleteAll();
	}

	public List<InPatientDept> findAllInPatientDept() {
		return unitRepo.findInPatientDept();
	}

	public Dept findTheDept(String id) {
		return (Dept) unitRepo.findOne(id);
	}

}
