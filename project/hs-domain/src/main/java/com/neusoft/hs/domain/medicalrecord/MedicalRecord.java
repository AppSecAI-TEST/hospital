//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecord.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_medical_record")
public class MedicalRecord extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clip_id")
	private MedicalRecordClip clip;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private MedicalRecordType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sign_doctor_id")
	private Doctor signDoctor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "create_date")
	private Date createDate;

	@OneToMany(mappedBy = "record", cascade = { CascadeType.ALL })
	private List<MedicalRecordItem> otherItems;

	@OneToMany(mappedBy = "record", cascade = { CascadeType.ALL })
	private List<MedicalRecordLog> logs;

	@Transient
	private Map<TreatmentItemSpec, TreatmentItem> datas = new HashMap<TreatmentItemSpec, TreatmentItem>();

	public static final String State_Created = "已创建";
	public static final String State_Signed = "已签名";

	public MedicalRecord() {
	}

	public MedicalRecord(MedicalRecordType type, Visit visit, Doctor doctor) {
		this.type = type;
		this.visit = visit;
		this.doctor = doctor;

		this.init();
	}

	public void init() {
		for (TreatmentItemSpec itemSpec : this.type.getItems()) {
			datas.put(itemSpec, itemSpec.getTheItem(this.visit));
		}
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public MedicalRecordClip getClip() {
		return clip;
	}

	public void setClip(MedicalRecordClip clip) {
		this.clip = clip;
	}

	public MedicalRecordType getType() {
		return type;
	}

	public void setType(MedicalRecordType type) {
		this.type = type;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public List<MedicalRecordItem> getOtherItems() {
		return otherItems;
	}

	public void setOtherItems(List<MedicalRecordItem> otherItems) {
		this.otherItems = otherItems;
	}

	public void addItem(MedicalRecordItem item) {
		if (this.otherItems == null) {
			this.otherItems = new ArrayList<MedicalRecordItem>();
		}
		this.otherItems.add(item);

		item.setRecord(this);
	}

	public List<MedicalRecordLog> getLogs() {
		return logs;
	}

	public void setLogs(List<MedicalRecordLog> logs) {
		this.logs = logs;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Map<TreatmentItemSpec, TreatmentItem> getDatas() {
		return datas;
	}

	public void setDatas(Map<TreatmentItemSpec, TreatmentItem> datas) {
		this.datas = datas;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Doctor getSignDoctor() {
		return signDoctor;
	}

	public void setSignDoctor(Doctor signDoctor) {
		this.signDoctor = signDoctor;
	}

	public void save() {
		for (TreatmentItem item : datas.values()) {
			item.save();
		}
		this.getService(MedicalRecordRepo.class).save(this);
	}

	public void sign(Doctor doctor) throws MedicalRecordException {
		if (this.state.equals(State_Signed)) {
			throw new MedicalRecordException(this, "id=[" + getId() + "]病历已签名");
		}

		this.state = State_Signed;
		this.signDoctor = doctor;

		this.init();

		this.fixedItems();
	}

	private void fixedItems() throws MedicalRecordException {
		MedicalRecordItem fixedItem;
		for (TreatmentItem item : datas.values()) {
			fixedItem = new MedicalRecordItem(item);
			
			this.addItem(fixedItem);
		}
	}

}
