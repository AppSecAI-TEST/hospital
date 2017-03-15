//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\registration\\Voucher.java

package com.neusoft.hs.domain.registration;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 挂号实体
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_voucher")
public class Voucher extends IdEntity {

	private Integer number;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "create_date")
	private Date createDate;

	private Boolean repeatVisit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_record_id")
	private OutPatientPlanRecord planRecord;

	/**
	 * @roseuid 58B7C8C7001F
	 */
	public Voucher() {

	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public OutPatientPlanRecord getPlanRecord() {
		return planRecord;
	}

	public void setPlanRecord(OutPatientPlanRecord planRecord) {
		this.planRecord = planRecord;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getRepeatVisit() {
		return repeatVisit;
	}

	public void setRepeatVisit(Boolean repeatVisit) {
		this.repeatVisit = repeatVisit;
	}

	/**
	 * 离开诊室
	 */
	public void out() {
		if (this.visit.getState().equals(Visit.State_Diagnosing)) {
			this.visit.setState(Visit.State_Diagnosed_Executing);
		}
		this.visit.save();
	}

	/**
	 * 进入诊室
	 */
	public void enter() {
		this.visit.setState(Visit.State_Diagnosing);
		this.visit.save();
	}

}
