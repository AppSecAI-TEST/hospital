package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class OrganizationDomainServiceStub implements OrganizationDomainService {

	@Override
	public void create(List<Unit> units) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<InPatientDept> findAllInPatientDept() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dept findTheDept(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Unit unit) {
		// TODO Auto-generated method stub

	}

}
