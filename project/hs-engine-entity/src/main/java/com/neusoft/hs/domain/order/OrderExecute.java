//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 医嘱执行条目 由医嘱条目分解而成 对应一个角色的具体执行任务
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order_execute")
public abstract class OrderExecute extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@NotEmpty(message = "类型不能为空")
	@Column(length = 32)
	private String type;

	private Integer count;

	@NotNull(message = "执行组不能为空")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private OrderExecuteTeam team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "compsite_order_id")
	private CompsiteOrder compsiteOrder;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_id")
	private OrderExecute previous;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "next_id")
	private OrderExecute next;

	@Column(name = "is_main")
	private boolean isMain = false;

	@Column(name = "is_last")
	private boolean isLast = false;

	@Column(name = "is_alone")
	private boolean isAlone = false;

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

	@Column(name = "actual_executor_name", length = 32)
	private String actualExecutorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToMany(mappedBy = "orderExecute", cascade = { CascadeType.ALL })
	private List<OrderExecuteChargeItemRecord> chargeItemRecords;

	@OneToMany(mappedBy = "orderExecute", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<ChargeRecord> chargeRecords;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_dept_id")
	private Dept belongDept;

	@Column(name = "belong_dept_name", length = 32)
	private String belongDeptName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_dept_id")
	private Dept executeDept;

	@Column(name = "execute_dept_name", length = 32)
	private String executeDeptName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_dept_id")
	private Dept chargeDept;

	@Column(name = "charge_dept_name", length = 32)
	private String chargeDeptName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(length = 16)
	private String visitName;

	@Column(name = "order_category", length = 8)
	private String orderCategory;

	public static final String State_NeedSend = "待发送";

	public static final String State_NeedExecute = "待执行";

	public static final String State_Executing = "执行中";

	public static final String State_Finished = "已完成";

	public static final String State_Canceled = "已作废";

	public static final String State_Stoped = "已停止";

	public static final String ChargeState_NoApply = "不适用";

	public static final String ChargeState_NoCharge = "未收费";

	public static final String ChargeState_Charge = "已收费";

	public static final String ChargeState_NeedBackCharge = "待退费";

	public static final String ChargeState_BackCharge = "已退费";

	public static final String CostState_NoApply = "不适用";

	public static final String CostState_NoCost = "未发生成本";

	public static final String CostState_Cost = "已发生成本";

	public static final String Type_Dispense_Drug = "摆药";

	public static final String Type_Distribute_Drug = "发药";

	public static final String Type_Configure_Fluid = "配液";

	public static final String Type_Transport_Fluid = "输液";

	public static final String Type_FirstNursing = "一级护理";

	public static final String Type_SecondNursing = "二级护理";

	public static final String Type_Transfer_Dept_Send = "发起转科";

	public static final String Type_Transfer_Dept_Confirm = "确认转科";

	public static final String Type_Enter_Hospital_Register = "入院登记";

	public static final String Type_Enter_Hospital_SupplyCost = "预存住院费";

	public static final String Type_Enter_Hospital_InWard = "接诊";

	public static final String Type_Leave_Hospital_Register = "出院登记";

	public static final String Type_Leave_Hospital_Balance = "出院结算";

	public static final String Type_Arrange_Inspect = "安排检查";

	public static final String Type_Confirm_Inspect = "确认检查";

	public static final String Type_Change = "收费";

	/**
	 * 发送执行条目 当执行条目由其他科室执行时需要通过发送才可以使其执行
	 * 
	 * @param nurse
	 * @throws OrderExecuteException
	 * @roseuid 584F624D0233
	 */
	public void send() throws OrderExecuteException {
		if (!this.state.equals(State_NeedSend)) {
			throw new OrderExecuteException(this, "state=[%s]不是[%s]",
					this.state, State_NeedSend);
		}

		this.updateState();
		this.sendDate = DateUtil.getSysDate();

		this.order.setStateDesc(this.type + "执行条目已发送");
	}

	/**
	 * 医嘱条目执行前回调函数
	 * 
	 * @throws OrderExecuteException
	 */
	protected void doExecuteBefore() throws OrderExecuteException {
	}

	/**
	 * 完成一条执行条目
	 * 
	 * 当有下一条执行条目时将其状态置为【执行中】
	 * 
	 * @param user
	 * @param params
	 * @throws OrderExecuteException
	 * @return 下一条执行条目
	 * @roseuid 584FB6EB03E5
	 */
	public OrderExecute finish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

		if (!this.state.equals(State_Executing)) {
			throw new OrderExecuteException(this, "执行条目[%s]的状态为[%s]不能执行完成",
					this.getId(), this.state);
		}

		this.doFinish(params, user);

		Date sysDate = DateUtil.getSysDate();
		this.endDate = sysDate;
		this.state = State_Finished;
		this.setActualExecutor(user);

		if (!this.isAlone) {
			if (order instanceof TemporaryOrder) {
				if (this.isMain) {
					TemporaryOrder temporaryOrder = (TemporaryOrder) order;
					temporaryOrder.setExecuteDate(DateUtil.getSysDate());
					temporaryOrder.setExecuteUser(user);
				}
			}

			OrderExecute next = this.getNext();
			if (next != null) {
				next.doExecuteBefore();
				next.setState(OrderExecute.State_Executing);
				next.setStartDate(sysDate);

				next.save();

				this.order.setStateDesc(this.type + "执行条目已完成");
			} else {
				this.order.updateState(this);
			}

			return next;
		}
		return null;
	}

	/**
	 * 完成一条执行条目回调函数
	 * 
	 * @param params
	 * @param user
	 * @throws OrderExecuteException
	 */
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {

	}

	/**
	 * 创建该执行条目对应的费用条目
	 * 
	 * @return
	 * @roseuid 58509B990022
	 */
	public List<ChargeRecord> createChargeRecords() {
		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();

		if (this.chargeItemRecords != null && this.chargeItemRecords.size() > 0) {
			for (OrderExecuteChargeItemRecord chargeItemRecord : this.chargeItemRecords) {
				ChargeRecord chargeRecord = new ChargeRecord();

				ChargeItem chargeItem = chargeItemRecord.getChargeItem();

				chargeRecord.setPrice(chargeItem.getPrice());
				chargeRecord.setCount(chargeItemRecord.getCount());
				chargeRecord.setAmount(-this.calAmout(chargeItemRecord));
				chargeRecord.setChargeItem(chargeItem);

				chargeRecords.add(chargeRecord);
			}
		}

		return chargeRecords;
	}

	/**
	 * 作废一条执行条目
	 * 
	 * @throws OrderExecuteException
	 * @roseuid 5850B1970103
	 */
	public void cancel() throws OrderExecuteException {

		this.doCancel();

		this.state = State_Canceled;
		if (this.chargeState.equals(ChargeState_Charge)) {
			this.chargeState = ChargeState_NeedBackCharge;
		}
	}

	/**
	 * 作废一条执行条目的回调函数
	 * 
	 * @throws OrderExecuteException
	 */
	protected void doCancel() throws OrderExecuteException {

	}

	/**
	 * 停止一条执行条目 该函数在长期医嘱停止时调用
	 * 
	 * @throws OrderExecuteException
	 */
	public void stop() throws OrderExecuteException {

		doStop();

		if (this.state.equals(State_NeedSend)
				|| this.state.equals(State_NeedExecute)
				|| this.state.equals(State_Executing)) {
			this.state = State_Stoped;
		}
	}

	/**
	 * 停止一条执行条目回调函数
	 * 
	 * @throws OrderExecuteException
	 */
	protected void doStop() throws OrderExecuteException {

	}

	/**
	 * @roseuid 584F62CB0254
	 */
	public void save() {
		this.getService(OrderExecuteRepo.class).save(this);
	}

	private Float calAmout(OrderExecuteChargeItemRecord chargeItemRecord) {
		if (chargeItemRecord.getCount() == null
				|| chargeItemRecord.getCount() == 0) {
			return chargeItemRecord.getChargeItem().getPrice();
		} else {
			return chargeItemRecord.getChargeItem().getPrice()
					* chargeItemRecord.getCount();
		}
	}

	/**
	 * 填充计划执行时间
	 * 
	 * @param planStartDate
	 * @param planEndDate
	 */
	public void fillPlanDate(Date planStartDate, Date planEndDate) {
		if (this.planStartDate == null) {
			this.planStartDate = planStartDate;
		}
		if (this.planEndDate == null) {
			this.planEndDate = planEndDate;
		}
	}

	public OrderExecute() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public OrderExecuteTeam getTeam() {
		return team;
	}

	public void setTeam(OrderExecuteTeam team) {
		this.team = team;
	}

	public CompsiteOrder getCompsiteOrder() {
		return compsiteOrder;
	}

	public void setCompsiteOrder(CompsiteOrder compsiteOrder) {
		this.compsiteOrder = compsiteOrder;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

	public boolean isAlone() {
		return isAlone;
	}

	public void setAlone(boolean isAlone) {
		this.isAlone = isAlone;
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
		if (actualExecutor != null) {
			this.actualExecutorName = actualExecutor.getName();
		}
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderExecuteChargeItemRecord> getChargeItemRecords() {
		return chargeItemRecords;
	}

	public void setChargeItemRecords(
			List<OrderExecuteChargeItemRecord> chargeItemRecords) {
		this.chargeItemRecords = chargeItemRecords;
	}

	public void addChargeItemRecord(
			OrderExecuteChargeItemRecord chargeItemRecord) {
		if (this.chargeItemRecords == null) {
			this.chargeItemRecords = new ArrayList<OrderExecuteChargeItemRecord>();
		}
		this.chargeItemRecords.add(chargeItemRecord);
	}

	public void addChargeItem(ChargeItem chargeItem) {

		OrderExecuteChargeItemRecord record = new OrderExecuteChargeItemRecord();

		record.setChargeItem(chargeItem);
		record.setCount(this.getOrder().getCount());
		record.setOrderExecute(this);

		this.addChargeItemRecord(record);
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

	public Dept getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Dept belongDept) {
		this.belongDept = belongDept;
		if (belongDept != null) {
			this.belongDeptName = belongDept.getName();
		}
	}

	public Dept getExecuteDept() {
		return executeDept;
	}

	public void setExecuteDept(Dept executeDept) {
		this.executeDept = executeDept;
		if (executeDept != null) {
			this.executeDeptName = executeDept.getName();
		}
	}

	public Dept getChargeDept() {
		return chargeDept;
	}

	public void setChargeDept(Dept chargeDept) {
		this.chargeDept = chargeDept;
		if (chargeDept != null) {
			this.chargeDeptName = chargeDept.getName();
		}
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
		if (visit != null) {
			this.visitName = visit.getName();
		}
	}

	public List<OrderExecute> getTeamOrderExecutes() {
		return this.team.getExecutes();
	}

	public OrderExecute getPrevious() {
		return previous;
	}

	public void setPrevious(OrderExecute previous) {
		this.previous = previous;
	}

	public OrderExecute getNext() {
		return next;
	}

	public void setNext(OrderExecute next) {
		this.next = next;
	}

	public String getActualExecutorName() {
		return actualExecutorName;
	}

	public void setActualExecutorName(String actualExecutorName) {
		this.actualExecutorName = actualExecutorName;
	}

	public String getBelongDeptName() {
		return belongDeptName;
	}

	public void setBelongDeptName(String belongDeptName) {
		this.belongDeptName = belongDeptName;
	}

	public String getExecuteDeptName() {
		return executeDeptName;
	}

	public void setExecuteDeptName(String executeDeptName) {
		this.executeDeptName = executeDeptName;
	}

	public String getChargeDeptName() {
		return chargeDeptName;
	}

	public void setChargeDeptName(String chargeDeptName) {
		this.chargeDeptName = chargeDeptName;
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	/**
	 * 根据系统时间更新执行条目状态
	 * 
	 * @throws OrderExecuteException
	 */
	void updateState() throws OrderExecuteException {
		Date sysDate = DateUtil.getSysDate();
		Date startDate = DateUtil.addDay(DateUtil.getSysDateStart(), 1);
		if (this.planStartDate != null && this.planStartDate.before(startDate)) {
			this.doExecuteBefore();
			this.state = State_Executing;
			this.startDate = sysDate;
		} else {
			this.state = State_NeedExecute;
		}
	}

	void updateChargeState() {
		if (this.chargeState == null) {
			if (this.chargeItemRecords == null
					|| this.chargeItemRecords.size() == 0) {
				this.chargeState = ChargeState_NoApply;
			} else {
				this.chargeState = ChargeState_NoCharge;
			}
		}
	}

	void updateCostState() {
		if (this.costState == null) {
			if (this.chargeItemRecords == null
					|| this.chargeItemRecords.size() == 0) {
				this.costState = CostState_NoApply;
			} else {
				this.costState = CostState_NoCost;
			}
		}
	}
}
