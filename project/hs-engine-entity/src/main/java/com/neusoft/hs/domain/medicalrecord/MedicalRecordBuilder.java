package com.neusoft.hs.domain.medicalrecord;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 病历创建器 用于创建病历数据
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_medical_record_builder")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedicalRecordBuilder extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@OneToOne(mappedBy = "builder")
	private MedicalRecord record;

	@Transient
	private boolean needTreatment = true;

	/**
	 * 创建病历数据
	 * 
	 * @return
	 * @throws TreatmentException
	 */
	public final Map<String, Itemable> create() throws TreatmentException {
		return this.doCreate();
	}

	/**
	 * 创建病历数据方法
	 * 
	 * @return
	 * @throws TreatmentException
	 */
	public abstract Map<String, Itemable> doCreate() throws TreatmentException;

	public void save() {
		this.getService(MedicalRecordBuilderRepo.class).save(this);
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public MedicalRecord getRecord() {
		return record;
	}

	public void setRecord(MedicalRecord record) {
		this.record = record;
	}

	public boolean isNeedTreatment() {
		return needTreatment;
	}

	public void setNeedTreatment(boolean needTreatment) {
		this.needTreatment = needTreatment;
	}
}
