package com.neusoft.hs.domain.visit;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class VisitDomainServiceStub implements VisitDomainService {

	@Override
	public Visit create(CreateVisitVO createVisitVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveHospital(Visit visit, AbstractUser user)
			throws VisitException {
		// TODO Auto-generated method stub

	}

	@Override
	public Visit find(String visitId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Visit findLastVisit(String cardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> findByState(String state, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> findByStateAndDept(String state, Dept dept,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> listVisit(Dept respDept, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> listInPatientVisit(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public int changeVisitState() {
		// TODO Auto-generated method stub
		return 0;
	}

}
