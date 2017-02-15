//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\treatment\\TreatmentDomainService.java

package com.neusoft.hs.domain.treatment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.util.DateUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class TreatmentDomainService {

	@Autowired
	private TreatmentItemRepo treatmentItemRepo;

	@Autowired
	private TreatmentItemSpecRepo treatmentItemSpecRepo;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * @roseuid 58A148A00070
	 */
	public void create(TreatmentItem item) {
		if (item.getCreateDate() == null) {
			item.setCreateDate(DateUtil.getSysDate());
		}
		treatmentItemRepo.save(item);
		applicationContext.publishEvent(new TreatmentItemCreatedEvent(item));
	}

	/**
	 * @roseuid 58A148900388
	 */
	public void update(TreatmentItem item) {
		treatmentItemRepo.save(item);
		applicationContext.publishEvent(new TreatmentItemUpdatedEvent(item));
	}

	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			Date shouldDate, AbstractUser user) throws TreatmentException {

		List<TreatmentItemSpec> shouldTreatmentItemSpecs = new ArrayList<TreatmentItemSpec>();

		Iterator<TreatmentItemSpec> it = getAllTreatmentItemSpecs().iterator();
		TreatmentItemSpec treatmentItemSpec;
		while (it.hasNext()) {
			treatmentItemSpec = it.next();
			Date theShouldDate = treatmentItemSpec.getShouldDate(visit);
			if (theShouldDate != null && theShouldDate.before(shouldDate)) {
				shouldTreatmentItemSpecs.add(treatmentItemSpec);
			}
		}
		return shouldTreatmentItemSpecs;
	}

	public Iterable<TreatmentItemSpec> getAllTreatmentItemSpecs() {
		return treatmentItemSpecRepo.findAll();
	}

	public void createTreatmentItemSpecs(
			List<TreatmentItemSpec> treatmentItemSpecs) {
		treatmentItemSpecRepo.save(treatmentItemSpecs);
	}

	public void clearTreatmentItemSpecs() {
		treatmentItemSpecRepo.deleteAll();
	}
}
