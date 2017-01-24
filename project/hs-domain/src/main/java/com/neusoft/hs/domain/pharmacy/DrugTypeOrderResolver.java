package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;

public interface DrugTypeOrderResolver {

	public List<OrderExecute> resolve(Order order, DrugOrderType drugOrderType);

}
