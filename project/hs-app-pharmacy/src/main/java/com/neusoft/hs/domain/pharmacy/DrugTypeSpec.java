//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\DrugTypeSpec.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 药品规格
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_pharmacy_drug_type_spec")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "drugTypeSpecCache")
public class DrugTypeSpec extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 64)
	private String name;

	@Column(length = 256)
	private String effect;

	@Column(name = "is_transport_fluid_charge")
	private boolean isTransportFluidCharge = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private DrugTypeSpec parent;

	@OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL })
	private List<DrugTypeSpec> children;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@OneToMany(mappedBy = "drugTypeSpec", cascade = { CascadeType.ALL })
	private List<DrugType> drugTypes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public boolean isTransportFluidCharge() {
		return isTransportFluidCharge;
	}

	public void setTransportFluidCharge(boolean isTransportFluidCharge) {
		this.isTransportFluidCharge = isTransportFluidCharge;
	}

	public DrugTypeSpec getParent() {
		return parent;
	}

	public void setParent(DrugTypeSpec parent) {
		this.parent = parent;
	}

	public List<DrugTypeSpec> getChildren() {
		return children;
	}

	public void setChildren(List<DrugTypeSpec> children) {
		this.children = children;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	public List<DrugType> getDrugTypes() {
		return drugTypes;
	}

	public void setDrugTypes(List<DrugType> drugTypes) {
		this.drugTypes = drugTypes;
	}

}
