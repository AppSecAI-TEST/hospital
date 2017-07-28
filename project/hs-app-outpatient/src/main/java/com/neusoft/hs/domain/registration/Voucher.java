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
import com.neusoft.hs.platform.log.LogUtil;

/**
 * 挂号实体
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_outpatient_voucher")
public class Voucher extends IdEntity {

	private Integer number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 32)
	private String visitName;

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
		this.visitName = visit.getName();
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
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

		LogUtil.log(this.getClass(), "患者一次就诊[{}]离开诊室[{}]",
				this.visit.getName(), planRecord.getRoom().getId());
	}

	/**
	 * 进入诊室
	 */
	public void enter() {
		this.visit.setState(Visit.State_Diagnosing);

		LogUtil.log(this.getClass(), "患者一次就诊[{}]进入诊室[{}]",
				this.visit.getName(), planRecord.getRoom().getId());
	}

}
