package com.neusoft.hs.domain.organization;

import java.util.List;

public interface OrganizationDomainService {

	public void create(List<Unit> units);

	public void clear();

	public List<InPatientDept> findAllInPatientDept();

	public Dept findTheDept(String id);

	public void save(Unit unit);

}
