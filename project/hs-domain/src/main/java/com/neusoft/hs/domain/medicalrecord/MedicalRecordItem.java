//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordItem.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record_item")
public class MedicalRecordItem extends IdEntity implements Itemable {

	@NotEmpty(message = "Key不能为空")
	@Column(length = 32)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "record_id")
	private MedicalRecord record;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@OneToMany(mappedBy = "item", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<MedicalRecordItemValue> values;

	public MedicalRecordItem() {

	}

	public MedicalRecordItem(String name) {
		super();
		this.name = name;
	}

	public MedicalRecordItem(TreatmentItem item) throws MedicalRecordException {
		this.name = item.getName();
		this.visit = item.getVisit();

		MedicalRecordItemValue theValue;
		for (TreatmentItemValue value : item.getValues()) {
			theValue = MedicalRecordItemValueFactory.create(value);
			this.addValue(theValue);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MedicalRecord getRecord() {
		return record;
	}

	public void setRecord(MedicalRecord record) {
		this.record = record;
	}

	public List<MedicalRecordItemValue> getValues() {
		return values;
	}

	public void setValues(List<MedicalRecordItemValue> values) {
		this.values = values;
	}

	public void addValue(MedicalRecordItemValue value) {
		if (this.values == null) {
			this.values = new ArrayList<MedicalRecordItemValue>();
		}
		this.values.add(value);

		value.setItem(this);
		value.setVisit(visit);
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public void save() {
		this.getService(MedicalRecordItemRepo.class).save(this);
	}

	@Override
	public Date getCreateDate() {
		return null;
	}

	@Override
	public void setCreateDate(Date date) {
	}

	@Override
	public AbstractUser getCreator() {
		return null;
	}

	@Override
	public void setCreator(AbstractUser user) {
	}
}
