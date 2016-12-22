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
import com.neusoft.hs.platform.exception.HsException;

@Entity
@Table(name = "domain_drug_type")
public class DrugType extends IdEntity {

	private int stock;

	private int withhold;

	@OneToOne(mappedBy = "drugType", cascade = { CascadeType.ALL })
	private DrugOrderType drugOrderType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_spec_id")
	private DrugTypeSpec drugTypeSpec;

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getWithhold() {
		return withhold;
	}

	public void setWithhold(int withhold) {
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

	public void withhold(int count) throws HsException {
		if (this.stock >= count) {
			this.stock = this.stock - count;
			this.withhold += count;

			this.save();
		} else {
			throw new HsException("drugTypeId[" + this.getId() + "]库存不足");
		}
	}

	private void save() {
		this.getService(DrugTypeRepo.class).save(this);
	}

}
