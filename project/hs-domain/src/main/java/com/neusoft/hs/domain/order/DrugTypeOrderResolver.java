package com.neusoft.hs.domain.order;

import java.util.List;

public interface DrugTypeOrderResolver {

	public List<OrderExecute> resolve(Order order, DrugOrderType drugOrderType);

}
