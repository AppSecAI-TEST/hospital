//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\Order.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;

/**
 * 一条抽象的医嘱条目 医嘱由医生创建，并关联患者一次就诊 每一条医嘱都有类型，医嘱的分解与类型有关
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_order")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Order extends IdEntity implements OrderCreateCommand {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@NotEmpty(message = "位置类型不能为空")
	@Column(name = "place_type", length = 32)
	private String placeType;

	@NotNull(message = "计划开始时间不能为空")
	@Column(name = "plan_start_date")
	private Date planStartDate;

	private Integer count;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_app_id")
	private OrderTypeApp typeApp;

	@OneToOne(mappedBy = "order", cascade = { CascadeType.ALL })
	private Apply apply;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.REFRESH,
			CascadeType.REMOVE })
	@OrderBy("planStartDate ASC")
	private List<OrderExecute> orderExecutes;

	// 一次解析中一个频次的执行条目集合
	@Transient
	private List<OrderExecute> resolveFrequencyOrderExecutes;

	// 一次解析的执行条目集合（多频次）
	@Transient
	private List<OrderExecute> resolveOrderExecutes;

	// 一次解析的执行条目集合（多频次）
	@Transient
	private List<OrderExecuteTeam> resolveTeams;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "last_execute_id")
	private OrderExecute lastOrderExecute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Doctor creator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_dept_id")
	private Dept belongDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_dept_id")
	private Dept executeDept;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "compsite_order_id")
	private CompsiteOrder compsiteOrder;

	@Transient
	private Map<String, Object> params = new HashMap<String, Object>();

	public static final String State_Created = "已创建/待核对";

	public static final String State_Executing = "执行中";

	public static final String State_Finished = "已完成";

	public static final String State_Canceled = "已作废";

	public static final String State_Stoped = "已停止";

	/**
	 * 创建医嘱条目前的检查回调函数 该回调函数将职责委托给医嘱类型@OrderType完成
	 * 
	 * @throws HsException
	 * @roseuid 584E6696009D
	 */
	protected void check() throws OrderException {

		if (visit == null) {
			throw new OrderException(this, "visit不能为空");
		}

		if (this.typeApp == null) {
			throw new OrderException(this, "orderTypeApp不能为空");
		}

		this.typeApp.check();
	}

	/**
	 * 创建医嘱后的回调函数 该职责将委托给@OrderType完成
	 * 
	 * @throws OrderException
	 */
	protected void create() throws OrderException {
		this.typeApp.create();
	}

	/**
	 * 医嘱核对 当医嘱核对后自动分解 该操作将回调@OrderType.verify
	 * 
	 * @return
	 * @throws OrderException
	 * @throws OrderExecuteException
	 */
	public int verify() throws OrderException, OrderExecuteException {
		this.setState(State_Executing);
		int count = this.resolve();
		this.typeApp.verify();
		return count;
	}

	/**
	 * 医嘱条目分解 该功能将委托给@OrderType.resolveOrder完成
	 * 
	 * @throws OrderException
	 * @throws OrderExecuteException
	 * @roseuid 584F494100C2
	 */
	public int resolve() throws OrderException, OrderExecuteException {
		if (!this.state.equals(State_Executing)) {
			throw new OrderException(this, "医嘱[" + this.getId() + "]的状态为["
					+ this.state + "],不能分解");
		}

		resolveFrequencyOrderExecutes = new ArrayList<OrderExecute>();
		resolveOrderExecutes = new ArrayList<OrderExecute>();
		resolveTeams = new ArrayList<OrderExecuteTeam>();

		this.typeApp.resolveOrder();

		if (resolveOrderExecutes.size() > 0) {
			for (OrderExecute orderExecute : resolveOrderExecutes) {
				// 更新一组执行条目的首条目的状态
				if (orderExecute.getPrevious() == null
						&& !orderExecute.getState().equals(
								OrderExecute.State_NeedSend)) {
					orderExecute.updateState();
				}
				// 设置医嘱组合
				if (this.getCompsiteOrder() != null) {
					orderExecute.setCompsiteOrder(this.getCompsiteOrder());
				}
			}
			// 持久化本次分解
			// orderExecuteTeamRepo.save(resolveTeams);

			this.getService(OrderExecuteTeamRepo.class).save(resolveTeams);

			this.lastOrderExecute = resolveOrderExecutes
					.get(resolveOrderExecutes.size() - 1);
		}

		LogUtil.log(this.getClass(), "系统分解了医嘱条目[{}],得到{}条执行条目", this.getId(),
				resolveOrderExecutes.size());

		return resolveOrderExecutes.size();
	}

	/**
	 * 作废医嘱条目
	 * 
	 * @param doctor
	 * @throws OrderExecuteException
	 * @roseuid 5850AF1E016C
	 */
	public void cancel(Doctor doctor) throws OrderExecuteException {
		for (OrderExecute execute : this.orderExecutes) {
			execute.cancel();
		}
		this.state = State_Canceled;
	}

	/**
	 * 删除医嘱条目 同时删除医嘱关联的申请单 回调@OrderType.delete
	 * 
	 * @throws OrderException
	 */
	public void delete() throws OrderException {
		if (!this.state.equals(State_Created)) {
			throw new OrderException(this, "医嘱[" + this.getId() + "]的状态为["
					+ this.state + "],不能删除");
		}

		if (this.apply != null) {
			this.apply.delete();
		}

		this.typeApp.delete();

		this.getService(OrderRepo.class).delete(this);
	}

	/**
	 * 执行条目执行完成后，更新医嘱条目状态
	 * 
	 * @param orderExecute
	 */
	protected abstract void updateState(OrderExecute orderExecute);

	/**
	 * @roseuid 584F5C1E019C
	 */
	public void save() {
		typeApp.save();
	}

	/**
	 * 给医嘱条目增加执行条目
	 * 
	 * @roseuid 584F5A920055
	 */
	public void addExecuteTeam(OrderExecuteTeam orderExecuteTeam) {
		this.resolveTeams.add(orderExecuteTeam);
		this.resolveFrequencyOrderExecutes.addAll(orderExecuteTeam
				.getExecutes());
		this.resolveOrderExecutes.addAll(orderExecuteTeam.getExecutes());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public OrderTypeApp getTypeApp() {
		return typeApp;
	}

	public void setTypeApp(OrderTypeApp typeApp) {
		this.typeApp = typeApp;
		this.typeApp.setOrder(this);
	}

	public List<OrderExecute> getOrderExecutes() {
		return orderExecutes;
	}

	public List<OrderExecute> getResolveOrderExecutes() {
		return resolveOrderExecutes;
	}

	public List<OrderExecute> getResolveFrequencyOrderExecutes() {
		return resolveFrequencyOrderExecutes;
	}

	public void clearResolveFrequencyOrderExecutes() {
		this.resolveFrequencyOrderExecutes = new ArrayList<OrderExecute>();
	}

	public void setOrderExecutes(List<OrderExecute> orderExecutes) {
		this.orderExecutes = orderExecutes;
	}

	public OrderExecute getLastOrderExecute() {
		return this.lastOrderExecute;
	}

	public Doctor getCreator() {
		return creator;
	}

	public void setCreator(Doctor creator) {
		this.creator = creator;
	}

	public Dept getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(Dept belongDept) {
		this.belongDept = belongDept;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Dept getExecuteDept() {
		return executeDept;
	}

	public void setExecuteDept(Dept executeDept) {
		this.executeDept = executeDept;
	}

	public CompsiteOrder getCompsiteOrder() {
		return compsiteOrder;
	}

	public void setCompsiteOrder(CompsiteOrder compsiteOrder) {
		this.compsiteOrder = compsiteOrder;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	/**
	 * 判断该医嘱是否是在患者住院期间创建的
	 * 
	 * @return
	 */
	public boolean isInPatient() {
		return this.placeType.equals(OrderCreateCommand.PlaceType_InPatient);
	}

	public Object getParam(String key) {
		return params.get(key);
	}

	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}

	@Override
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(this);
		return orders;
	}

	public Apply getApply() {
		return apply;
	}

	public void setApply(Apply apply) {
		this.apply = apply;
		this.apply.setOrder(this);
		this.apply.setVisit(visit);
	}

	/**
	 * 判断指定的医嘱条目是否可以和本医嘱条目组成组合医嘱
	 * 
	 * @param order
	 * @throws OrderException
	 */
	public void compsiteMatch(Order order) throws OrderException {
		if (getClass() != order.getClass()) {
			throw new OrderException(this, "医嘱类型不同");
		}

		if (!this.planStartDate.equals(order.planStartDate)) {
			throw new OrderException(this, "计划开始时间不同");
		}
	}
}
