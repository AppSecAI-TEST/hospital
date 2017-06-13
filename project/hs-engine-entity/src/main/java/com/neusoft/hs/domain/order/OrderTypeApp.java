//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderTypeApp.java

package com.neusoft.hs.domain.order;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 创建医嘱条目时关联医嘱类型后的附属属性集合父类
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_type_app")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OrderTypeApp extends IdEntity {

	@OneToOne(mappedBy = "typeApp", cascade = { CascadeType.ALL })
	private Order order;

	public OrderTypeApp() {
		super();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void save() {
		this.getService(OrderTypeAppRepo.class).save(this);
	}
}
