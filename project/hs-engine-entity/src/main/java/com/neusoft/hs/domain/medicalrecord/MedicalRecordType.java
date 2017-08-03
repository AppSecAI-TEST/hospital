//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordType.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 病历类型
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_medical_record_type")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "medicalRecordTypeCache")
public class MedicalRecordType extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@Column(length = 256)
	private String template;

	@Column(name = "need_sign")
	private boolean needSign;

	@Column(name = "need_create")
	private boolean needCreate;

	@Column(name = "is_unique")
	private boolean unique;

	@OneToMany(mappedBy = "type")
	private List<MedicalRecord> records;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "domain_medical_record_type_item", joinColumns = { @JoinColumn(name = "type_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "item_id", referencedColumnName = "id") })
	private List<TreatmentItemSpec> items;

	@OneToOne(mappedBy = "type", cascade = { CascadeType.REMOVE })
	private MedicalRecordRender render;

	public final static String IntoWardRecord = "入院记录";

	public final static String InspectResult = "检查单";

	public final static String TemporaryOrderList = "临时医嘱单";

	public final static String OutPatientRecord = "门诊记录";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public boolean isNeedSign() {
		return needSign;
	}

	public void setNeedSign(boolean needSign) {
		this.needSign = needSign;
	}

	public boolean isNeedCreate() {
		return needCreate;
	}

	public void setNeedCreate(boolean needCreate) {
		this.needCreate = needCreate;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public List<MedicalRecord> getRecords() {
		return records;
	}

	public void setRecords(List<MedicalRecord> records) {
		this.records = records;
	}

	public List<TreatmentItemSpec> getItems() {
		return items;
	}

	public void setItems(List<TreatmentItemSpec> items) {
		this.items = items;
	}

	public MedicalRecordRender getRender() {
		return render;
	}

	public void setRender(MedicalRecordRender render) {
		this.render = render;
		this.render.setType(this);
	}

	public void save() {
		this.getService(MedicalRecordTypeRepo.class).save(this);
	}
}
