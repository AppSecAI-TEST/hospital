package com.neusoft.hs.domain.order;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.inspect.InspectApplyItem;
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
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		super.doFinish(params, user);
		inspectApplyItem.setExecuteDate(DateUtil.getSysDate());
		inspectApplyItem.setState(InspectApplyItem.State_Finished);
	}

	public InspectApplyItem getInspectApplyItem() {
		return inspectApplyItem;
	}

	public void setInspectApplyItem(InspectApplyItem inspectApplyItem) {
		this.inspectApplyItem = inspectApplyItem;
	}

}
