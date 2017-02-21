package com.neusoft.hs.domain.inspect;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.treatment.Itemable;

@Entity
@DiscriminatorValue("InspectResult")
public class InspectResultMedicalRecordBuilder extends MedicalRecordBuilder {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_result_id")
	private InspectResult result;

	@Override
	public Map<String, Itemable> create() {
		// TODO Auto-generated method stub
		return null;
	}

	public InspectResult getResult() {
		return result;
	}

	public void setResult(InspectResult result) {
		this.result = result;
	}

}
