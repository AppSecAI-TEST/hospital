package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_inspect_item")
public class InspectItem extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@ManyToMany(mappedBy = "inspectItems", cascade = { CascadeType.ALL })
	private List<InspectApply> inspectApplys;
	
	@OneToMany(mappedBy = "inspectItem", cascade = { CascadeType.ALL })
	private List<InspectResult> inspectResults;

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

	public List<InspectApply> getInspectApplys() {
		return inspectApplys;
	}

	public void setInspectApplys(List<InspectApply> inspectApplys) {
		this.inspectApplys = inspectApplys;
	}

}