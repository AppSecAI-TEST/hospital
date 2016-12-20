//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_order_execute")
public class OrderExecute extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "charge_state", length = 32)
	private String chargeState;

	@Column(name = "cost_state", length = 32)
	private String costState;

	@Column(name = "plan_start_date")
	private Date planStartDate;

	@Column(name = "plan_end_date")
	private Date planEndDate;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role executeRole;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actual_executor_id")
	private AbstractUser actualExecutor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToMany(mappedBy = "orderExecute", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<ChargeRecord> chargeRecords;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_dept_id")
	private Dept executeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	/**
	 * @roseuid 584F624D0233
	 */
	public void send() {

	}

	/**
	 * @roseuid 584F62CB0254
	 */
	public void save() {

	}

	/**
	 * @roseuid 584FB6EB03E5
	 */
	public void finish() {

	}

	/**
	 * @roseuid 58509B990022
	 */
	public void createChargeRecords() {

	}

	/**
	 * @roseuid 5850B1970103
	 */
	public void cancel() {

	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChargeState() {
		return chargeState;
	}

	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}

	public String getCostState() {
		return costState;
	}

	public void setCostState(String costState) {
		this.costState = costState;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Role getExecuteRole() {
		return executeRole;
	}

	public void setExecuteRole(Role executeRole) {
		this.executeRole = executeRole;
	}

	public AbstractUser getActualExecutor() {
		return actualExecutor;
	}

	public void setActualExecutor(AbstractUser actualExecutor) {
		this.actualExecutor = actualExecutor;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

	public Dept getExecuteDept() {
		return executeDept;
	}

	public void setExecuteDept(Dept executeDept) {
		this.executeDept = executeDept;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}
