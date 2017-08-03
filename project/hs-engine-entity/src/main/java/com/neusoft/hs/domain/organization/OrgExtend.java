package com.neusoft.hs.domain.organization;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_organization_org_extend")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "orgExtendCache")
public class OrgExtend extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private Org org;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
	@JoinColumn(name = "out_charge_dept_id")
	private Dept outChargeDept;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE})
	@JoinColumn(name = "in_charge_dept_id")
	private Dept inChargeDept;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE})
	@JoinColumn(name = "record_room_dept_id")
	private Dept recordRoomDept;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE})
	@JoinColumn(name = "in_patient_office_dept_id")
	private Dept inPatientOfficeDept;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
		if (this.id == null) {
			this.id = org.getId();
		}
	}

	public Dept getOutChargeDept() {
		return outChargeDept;
	}

	public void setOutChargeDept(Dept outChargeDept) {
		this.outChargeDept = outChargeDept;
	}

	public Dept getInChargeDept() {
		return inChargeDept;
	}

	public void setInChargeDept(Dept inChargeDept) {
		this.inChargeDept = inChargeDept;
	}

	public Dept getRecordRoomDept() {
		return recordRoomDept;
	}

	public void setRecordRoomDept(Dept recordRoomDept) {
		this.recordRoomDept = recordRoomDept;
	}

	public Dept getInPatientOfficeDept() {
		return inPatientOfficeDept;
	}

	public void setInPatientOfficeDept(Dept inPatientOfficeDept) {
		this.inPatientOfficeDept = inPatientOfficeDept;
	}

}
