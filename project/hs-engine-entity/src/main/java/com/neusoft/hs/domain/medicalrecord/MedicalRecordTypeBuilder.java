package com.neusoft.hs.domain.medicalrecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;

@Entity
@DiscriminatorValue("MedicalRecordType")
public class MedicalRecordTypeBuilder extends MedicalRecordBuilder {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private MedicalRecordType type;

	public MedicalRecordTypeBuilder() {
	}

	public MedicalRecordTypeBuilder(MedicalRecordType type, Visit visit) {
		super();
		this.type = type;
		this.setVisit(visit);
	}

	@Override
	public Map<String, Itemable> doCreate() throws TreatmentException {

		Map<String, Itemable> datas = new HashMap<String, Itemable>();

		for (TreatmentItemSpec itemSpec : this.type.getItems()) {
			datas.put(itemSpec.getName(), itemSpec.getTheItem(this.getVisit()));
		}

		return datas;
	}

	@Override
	public void delete() {
		if (type.isUnique()) {
			List<MedicalRecord> records = this.getService(
					MedicalRecordRepo.class).findByVisitAndType(
					this.getVisit(), type);

			TreatmentItem treatmentItem;
			for (MedicalRecord record : records) {
				// 删除对应的诊疗信息
				for (MedicalRecordItem item : record.getItems()) {
					treatmentItem = item.getTreatmentItem();
					if (treatmentItem != null
							&& treatmentItem.getTreatmentItemSpec()
									.isRepeatCreate()) {
						treatmentItem.delete();
					}
				}
				// 删除病历
				record.delete();
			}
		}
	}

	public MedicalRecordType getType() {
		return type;
	}

	public void setType(MedicalRecordType type) {
		this.type = type;
	}
}
