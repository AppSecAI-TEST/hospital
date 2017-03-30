//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitDomainService.java

package com.neusoft.hs.domain.patient;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("engine-service")
public interface PatientDomainService {

	public Patient findByCardNumber(String cardNumber);

	public void clear();
}
