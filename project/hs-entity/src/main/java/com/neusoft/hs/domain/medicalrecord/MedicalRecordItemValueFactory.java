package com.neusoft.hs.domain.medicalrecord;

import com.neusoft.hs.domain.treatment.SimpleTreatmentItemValue;
import com.neusoft.hs.domain.treatment.TreatmentItemValue;

public class MedicalRecordItemValueFactory {

	public static MedicalRecordItemValue create(TreatmentItemValue value)
			throws MedicalRecordException {

		if (value instanceof SimpleTreatmentItemValue) {
			return new SimpleMedicalRecordItemValue(
					(SimpleTreatmentItemValue) value);
		} else {
			throw new MedicalRecordException(null, "TreatmentItemValue类型["
					+ value.getClass() + "]为编写转化逻辑");
		}

	}

}
