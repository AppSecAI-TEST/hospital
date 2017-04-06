package com.neusoft.hs.domain.cost;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.OrderExecute;

@Entity
@DiscriminatorValue("Charge")
public class ChargeOrderExecute extends OrderExecute {

	@NotEmpty(message = "被收费执行条目Id不能为空")
	@Column(name = "charge_id", length = 36)
	private String chargeId;

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

}
