package com.neusoft.hs.portal.framework.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysDateService {

	@Autowired
	private SysDateRepo sysDateRepo;

	public void init() {
		SysDate sysDate = sysDateRepo.findOne(SysDate.Id);
		if (sysDate != null) {
			Date currentDate = DateUtil.getSysDate();
			if (currentDate.before(sysDate.getSysDate())) {
				DateUtil.setSysDate(sysDate.getSysDate());
			}
		}
	}

	public void save() {
		Date currentDate = DateUtil.getSysDate();
		SysDate sysDate = sysDateRepo.findOne(SysDate.Id);
		if (sysDate == null) {
			sysDate = new SysDate();
		}
		sysDate.setSysDate(currentDate);

		sysDateRepo.save(sysDate);
	}
}
