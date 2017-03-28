package com.neusoft.hs.domain.inspect;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.platform.entity.IdEntity;

/**
 * 检查项目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_inspect_apply_item")
public class InspectApplyItem extends IdEntity {

	private String state = State_Executing;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_apply_id")
	private InspectApply inspectApply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_item_id")
	private InspectItem inspectItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "arrange_dept_id")
	private Dept arrangeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inspect_dept_id")
	private InspectDept inspectDept;

	@Column(name = "inspect_place", length = 64)
	private String inspectPlace;

	@Column(name = "plan_execute_date")
	private Date planExecuteDate;

	@Column(name = "execute_date")
	private Date executeDate;

	@OneToOne(mappedBy = "inspectApplyItem", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private InspectArrangeOrderExecute inspectArrangeOrderExecute;

	@OneToOne(mappedBy = "inspectApplyItem", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private InspectConfirmOrderExecute inspectConfirmOrderExecute;

	public static final String State_Executing = "执行中";

	public static final String State_Finished = "已完成";

	public static final String State_Canceled = "已取消";

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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

	public Dept getArrangeDept() {
		return arrangeDept;
	}

	public void setArrangeDept(Dept arrangeDept) {
		this.arrangeDept = arrangeDept;
	}

	public InspectDept getInspectDept() {
		return inspectDept;
	}

	public void setInspectDept(InspectDept inspectDept) {
		this.inspectDept = inspectDept;
	}

	public String getInspectPlace() {
		return inspectPlace;
	}

	public void setInspectPlace(String inspectPlace) {
		this.inspectPlace = inspectPlace;
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

	public void save() {
		this.getService(InspectApplyItemRepo.class).save(this);
	}

}
