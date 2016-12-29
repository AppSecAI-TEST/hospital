//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitLog.java

package com.neusoft.hs.domain.visit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

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

	@Column(name = "create_date")
	private Date createDate;

	public static final String Type_Create = "送诊";
	public static final String Type_InitAccount = "初始账户";
	public static final String Type_IntoWard = "进入病房";
	public static final String Type_LeaveWard = "出院登记";
	public static final String Type_LeaveHospital = "出院结算";
	

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
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void save() {
		this.getService(VisitLogRepo.class).save(this);
	}

}
