package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserDomainServiceImpl implements UserDomainService{

	@Autowired
	private AbstractUserRepo userRepo;

	public void create(List<AbstractUser> users) {
		userRepo.save(users);
	}

	public AbstractUser findTheUserByAccount(String account) {
		return userRepo.findOne(account);
	}

	public List<Doctor> findAllDoctor() {
		return userRepo.findDoctor();
	}

	public void clear() {
		userRepo.deleteAll();
	}

}
