package com.neusoft.hs.domain.order;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.hs.platform.entity.EntityUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderUtil {

	@Autowired
	private OrderDomainService orderDomainService;

	private Set<String> integration(List<Order> orders) {
		Set<String> ids = new HashSet<String>();

		Iterator<Order> iterator = orderDomainService.find(EntityUtil
				.listId(orders));
		while (iterator.hasNext()) {
			Order order = iterator.next();
			if (order.getCompsiteOrder() != null) {
				ids.add(order.getCompsiteOrder().getId());
			} else {
				ids.add(order.getId());
			}
		}

		return ids;
	}

}
