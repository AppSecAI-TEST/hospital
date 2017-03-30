//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\treatment\\TreatmentDomainService.java

package com.neusoft.hs.domain.treatment;

import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;

@FeignClient("engine-service")
public interface TreatmentDomainService {

	/**
	 * 创建诊疗信息（如果存在进行修改）
	 * 
	 * @roseuid 58A148A00070
	 */
	public void create(TreatmentItem item);

	/**
	 * 得到当前时间下指定患者一次就诊需创建的诊疗信息集合
	 * 
	 * @param visit
	 * @param shouldDate
	 * @param user
	 * @return
	 * @throws TreatmentException
	 */
	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			Date shouldDate, AbstractUser user) throws TreatmentException;

	public TreatmentItem getTheTreatmentItem(Visit visit,
			TreatmentItemSpec treatmentItemSpec);

	/**
	 * @roseuid 58A148900388
	 */
	public void update(TreatmentItem item);

	public Iterable<TreatmentItemSpec> getAllTreatmentItemSpecs();

	public void createTreatmentItemSpecs(
			List<TreatmentItemSpec> treatmentItemSpecs);

	public void clearTreatmentItems();

	public void clearTreatmentItemSpecs();
}
