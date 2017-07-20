package com.neusoft.hs.domain.medicalrecord;

import java.util.HashMap;
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

/**
 * 根据病历类型创建病历器
 * 
 * @author kingbox
 *
 */
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

		TreatmentItem item;
		for (TreatmentItemSpec itemSpec : this.type.getItems()) {
			item = itemSpec.getTheItem(this.getVisit());
			if (item == null && this.isNeedTreatment()) {
				throw new TreatmentException("患者[%s]的诊疗信息[%s]还没有生成", this
						.getVisit().getName(), itemSpec.getName());
			}
			datas.put(itemSpec.getName(), item);
		}

		return datas;
	}

	public MedicalRecordType getType() {
		return type;
	}

	public void setType(MedicalRecordType type) {
		this.type = type;
	}
}
