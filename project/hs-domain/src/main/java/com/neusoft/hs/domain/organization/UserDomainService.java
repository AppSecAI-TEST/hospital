package com.neusoft.hs.domain.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserDomainService {

	@Autowired
	private UserRepo userRepo;

	public void create(List<AbstractUser> users) {
		userRepo.save(users);
	}

	public void clear() {
		userRepo.deleteAll();
	}

}
