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

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.treatment.Itemable;
import com.neusoft.hs.domain.treatment.TreatmentException;
import com.neusoft.hs.domain.treatment.TreatmentItem;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 病历
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_medical_record")
public class MedicalRecord extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clip_id")
	private MedicalRecordClip clip;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private MedicalRecordType type;

	@Column(name = "type_name", length = 128)
	private String typeName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private AbstractUser doctor;

	@Column(name = "doctor_name", length = 16)
	private String doctorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sign_doctor_id")
	private AbstractUser signDoctor;

	@Column(name = "sign_doctor_name", length = 16)
	private String signDoctorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	@Column(name = "create_date")
	private Date createDate;

	@OneToMany(mappedBy = "record", cascade = { CascadeType.ALL })
	private List<MedicalRecordItem> items;

	@OneToMany(mappedBy = "record", cascade = { CascadeType.ALL })
	private List<MedicalRecordLog> logs;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "builder_id")
	private MedicalRecordBuilder builder;

	@Transient
	private Map<String, Itemable> datas = new HashMap<String, Itemable>();

	public static final String State_Created = "已创建";
	public static final String State_Fixed = "已锁定";
	public static final String State_Signed = "已签名";

	public MedicalRecord() {
	}

	/**
	 * 创建一份初始化后的病历
	 * 
	 * @param builder
	 * @param type
	 * @param visit
	 * @param doctor
	 * @throws TreatmentException
	 */
	public MedicalRecord(MedicalRecordBuilder builder, MedicalRecordType type,
			Visit visit, AbstractUser doctor) throws TreatmentException {
		this.setType(type);
		this.setVisit(visit);
		this.setDoctor(doctor);

		this.builder = builder;

		this.init();
	}

	/**
	 * 根据诊疗信息创建病历数据
	 * 
	 * @throws TreatmentException
	 */
	public void init() throws TreatmentException {

		datas = this.builder.create();

		MedicalRecordItem medicalRecordItem;
		for (Itemable item : datas.values()) {
			if (item instanceof MedicalRecordItem) {
				medicalRecordItem = (MedicalRecordItem) item;

				medicalRecordItem.setRecord(this);
				medicalRecordItem.setVisit(visit);
			}
		}
	}

	/**
	 * 加载病历数据
	 * 
	 * @throws TreatmentException
	 */
	public void load() throws TreatmentException {
		if (this.state.equals(State_Signed) || this.state.equals(State_Fixed)) {
			this.loadData();
		} else {
			this.init();
		}
	}

	/**
	 * 签名
	 * 
	 * @param doctor
	 * @throws MedicalRecordException
	 * @throws TreatmentException
	 */
	public void sign(AbstractUser doctor) throws MedicalRecordException,
			TreatmentException {
		if (!this.type.isNeedSign()) {
			throw new MedicalRecordException(this, "类型为[%s]的病历不需要签名",
					this.type.getName());
		}
		if (this.state.equals(State_Signed)) {
			throw new MedicalRecordException(this, "id=[%s]病历已签名", getId());
		}

		if (!this.state.equals(State_Fixed)) {
			this.doFix();
		}

		this.state = State_Signed;
		this.setSignDoctor(doctor);

		MedicalRecordLog recordLog = new MedicalRecordLog();
		recordLog.setRecord(this);
		recordLog.setType(MedicalRecordLog.Type_Sign);
		recordLog.setOperator(doctor);
		recordLog.setCreateDate(DateUtil.getSysDate());

		recordLog.save();

	}

	/**
	 * 锁定
	 * 
	 * @param user
	 * @throws MedicalRecordException
	 * @throws TreatmentException
	 */
	public void fix(AbstractUser user) throws MedicalRecordException,
			TreatmentException {
		this.doFix();
		this.state = State_Fixed;

		MedicalRecordLog recordLog = new MedicalRecordLog();
		recordLog.setRecord(this);
		recordLog.setType(MedicalRecordLog.Type_Fix);
		recordLog.setOperator(user);
		recordLog.setCreateDate(DateUtil.getSysDate());

		recordLog.save();
	}

	/**
	 * 检查是否可以移交到病案室
	 * 
	 * @throws MedicalRecordException
	 */
	public void checkTransfer() throws MedicalRecordException {
		if (this.type.isNeedSign() && !this.state.equals(State_Signed)) {
			throw new MedicalRecordException(this, "患者[%s]的id=[%s][%s]病历还没有签名",
					this.getVisitName(), getId(), this.type.getName());
		}
	}

	/**
	 * 锁定病历数据
	 * 
	 * @throws MedicalRecordException
	 * @throws TreatmentException
	 */
	protected void doFix() throws MedicalRecordException, TreatmentException {
		this.init();
		this.fixedItems();
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
		this.typeName = type.getName();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public AbstractUser getDoctor() {
		return doctor;
	}

	public void setDoctor(AbstractUser doctor) {
		this.doctor = doctor;
		this.doctorName = doctor.getName();
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public List<MedicalRecordItem> getItems() {
		return items;
	}

	public void setItems(List<MedicalRecordItem> items) {
		this.items = items;
	}

	public void addItem(MedicalRecordItem item) {
		if (this.items == null) {
			this.items = new ArrayList<MedicalRecordItem>();
		}
		this.items.add(item);

		item.setRecord(this);
	}

	public MedicalRecordItem getTheItem(String name) {
		return this.getService(MedicalRecordItemRepo.class)
				.findByRecordAndName(this, name);
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
		this.visitName = visit.getName();
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public Map<String, Itemable> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Itemable> datas) {
		this.datas = datas;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public AbstractUser getSignDoctor() {
		return signDoctor;
	}

	public void setSignDoctor(AbstractUser signDoctor) {
		this.signDoctor = signDoctor;
		this.signDoctorName = signDoctor.getName();
	}

	public String getSignDoctorName() {
		return signDoctorName;
	}

	public void setSignDoctorName(String signDoctorName) {
		this.signDoctorName = signDoctorName;
	}

	public MedicalRecordBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(MedicalRecordBuilder builder) {
		this.builder = builder;
	}

	public MedicalRecordRender getRender() throws MedicalRecordException {
		MedicalRecordRender render = this.type.getRender();

		if (render == null) {
			throw new MedicalRecordException(this, "病历类型[%s]没有配置渲染器",
					this.type.getName());
		}

		return render;
	}

	public void save() {

		this.builder.save();

		this.getService(MedicalRecordRepo.class).save(this);

		for (Itemable item : datas.values()) {
			item.save();
		}
	}

	private void loadData() {
		for (MedicalRecordItem item : this.items) {
			datas.put(item.getName(), item);
		}
	}

	private void fixedItems() throws MedicalRecordException {
		MedicalRecordItem fixedItem;
		for (Itemable item : datas.values()) {
			if (item instanceof TreatmentItem) {
				fixedItem = new MedicalRecordItem((TreatmentItem) item);
				this.addItem(fixedItem);
			}
		}
	}

	public void delete() {
		this.getService(MedicalRecordRepo.class).delete(this);
	}

}
