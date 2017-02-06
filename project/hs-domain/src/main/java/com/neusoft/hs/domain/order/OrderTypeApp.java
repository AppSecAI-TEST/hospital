//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderTypeApp.java

package com.neusoft.hs.domain.order;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_order_type_app")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderTypeApp extends IdEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_type_id")
	private OrderType orderType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	public OrderTypeApp() {
		super();
	}

	public OrderTypeApp(OrderType orderType) {
		super();
		this.orderType = orderType;
	}

	/**
	 * @param order
	 * @return
	 * @throws OrderException
	 * @roseuid 584F4A3201B9
	 */
	public abstract void resolveOrder() throws OrderException;

	public void check() throws OrderException {
		orderType.check(order);
	}

	public void verify() throws OrderException {
		orderType.verify(order);
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
