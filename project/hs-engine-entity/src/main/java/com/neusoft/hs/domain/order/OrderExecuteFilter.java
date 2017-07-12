//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\engine\\entity\\order\\OrderExecuteFilter.java

package com.neusoft.hs.domain.order;

import java.util.Map;

import com.neusoft.hs.domain.organization.AbstractUser;

/**
 * 执行条目过滤器
 * 
 * @author kingbox
 *
 */
public interface OrderExecuteFilter {

	/**
	 * 创建过滤条目
	 * 
	 * @return
	 * @roseuid 592E649801E1
	 */
	public OrderExecuteFilterCondition createCondition(
			Map<String, Object> params, AbstractUser user);
}
