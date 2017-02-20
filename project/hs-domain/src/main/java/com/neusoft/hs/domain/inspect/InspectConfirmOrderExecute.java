package com.neusoft.hs.domain.inspect;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderExecuteException;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("InspectConfirm")
public class InspectConfirmOrderExecute extends OrderExecute {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_item_id")
	private InspectApplyItem inspectApplyItem;

	@Override
	protected void doFinish(AbstractUser user) throws OrderExecuteException {
		super.doFinish(user);
		inspectApplyItem.setExecuteDate(DateUtil.getSysDate());
		inspectApplyItem.setState(InspectApplyItem.State_Finished);

		Order order = this.getOrder();
		if (order instanceof TemporaryOrder) {
			TemporaryOrder temporaryOrder = (TemporaryOrder) order;
			temporaryOrder.setExecuteDate(DateUtil.getSysDate());
			temporaryOrder.setExecuteUser(user);
		}
	}

	public InspectApplyItem getInspectApplyItem() {
		return inspectApplyItem;
	}

	public void setInspectApplyItem(InspectApplyItem inspectApplyItem) {
		this.inspectApplyItem = inspectApplyItem;
	}

}
