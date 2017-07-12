package com.neusoft.hs.domain.cost;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.neusoft.hs.domain.order.OrderExecute;

/**
 * 收费执行条目
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Charge")
public class ChargeOrderExecute extends OrderExecute {

	@NotNull(message = "被收费执行条目不能为空")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_id")
	private OrderExecute charge;

	public OrderExecute getCharge() {
		return charge;
	}

	public void setCharge(OrderExecute charge) {
		this.charge = charge;
	}

}
