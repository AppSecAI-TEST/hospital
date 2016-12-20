//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\DrugType.java

package com.neusoft.hs.domain.pharmacy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.order.DrugOrderType;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_drug_type")
public class DrugType extends IdEntity {

	private Integer stock;

	private Integer withhold;

	@OneToOne(mappedBy = "drugType", cascade = { CascadeType.ALL })
	private DrugOrderType drugOrderType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_spec_id")
	private DrugTypeSpec drugTypeSpec;

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getWithhold() {
		return withhold;
	}

	public void setWithhold(Integer withhold) {
		this.withhold = withhold;
	}

	public DrugOrderType getDrugOrderType() {
		return drugOrderType;
	}

	public void setDrugOrderType(DrugOrderType drugOrderType) {
		this.drugOrderType = drugOrderType;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public DrugTypeSpec getDrugTypeSpec() {
		return drugTypeSpec;
	}

	public void setDrugTypeSpec(DrugTypeSpec drugTypeSpec) {
		this.drugTypeSpec = drugTypeSpec;
	}

}
