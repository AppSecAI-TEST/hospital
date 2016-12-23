//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderType.java

package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@Table(name = "domain_order_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderType extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 16)
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private OrderType parent;

	@OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL })
	private List<OrderType> children;

	@OneToMany(mappedBy = "type", cascade = { CascadeType.ALL })
	private List<Order> orders;

	/**
	 * @param order
	 * @throws HsException
	 * @roseuid 584E66D50265
	 */
	public void check(Order order) throws OrderException {

	}

	/**
	 * @param order
	 * @return
	 * @roseuid 584F4A3201B9
	 */
	public abstract List<OrderExecute> resolveOrder(Order order);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public OrderType getParent() {
		return parent;
	}

	public void setParent(OrderType parent) {
		this.parent = parent;
	}

	public List<OrderType> getChildren() {
		return children;
	}

	public void setChildren(List<OrderType> children) {
		this.children = children;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
