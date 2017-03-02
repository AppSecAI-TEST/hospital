//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\registration\\Voucher.java

package com.neusoft.hs.domain.registration;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.outpatientoffice.OutpatientPlanRecord;
import com.neusoft.hs.domain.visit.OutPatientVisit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_voucher")
public class Voucher extends IdEntity {

	private Integer number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private OutPatientVisit visit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_record_id")
	private OutpatientPlanRecord planRecord;

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

	public OutPatientVisit getVisit() {
		return visit;
	}

	public void setVisit(OutPatientVisit visit) {
		this.visit = visit;
	}

	public OutpatientPlanRecord getPlanRecord() {
		return planRecord;
	}

	public void setPlanRecord(OutpatientPlanRecord planRecord) {
		this.planRecord = planRecord;
	}

}
