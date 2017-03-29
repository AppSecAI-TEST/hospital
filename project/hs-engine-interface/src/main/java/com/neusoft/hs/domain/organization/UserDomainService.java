package com.neusoft.hs.domain.organization;

import java.util.List;

public interface UserDomainService {

	public void create(List<AbstractUser> users);

	public AbstractUser findTheUserByAccount(String account);

	public List<Doctor> findAllDoctor();

	public void clear();

}
