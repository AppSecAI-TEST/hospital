//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderTypeApp.java

package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.pharmacy.DrugTypeConsumeRecord;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.Pharmacy;
import com.neusoft.hs.domain.pharmacy.PharmacyException;

@Entity
@DiscriminatorValue("Drug")
public class DrugOrderTypeApp extends OrderTypeApp {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_use_mode_id")
	public DrugUseMode drugUseMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

	@JsonIgnore
	@OneToMany(mappedBy = "drugOrderTypeApp", cascade = { CascadeType.ALL })
	private List<DrugTypeConsumeRecord> consumeRecords;

	public DrugOrderTypeApp() {
		super();
	}

	public DrugOrderTypeApp(Pharmacy pharmacy, DrugUseMode drugUseMode) {
		this.pharmacy = pharmacy;
		this.drugUseMode = drugUseMode;
	}

	public DrugUseMode getDrugUseMode() {
		return drugUseMode;
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public List<DrugTypeConsumeRecord> getConsumeRecords() {
		return consumeRecords;
	}

	public void setConsumeRecords(List<DrugTypeConsumeRecord> consumeRecords) {
		this.consumeRecords = consumeRecords;

		for (DrugTypeConsumeRecord record : consumeRecords) {
			record.setDrugOrderTypeApp(this);
		}
	}

	public DrugTypeSpec getDrugTypeSpec() {
		return this.getService(DrugOrderTypeRepo.class)
				.findOne(this.getOrder().getOrderType().getId())
				.getDrugTypeSpec();
	}

	public void withhold(DrugTypeSpec drugTypeSpec, Integer count)
			throws PharmacyException {
		this.setConsumeRecords(this.pharmacy.withhold(drugTypeSpec, count));
	}

	public void unWithhold() throws PharmacyException {
		this.pharmacy.unWithhold(consumeRecords);
	}

}
