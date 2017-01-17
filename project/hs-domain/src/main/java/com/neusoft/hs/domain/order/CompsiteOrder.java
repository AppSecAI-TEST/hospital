package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_order_team")
public class CompsiteOrder extends IdEntity implements OrderCreateCommand {

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.ALL })
	private List<Order> orders;

	@OneToMany(mappedBy = "compsiteOrder", cascade = { CascadeType.ALL })
	private List<OrderExecute> orderExecutes;

	public void addOrder(Order order) throws OrderException {
		if (this.orders == null) {
			this.orders = new ArrayList<Order>();
		}
		if (this.orders.size() > 0) {
			this.orders.get(0).compsiteMatch(order);
		}
		this.orders.add(order);
		order.setCompsiteOrder(this);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderExecute> getOrderExecutes() {
		return orderExecutes;
	}

	public void setOrderExecutes(List<OrderExecute> orderExecutes) {
		this.orderExecutes = orderExecutes;
	}

	@Override
	public void save() {
		this.getService(CompsiteOrderRepo.class).save(this);
	}
}
