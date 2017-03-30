package com.neusoft.hs.application.treatment;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;

@FeignClient("engine-service")
public interface TreatmentAppService {

	public List<TreatmentItemSpec> getShouldTreatmentItemSpecs(Visit visit,
			AbstractUser user) throws TreatmentException;

}
