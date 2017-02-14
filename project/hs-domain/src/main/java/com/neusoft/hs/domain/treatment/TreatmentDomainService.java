//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\treatment\\TreatmentDomainService.java

package com.neusoft.hs.domain.treatment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public void createTreatmentItemSpecs(
			List<TreatmentItemSpec> treatmentItemSpecs) {
		treatmentItemSpecRepo.save(treatmentItemSpecs);
	}

	public void clearTreatmentItemSpecs() {
		treatmentItemSpecRepo.deleteAll();
	}
}
