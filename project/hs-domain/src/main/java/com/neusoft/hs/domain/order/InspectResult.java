package com.neusoft.hs.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.organization.InspectDept;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_inspect_result")
public class InspectResult extends IdEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_id")
	private InspectApply inspectApply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_item_id")
	private InspectItem inspectItem;

	private String result;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_dept_id")
	private InspectDept inspectDept;

	@Column(name = "create_date")
	private Date createDate;

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public InspectDept getInspectDept() {
		return inspectDept;
	}

	public void setInspectDept(InspectDept inspectDept) {
		this.inspectDept = inspectDept;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
