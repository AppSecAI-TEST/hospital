//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderFrequencyType.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 长期医嘱频次基类
 * 
 * 通过子类化，可以增加不同类型的医嘱执行频次
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_frequency_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "orderFrequencyTypeCache")
public abstract class OrderFrequencyType extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@OneToMany(mappedBy = "frequencyType")
	private List<LongOrder> orders;

	public OrderFrequencyType() {
		super();
	}

	public OrderFrequencyType(String id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	/**
	 * 根据医嘱的计划执行时间段，计算指定天的频次执行时间
	 * 
	 * @param order
	 *            分解的长嘱
	 * @param currentDate
	 *            待分解的时间（天）（0点0分0秒）
	 * 
	 * @roseuid 587DB2EE02BB
	 */
	public abstract List<Date> calExecuteDates(LongOrder order, Date currentDate);

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

	public List<LongOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<LongOrder> orders) {
		this.orders = orders;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
