package com.neusoft.hs.domain.inspect;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.medicalrecord.DateMedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.DeptMedicalRecordItemValue;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordBuilder;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordItem;
import com.neusoft.hs.domain.medicalrecord.SimpleMedicalRecordItemValue;
import com.neusoft.hs.domain.treatment.Itemable;

@Entity
@DiscriminatorValue("InspectResult")
public class InspectResultMedicalRecordBuilder extends MedicalRecordBuilder {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_result_id")
	private InspectResult result;

	public InspectResultMedicalRecordBuilder() {
		super();
	}

	public InspectResultMedicalRecordBuilder(InspectResult result) {
		super();
		this.result = result;
	}

	@Override
	public Map<String, Itemable> create() {
		Map<String, Itemable> datas = new HashMap<String, Itemable>();

		MedicalRecordItem item;
		
		item = new MedicalRecordItem("患者姓名");

		SimpleMedicalRecordItemValue visitName = new SimpleMedicalRecordItemValue();
		visitName.setInfo(result.getVisit().getName());

		item.addValue(visitName);

		datas.put(item.getName(), item);

		item = new MedicalRecordItem("检查结果");

		SimpleMedicalRecordItemValue content = new SimpleMedicalRecordItemValue();
		content.setInfo(result.getResult());

		item.addValue(content);

		datas.put(item.getName(), item);

		item = new MedicalRecordItem("检查时间");

		DateMedicalRecordItemValue inspectDate = new DateMedicalRecordItemValue();
		inspectDate.setDate(result.getCreateDate());

		item.addValue(inspectDate);

		datas.put(item.getName(), item);
		
		item = new MedicalRecordItem("检查科室");

		DeptMedicalRecordItemValue inspectDept = new DeptMedicalRecordItemValue();
		inspectDept.setDept(result.getInspectDept());

		item.addValue(inspectDept);

		datas.put(item.getName(), item);

		return datas;
	}

	public InspectResult getResult() {
		return result;
	}

	public void setResult(InspectResult result) {
		this.result = result;
	}

}
