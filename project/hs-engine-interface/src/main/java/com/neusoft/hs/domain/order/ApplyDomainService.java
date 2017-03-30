package com.neusoft.hs.domain.order;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("engine-service")
public interface ApplyDomainService {

	public Apply find(String applyId);

	public void save(Apply apply);

}
