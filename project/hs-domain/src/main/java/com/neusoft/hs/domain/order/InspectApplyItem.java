package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.organization.InspectDept;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_inspect_apply_item")
public class InspectApplyItem extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_id")
	private InspectApply inspectApply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_item_id")
	private InspectItem inspectItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_dept_id")
	private InspectDept inspectDept;

	@Column(name = "plan_execute_date")
	private Date planExecuteDate;

	@Column(name = "execute_date")
	private Date executeDate;

	@OneToOne(mappedBy = "inspectApplyItem", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private InspectArrangeOrderExecute inspectArrangeOrderExecute;

	@OneToOne(mappedBy = "inspectApplyItem", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private InspectConfirmOrderExecute inspectConfirmOrderExecute;

	public InspectApply getInspectApply() {
		return inspectApply;
	}

	public void setInspectApply(InspectApply inspectApply) {
		this.inspectApply = inspectApply;
	}

	public InspectItem getInspectItem() {
		return inspectItem;
	}

	public void setInspectItem(InspectItem inspectItem) {
		this.inspectItem = inspectItem;
	}

	public InspectDept getInspectDept() {
		return inspectDept;
	}

	public void setInspectDept(InspectDept inspectDept) {
		this.inspectDept = inspectDept;
	}

	public Date getPlanExecuteDate() {
		return planExecuteDate;
	}

	public void setPlanExecuteDate(Date planExecuteDate) {
		this.planExecuteDate = planExecuteDate;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public InspectArrangeOrderExecute getInspectArrangeOrderExecute() {
		return inspectArrangeOrderExecute;
	}

	public void setInspectArrangeOrderExecute(
			InspectArrangeOrderExecute inspectArrangeOrderExecute) {
		this.inspectArrangeOrderExecute = inspectArrangeOrderExecute;
	}

	public InspectConfirmOrderExecute getInspectConfirmOrderExecute() {
		return inspectConfirmOrderExecute;
	}

	public void setInspectConfirmOrderExecute(
			InspectConfirmOrderExecute inspectConfirmOrderExecute) {
		this.inspectConfirmOrderExecute = inspectConfirmOrderExecute;
	}

}
