//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Role;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.SuperEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 医嘱执行条目 由医嘱条目分解而成 对应一个角色的具体执行任务
 * 
 * @author kingbox
 *
 */
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

	private Integer count;

	@NotEmpty(message = "组标识不能为空")
	@Column(name = "team_id", length = 36)
	private String teamId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "compsite_order_id")
	private CompsiteOrder compsiteOrder;

	@Column(name = "previous_id", length = 36)
	private String previousId;

	@Transient
	private OrderExecute previous;

	@Column(name = "next_id", length = 36)
	private String nextId;

	@Transient
	private OrderExecute next;

	@Column(name = "is_last")
	private boolean isLast = false;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "domain_order_execute_charge_item", joinColumns = { @JoinColumn(name = "order_execute_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "charge_item_id", referencedColumnName = "id") })
	private List<ChargeItem> chargeItems;

	@OneToMany(mappedBy = "orderExecute", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<ChargeRecord> chargeRecords;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_dept_id")
	private Dept belongDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_dept_id")
	private Dept executeDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public static final String State_NeedSend = "待发送";

	public static final String State_NeedExecute = "待执行";

	public static final String State_Executing = "执行中";

	public static final String State_Finished = "已完成";

	public static final String State_Canceled = "已作废";

	public static final String State_Stoped = "已停止";

	public static final String ChargeState_NoCharge = "未收费";

	public static final String ChargeState_Charge = "已收费";

	public static final String ChargeState_NeedBackCharge = "待退费";

	public static final String ChargeState_BackCharge = "已退费";

	public static final String CostState_NoCost = "未发生成本";

	public static final String CostState_Cost = "已发生成本";

	public static final String Type_Dispense_Drug = "摆药";

	public static final String Type_Take_Drug = "取药";

	public static final String Type_Configure_Fluid = "配液";

	public static final String Type_Transport_Fluid = "输液";

	public static final String Type_SecondNursing = "二级护理";

	public static final String Type_Enter_Hospital_Register = "入院登记";

	public static final String Type_Leave_Hospital_Register = "出院登记";

	public static final String Type_Leave_Hospital_Balance = "出院结算";

	public static final String Type_Arrange_Inspect = "安排检查";

	public static final String Type_Confirm_Inspect = "确认检查";

	/**
	 * 发送执行条目 当执行条目由其他科室执行时需要通过发送才可以使其执行
	 * 
	 * @param nurse
	 * @throws OrderExecuteException
	 * @roseuid 584F624D0233
	 */
	public void send() throws OrderExecuteException {
		if (!this.state.equals(State_NeedSend)) {
			throw new OrderExecuteException(this, "state=[" + this.state
					+ "]不是" + State_NeedSend);
		}
		this.updateState();
		this.sendDate = DateUtil.getSysDate();

		this.order.setStateDesc(this.type + "执行条目已发送");
	}

	/**
	 * 根据系统时间更新执行条目状态
	 */
	public void updateState() {
		Date sysDate = DateUtil.getSysDate();
		Date startDate = DateUtil.addDay(DateUtil.getSysDateStart(), 1);
		if (this.planStartDate != null && this.planStartDate.before(startDate)) {
			this.state = State_Executing;
			this.startDate = sysDate;
		} else {
			this.state = State_NeedExecute;
		}
	}

	/**
	 * 完成一条执行条目
	 * 
	 * 当有下一条执行条目时将其状态置为【执行中】
	 * 
	 * @param user
	 * @throws OrderExecuteException
	 * @roseuid 584FB6EB03E5
	 */
	public void finish(AbstractUser user) throws OrderExecuteException {

		this.doFinish(user);

		Date sysDate = DateUtil.getSysDate();
		this.endDate = sysDate;
		this.state = State_Finished;
		this.actualExecutor = user;

		OrderExecute next = this.getNext();
		if (next != null) {
			next.setState(OrderExecute.State_Executing);
			next.setStartDate(sysDate);

			next.save();

			this.order.setStateDesc(this.type + "执行条目已完成");
		} else {
			this.order.updateState(this);
		}
	}

	/**
	 * 完成一条执行条目回调函数
	 * 
	 * @param user
	 * @throws OrderExecuteException
	 */
	protected void doFinish(AbstractUser user) throws OrderExecuteException {

	}

	/**
	 * 创建该执行条目对应的费用条目
	 * 
	 * @return
	 * @roseuid 58509B990022
	 */
	public List<ChargeRecord> createChargeRecords() {
		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();

		if (this.chargeItems != null && this.chargeItems.size() > 0) {
			for (ChargeItem chargeItem : this.chargeItems) {
				ChargeRecord chargeRecord = new ChargeRecord();
				chargeRecord.setPrice(chargeItem.getPrice());
				chargeRecord.setCount(count);
				chargeRecord.setAmount(-this.calAmout(chargeItem));
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

	private Float calAmout(ChargeItem chargeItem) {
		if (this.getOrder().getCount() == null
				|| this.getOrder().getCount() == 0) {
			return chargeItem.getPrice();
		} else {
			return chargeItem.getPrice() * this.getOrder().getCount();
		}
	}

	public void fillPlanDate(Date planStartDate, Date planEndDate) {
		this.planStartDate = planStartDate;
		this.planEndDate = planEndDate;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public CompsiteOrder getCompsiteOrder() {
		return compsiteOrder;
	}

	public void setCompsiteOrder(CompsiteOrder compsiteOrder) {
		this.compsiteOrder = compsiteOrder;
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

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
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

	public List<ChargeItem> getChargeItems() {
		return chargeItems;
	}

	public void setChargeItems(List<ChargeItem> chargeItems) {
		this.chargeItems = chargeItems;
	}

	public void addChargeItem(ChargeItem chargeItem) {
		if (this.chargeItems == null) {
			this.chargeItems = new ArrayList<ChargeItem>();
		}
		this.chargeItems.add(chargeItem);
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

	public OrderExecute getNext() {
		if (this.next == null) {
			if (this.nextId != null) {
				this.next = this.getService(OrderExecuteRepo.class).findOne(
						this.nextId);
			}
		}
		return this.next;
	}
}
