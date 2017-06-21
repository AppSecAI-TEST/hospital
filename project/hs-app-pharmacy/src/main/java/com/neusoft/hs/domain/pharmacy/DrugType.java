//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\pharmacy\\DrugType.java

package com.neusoft.hs.domain.pharmacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;

/**
 * 药品类型（含库存及所在药房）
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_pharmacy_drug_type")
public class DrugType extends IdEntity {

	private int stock;

	private int withhold;

	@Column(length = 64)
	private String productionBatch;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_purchase_id")
	private DrugPurchase drugPurchase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drug_type_spec_id")
	private DrugTypeSpec drugTypeSpec;

	/**
	 * 预扣
	 * 
	 * @param count
	 * @throws HsException
	 */
	public void withhold(int count) throws PharmacyException {
		if (this.stock >= count) {
			this.stock -= count;
			this.withhold += count;

			this.save();
		} else {
			throw new PharmacyException("drugTypeId[" + this.getId() + "]库存不足");
		}
	}

	/**
	 * 取消预扣
	 * 
	 * @param count
	 * @throws HsException
	 */
	public void unWithhold(Integer count) throws PharmacyException {
		if (this.withhold >= count) {
			this.withhold -= count;
			this.stock += count;

			this.save();
		} else {
			throw new PharmacyException("drugTypeId[" + this.getId()
					+ "]已预扣数量不足");
		}
	}

	/**
	 * 摆药
	 * 
	 * @param count
	 * @throws HsException
	 */
	public void send(int count) throws PharmacyException {
		if (this.withhold >= count) {
			this.withhold -= count;
		} else if (this.withhold + this.stock >= count) {
			this.stock -= (count - this.withhold);
			this.withhold = 0;
		} else {
			throw new PharmacyException("drugTypeId[" + this.getId() + "]库存不足");
		}
	}

	/**
	 * 取消摆药
	 * 
	 * @param count
	 * @throws HsException
	 */
	public void unSend(int count) throws PharmacyException {
		this.stock += count;
	}

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
		drugTypeSpec.addDrugType(this);
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	public DrugPurchase getDrugPurchase() {
		return drugPurchase;
	}

	public void setDrugPurchase(DrugPurchase drugPurchase) {
		this.drugPurchase = drugPurchase;
	}

	public void save() {
		this.getService(DrugTypeRepo.class).save(this);
	}
}
