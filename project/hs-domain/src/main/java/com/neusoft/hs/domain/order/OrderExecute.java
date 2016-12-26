//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@Table(name = "domain_order_execute")
public class OrderExecute extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@NotEmpty(message = "类型不能为空")
	@Column(length = 32)
	private String type;

	@NotEmpty(message = "组标识不能为空")
	@Column(name = "team_id", length = 36)
	private String teamId;

	@Column(name = "previous_id", length = 36)
	private String previousId;

	@Column(name = "next_id", length = 36)
	private String nextId;

	@Column(name = "charge_state", length = 32)
	private String chargeState;

	@Column(name = "cost_state", length = 32)
	private String costState;

	@Column(name = "plan_start_date")
	private Date planStartDate;

	@Column(name = "plan_end_date")
	private Date planEndDate;

	@Column(name = "send_date")
	private Date sendDate;

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
	@JoinColumn(name = "belong_dept_id")
	private InPatientDept belongDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_dept_id")
	private Dept executeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public static final String State_NeedSend = "待发送";

	public static final String State_NeedExecute = "待执行";

	public static final String State_Executing = "执行中";

	public static final String ChargeState_NoCharge = "未收费";

	public static final String CostState_NoCost = "未发生成本";

	public static final String Type_Dispense_Drug = "摆药";

	public static final String Type_Take_Drug = "取药";

	/**
	 * @param nurse
	 * @roseuid 584F624D0233
	 */
	public void send() {
		this.state = State_NeedExecute;
		this.sendDate = DateUtil.getSysDate();

		this.order.setStateDesc(this.type + "执行条目已发送");
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

	public OrderExecute() {
		super();
	}

	public OrderExecute(String id) {
		this.setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getPreviousId() {
		return previousId;
	}

	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
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

	public InPatientDept getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(InPatientDept belongDept) {
		this.belongDept = belongDept;
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
