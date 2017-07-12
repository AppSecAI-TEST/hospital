//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\engine\\entity\\medicalrecord\\MedicalRecordRender.java

package com.neusoft.hs.domain.medicalrecord;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_medical_record_render")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "medicalRecordRenderCache")
public abstract class MedicalRecordRender extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private MedicalRecordType type;
	
	public final static String IntoWardRecord = "入院记录渲染器";

	public final static String InspectResult = "检查单渲染器";

	public final static String TemporaryOrderList = "临时医嘱单渲染器";

	public final static String OutPatientRecord = "门诊记录渲染器";

	public MedicalRecordRender() {
		super();
	}

	public MedicalRecordRender(String id) {
		super();
		this.setId(id);
	}

	/**
	 * 渲染病历
	 * 
	 * @param medicalRecord
	 * @roseuid 5955A3F90025
	 */
	public abstract Object play(MedicalRecord medicalRecord);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MedicalRecordType getType() {
		return type;
	}

	public void setType(MedicalRecordType type) {
		this.type = type;
	}
}
