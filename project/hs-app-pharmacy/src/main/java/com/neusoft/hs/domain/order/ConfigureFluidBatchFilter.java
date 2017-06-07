//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\app\\pharmacy\\order\\ConfigureFluidBatchFilter.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.Map;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.InPatientAreaDept;
import com.neusoft.hs.domain.pharmacy.ConfigureFluidBatch;
import com.neusoft.hs.platform.util.DateUtil;

public class ConfigureFluidBatchFilter implements OrderExecuteFilter {

	private ConfigureFluidBatch batch;

	private InPatientAreaDept area;

	/**
	 * @return DesignModel.DesignElement.domain.order.OrderExecute
	 * @roseuid 592E75940179
	 */
	public OrderExecuteFilterCondition createCondition(
			Map<String, Object> params, AbstractUser user) {

		OrderExecuteFilterCondition condition = new OrderExecuteFilterCondition();

		Date begin = DateUtil.addHour(DateUtil.getSysDateStart(),
				batch.getBeginDate());
		Date end = DateUtil.addHour(DateUtil.getSysDateStart(),
				batch.getEndDate());
		condition.setBegin(begin);
		condition.setEnd(end);
		condition.addType(OrderExecute.Type_Configure_Fluid);

		condition.setBelongDepts(area.getDepts());

		return condition;
	}

	public ConfigureFluidBatch getBatch() {
		return batch;
	}

	public void setBatch(ConfigureFluidBatch batch) {
		this.batch = batch;
	}

	public InPatientAreaDept getArea() {
		return area;
	}

	public void setArea(InPatientAreaDept area) {
		this.area = area;
	}
}
