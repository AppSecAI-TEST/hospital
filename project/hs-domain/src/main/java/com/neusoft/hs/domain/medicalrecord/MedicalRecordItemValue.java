//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\TreatmentItemValue.java

package com.neusoft.hs.domain.medicalrecord;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record_item_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedicalRecordItemValue extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private MedicalRecordItem item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public MedicalRecordItem getItem() {
		return item;
	}

	public void setItem(MedicalRecordItem item) {
		this.item = item;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	@Override
	public abstract String toString();

	public void save() {
		this.getService(MedicalRecordItemValueRepo.class).save(this);
	}

}
