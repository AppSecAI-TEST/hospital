//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\medicalrecord\\MedicalRecordType.java

package com.neusoft.hs.domain.medicalrecord;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.treatment.TreatmentItemSpec;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_medical_type")
public class MedicalRecordType extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@Column(length = 256)
	private String template;

	@OneToMany(mappedBy = "type", cascade = { CascadeType.ALL })
	private List<MedicalRecord> records;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "domain_medical_type_item", joinColumns = { @JoinColumn(name = "type_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "item_id", referencedColumnName = "id") })
	private List<TreatmentItemSpec> items;

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

}