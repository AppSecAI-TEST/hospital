package com.neusoft.hs.application.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public User2Impl getUser(String userId) {
		return userRepo.findOne(userId);
	}

	public void createUser(User2Impl user) {
		userRepo.save(user);
	}
	
	public void clearUsers(){
		userRepo.deleteAll();
	}
}
