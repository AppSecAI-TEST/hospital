package com.neusoft.hs.domain.organization;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationAdminDomainService {

	@Autowired
	private UnitRepo unitRepo;

	public List<InPatientDept> findInPatientDept(Pageable pageable) {
		return unitRepo.findInPatientDept(pageable);
	}

	public List<InPatientAreaDept> findInPatientArea(Pageable pageable) {
		return unitRepo.findInPatientArea(pageable);
	}

	public List<InPatientAreaDept> findInPatientArea(InPatientDept dept) {

		InPatientDept inPatientDept = (InPatientDept) unitRepo.findOne(dept
				.getId());
		Hibernate.initialize(inPatientDept.getAreas());
		return inPatientDept.getAreas();
	}

	public Dept findTheDept(String id) {
		return (Dept) unitRepo.findOne(id);
	}

	public void save(Unit unit) {
		unitRepo.save(unit);
	}

	public void create(List<Unit> units) {
		unitRepo.save(units);
	}

	public void clear() {
		List<Org> orgs = unitRepo.findOrgs();
		if (orgs != null) {
			for (Org org : orgs) {
				unitRepo.delete(org);
			}
		}
	}
}
