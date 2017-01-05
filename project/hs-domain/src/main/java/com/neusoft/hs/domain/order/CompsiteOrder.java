package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("Compsite")
public class CompsiteOrder extends Order {

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.ALL })
	private List<Order> orders;

	@Override
	public void check() throws OrderException {
		for (Order order : orders) {
			order.check();
		}
	}

	@Override
	public void updateState(OrderExecute orderExecute) {
		for (Order order : orders) {
			order.updateState(orderExecute);
		}
	}

	public void addOrder(Order order) throws OrderException {
		if (this.orders == null) {
			this.orders = new ArrayList<Order>();
		}
		if (this.orders.size() > 0) {
			this.orders.get(0).compsiteMatch(order);
		}
		this.orders.add(order);

	}

}
