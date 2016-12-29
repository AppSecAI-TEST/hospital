package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SecondNursing")
public class SecondNursingOrderType extends OrderType {

	@Override
	public OrderExecuteTeam resolveOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

}
