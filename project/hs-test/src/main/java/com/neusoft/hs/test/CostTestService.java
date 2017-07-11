package com.neusoft.hs.test;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.CostDomainService;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class CostTestService {

	@Autowired
	private CostDomainService costDomainService;

	public List<ChargeRecord> getChargeRecords(Visit visit,
			String chargeItemName, AbstractUser user) {

		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<ChargeRecord> records = costDomainService.getChargeRecords(visit,
				user.getOperationDepts(), pageable);

		List<ChargeRecord> theRecords = records
				.stream()
				.filter(record -> DateUtil.isDayIn(record.getCreateDate(),
						DateUtil.getDateStart(DateUtil.getSysDate()))
						&& record.getChargeItemName().equals(chargeItemName))
				.collect(Collectors.toList());

		return theRecords;
	}
}
