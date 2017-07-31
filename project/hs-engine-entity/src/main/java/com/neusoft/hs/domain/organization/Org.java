//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\organization\\Org.java

package com.neusoft.hs.domain.organization;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Org")
public class Org extends Unit {

	@OneToOne(mappedBy = "org", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OrgExtend orgExtend;

	/**
	 * 门诊收费处
	 * 
	 * @return
	 */
	public Dept getOutChargeDept() {
		return orgExtend.getOutChargeDept();
	}

	public void setOutChargeDept(Dept outChargeDept) {
		if (this.orgExtend == null) {
			this.orgExtend = new OrgExtend();
			this.orgExtend.setOrg(this);
		}
		this.orgExtend.setOutChargeDept(outChargeDept);
	}

	/**
	 * 住院收费处
	 * 
	 * @return
	 */
	public Dept getInChargeDept() {
		return orgExtend.getInChargeDept();
	}

	public void setInChargeDept(Dept inChargeDept) {
		if (this.orgExtend == null) {
			this.orgExtend = new OrgExtend();
			this.orgExtend.setOrg(this);
		}
		this.orgExtend.setInChargeDept(inChargeDept);
	}

	/**
	 * 病案室
	 * 
	 * @return
	 */
	public Dept getRecordRoomDept() {
		return orgExtend.getRecordRoomDept();
	}

	public void setRecordRoomDept(Dept recordRoomDept) {
		if (this.orgExtend == null) {
			this.orgExtend = new OrgExtend();
			this.orgExtend.setOrg(this);
		}
		this.orgExtend.setRecordRoomDept(recordRoomDept);
	}

	/**
	 * 住院处
	 * 
	 * @return
	 */
	public Dept getInPatientOfficeDept() {
		return orgExtend.getInPatientOfficeDept();
	}

	public void setInPatientOfficeDept(Dept inPatientOfficeDept) {
		if (this.orgExtend == null) {
			this.orgExtend = new OrgExtend();
			this.orgExtend.setOrg(this);
		}
		this.orgExtend.setInPatientOfficeDept(inPatientOfficeDept);
	}

}
