package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.inspect.InspectApplyItem;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.log.LogUtil;

@Entity
@DiscriminatorValue("InspectArrange")
public class InspectArrangeOrderExecute extends OrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_item_id")
	private InspectApplyItem inspectApplyItem;

	public static final String PlanExecuteDate = "PlanExecuteDate";

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

		if (params == null
				|| !params
						.containsKey(InspectArrangeOrderExecute.PlanExecuteDate)) {
			throw new OrderExecuteException(this, "params没有设置Key为["
					+ PlanExecuteDate + "]计划检查时间");

		}
		Date planExecuteDate = (Date) params
				.get(InspectArrangeOrderExecute.PlanExecuteDate);

		inspectApplyItem.setPlanExecuteDate(planExecuteDate);

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]安排检查[{}]时间为[{}]",
				user.getId(), this.getVisit().getName(),
				inspectApplyItem.getId(), planExecuteDate);

		OrderExecute next = this.getNext();
		next.setPlanStartDate(planExecuteDate);
		next.setPlanEndDate(planExecuteDate);

		next.save();
	}

	public InspectApplyItem getInspectApplyItem() {
		return inspectApplyItem;
	}

	public void setInspectApplyItem(InspectApplyItem inspectApplyItem) {
		this.inspectApplyItem = inspectApplyItem;
	}

}
