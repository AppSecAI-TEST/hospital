package com.neusoft.hs.domain.diagnosis;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("engine-service")
public interface DiseaseDomainService {

	public void createDiseases(List<Disease> diseases);

	public void clearDiseases();

}
