//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderUseMode.java

package com.neusoft.hs.domain.pharmacy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_order_use_mode")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderUseMode extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 64)
	private String name;

	@OneToMany(mappedBy = "useMode", cascade = { CascadeType.ALL })
	private List<Order> orders;

	@OneToMany(mappedBy = "orderUseMode", cascade = { CascadeType.ALL })
	private List<OrderUseModeAssistMaterial> orderUseModeAssistMaterials;

	/**
	 * @param drugOrderType
	 * @param order
	 * @return
	 * @roseuid 586D9239030F
	 */
	public abstract void resolve(Order order, DrugOrderType drugOrderType);

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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public OrderUseModeAssistMaterial getTheOrderUseModeChargeItem(String key) {
		for (OrderUseModeAssistMaterial orderUseModeAssistMaterial : orderUseModeAssistMaterials) {
			if (orderUseModeAssistMaterial.getSign().equals(key)) {
				return orderUseModeAssistMaterial;
			}
		}
		return null;
	}

	public List<OrderUseModeAssistMaterial> getOrderUseModeChargeItems() {
		return orderUseModeAssistMaterials;
	}

	public void setOrderUseModeChargeItems(
			List<OrderUseModeAssistMaterial> orderUseModeChargeItems) {
		this.orderUseModeAssistMaterials = orderUseModeChargeItems;
	}

}
