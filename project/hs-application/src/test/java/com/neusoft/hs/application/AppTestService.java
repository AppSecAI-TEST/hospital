package com.neusoft.hs.application;

import org.springframework.stereotype.Service;

import com.neusoft.hs.domain.DomainTestService;
import com.neusoft.hs.platform.exception.HsException;

@Service
public class AppTestService extends DomainTestService {
	
	/**
	 * @throws HsException
	 */
	@Override
	protected void execute() {

	}

}
