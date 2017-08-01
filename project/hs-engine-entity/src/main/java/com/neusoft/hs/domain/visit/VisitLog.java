//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitLog.java

package com.neusoft.hs.domain.visit;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_visit_log")
public class VisitLog extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(length = 16)
	private String type;

	@Column(length = 256)
	private String info;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private AbstractUser operator;

	@Column(name = "operator_name", length = 32)
	private String operatorName;

	@Column(name = "create_date")
	private Date createDate;

	public static final String Type_First = "初诊";
	public static final String Type_Repeat = "复诊";
	public static final String Type_Referral = "送诊";
	public static final String Type_InitAccount = "初始账户";
	public static final String Type_IntoWard = "进入病房";
	public static final String Type_TransferDeptSend = "发起转科";
	public static final String Type_TransferDeptConfirm = "确认转科";
	public static final String Type_OutWard = "出院登记";
	public static final String Type_OutHospital = "出院结算";
	public static final String Type_Transfer = "移交病案室";
	public static final String Type_Archived = "已归档";

	public static final String Type_LeaveHospital = "门诊离院";

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public AbstractUser getOperator() {
		return operator;
	}

	public void setOperator(AbstractUser operator) {
		this.operator = operator;
		this.operatorName = operator.getName();
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String calType(boolean isInPatient) {
		if (isInPatient) {
			this.type = VisitLog.Type_Referral;
		} else {
			this.type = VisitLog.Type_First;
		}
		return this.type;
	}

	public void save() {
		this.getService(VisitLogRepo.class).save(this);
	}

	public void delete() {
		this.getService(VisitLogRepo.class).delete(this);
	}

}
