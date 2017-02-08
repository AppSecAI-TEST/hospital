package com.neusoft.hs.domain.order;

import java.util.List;

public interface OrderCreateCommand {

	public List<Order> getOrders();

	public void save();

}
