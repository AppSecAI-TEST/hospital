package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class UserDomainServiceStub implements UserDomainService {

	@Override
	public void create(List<AbstractUser> users) {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractUser findTheUserByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Doctor> findAllDoctor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
