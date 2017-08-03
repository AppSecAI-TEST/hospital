//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderType.java

package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 医嘱类型
 * 
 * 通过子类化，负责编写该类型医嘱操作过程中的回调逻辑
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "orderTypeCache")
public abstract class OrderType extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 64)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private OrderType parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@OneToMany(mappedBy = "parent")
	private List<OrderType> children;

	@OneToMany(mappedBy = "orderType")
	private List<Order> orders;

	/**
	 * 医嘱创建时的检查回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 * @throws OrderExecuteException 
	 * @roseuid 584E66D50265
	 */
	protected void check(Order order) throws OrderException, OrderExecuteException {

	}

	/**
	 * 医嘱创建后的回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 */
	protected void create(Order order) throws OrderException {
	}

	/**
	 * 医嘱分解逻辑
	 * 
	 * @param order
	 * @return
	 * @throws OrderException
	 * @roseuid 584F4A3201B9
	 */
	public abstract void resolveOrder(OrderTypeApp orderTypeApp)
			throws OrderException;

	/**
	 * 医嘱核对后的回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 */
	protected void verify(Order order) throws OrderException {
	}

	/**
	 * 医嘱删除后的回调函数
	 * 
	 * @param order
	 * @throws OrderException
	 */
	public void delete(Order order) throws OrderException {

	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
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
	
	@Override
	public String toString() {
		return name;
	}
}
