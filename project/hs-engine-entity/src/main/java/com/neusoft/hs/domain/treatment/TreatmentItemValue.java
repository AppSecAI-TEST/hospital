//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\TreatmentItemValue.java

package com.neusoft.hs.domain.treatment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItemValue;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 诊疗项目值
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_treatment_item_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TreatmentItemValue extends IdEntity implements ItemValue {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private TreatmentItem item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "treatment_spec_id")
	private TreatmentItemSpec treatmentItemSpec;

	@Column(name = "treatment_spec_name", length = 128)
	private String treatmentItemSpecName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	public TreatmentItem getItem() {
		return item;
	}

	public void setItem(TreatmentItem item) {
		this.item = item;
	}

	public TreatmentItemSpec getTreatmentItemSpec() {
		return treatmentItemSpec;
	}

	public void setTreatmentItemSpec(TreatmentItemSpec treatmentItemSpec) {
		this.treatmentItemSpec = treatmentItemSpec;
		this.treatmentItemSpecName = treatmentItemSpec.getName();
	}

	public String getTreatmentItemSpecName() {
		return treatmentItemSpecName;
	}

	public void setTreatmentItemSpecName(String treatmentItemSpecName) {
		this.treatmentItemSpecName = treatmentItemSpecName;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	/**
	 * 将诊疗项目值转化成病历项目值
	 * 
	 * @return
	 * @throws MedicalRecordException
	 */
	public MedicalRecordItemValue toMedicalRecordItemValue()
			throws MedicalRecordException {
		throw new MedicalRecordException(null,
				"TreatmentItemValue类型[%s]未编写转化逻辑", this.getClass());
	}

	public void save() {
		this.getService(TreatmentItemValueRepo.class).save(this);
	}

	@Override
	public void delete() {
		this.getService(TreatmentItemValueRepo.class).delete(this);
	}
	
	@Override
	public void doLoad() {
	}
}
