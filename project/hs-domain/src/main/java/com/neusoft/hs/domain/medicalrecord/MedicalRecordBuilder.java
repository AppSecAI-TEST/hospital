package com.neusoft.hs.domain.medicalrecord;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record_builder")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedicalRecordBuilder extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public abstract Map<String, Itemable> create();

	public void save() {
		this.getService(MedicalRecordBuilderRepo.class).save(this);
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}
