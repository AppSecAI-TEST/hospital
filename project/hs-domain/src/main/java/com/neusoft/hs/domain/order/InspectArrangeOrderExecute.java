package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.organization.AbstractUser;

@Entity
@DiscriminatorValue("InspectArrange")
public class InspectArrangeOrderExecute extends OrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_item_id")
	private InspectApplyItem inspectApplyItem;

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {

		if (inspectApplyItem.getPlanExecuteDate() == null) {
			throw new OrderExecuteException(this, "inspectApplyItem["
					+ inspectApplyItem.getId() + "]没有设置计划检查时间");

		}
		Date planExecuteDate = inspectApplyItem.getExecuteDate();

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
