package com.neusoft.hs.domain.organization;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserAdminDomainService {

	@Autowired
	private AbstractUserRepo userRepo;

	public void create(List<AbstractUser> users) {
		userRepo.save(users);
	}

	public AbstractUser findTheUserByAccount(String account) {
		return userRepo.findOne(account);
	}

	public List<AbstractUser> find(Pageable pageable) {
		return userRepo.findAll(pageable).getContent();
	}

	public AbstractUser find(String id) {
		AbstractUser user = userRepo.findOne(id);
		Hibernate.initialize(user.getDept());
		return user;
	}

	public List<Doctor> findDoctor(Pageable pageable) {
		return userRepo.findDoctor(pageable);
	}

	public List<Nurse> findNurse(List<Dept> depts, Pageable pageable) {
		return userRepo.findNurse(depts, pageable);
	}

	public List<Admin> findAdmin(Pageable pageable) {
		return userRepo.findAdmin(pageable);
	}

	public void delete(String id) {
		AbstractUser user = userRepo.findOne(id);
		user.delete();
	}

	public void clear() {
		userRepo.deleteAll();
	}
}
