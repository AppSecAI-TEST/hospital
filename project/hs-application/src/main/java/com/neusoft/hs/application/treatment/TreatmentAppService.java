package com.neusoft.hs.application.treatment;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.TreatmentDomainService;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class TreatmentAppService {

	public final static int shouldHour = 24;

	@Autowired
	private TreatmentDomainService treatmentDomainService;

	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			AbstractUser user) {
		Date shouldDate = DateUtil.addHour(DateUtil.getSysDate(), shouldHour);
		return treatmentDomainService.getShouldTreatmentItemSpecs(visit,
				shouldDate, user);
	}

}
