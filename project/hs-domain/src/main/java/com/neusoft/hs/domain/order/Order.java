//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\Order.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@Table(name = "domain_order")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Order extends IdEntity {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 128)
	private String name;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@Column(name = "plan_start_date")
	private Date planStartDate;

	private Integer count;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private OrderType type;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.ALL })
	@OrderBy("planStartDate ASC")
	private List<OrderExecute> orderExecutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Doctor creator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "belong_dept_id")
	private InPatientDept belongDept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "execute_dept_id")
	private Dept executeDept;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Transient
	private String visitId;

	public static final String State_Created = "已创建/待核对";

	public static final String State_Executing = "执行中";

	public static final String State_Finished = "已完成";

	public static final String State_Canceled = "已作废";

	/**
	 * @throws HsException
	 * @roseuid 584E6696009D
	 */
	public void check() throws OrderException {

		Visit visit = this.getService(VisitDomainService.class).find(visitId);
		if (visit == null) {
			throw new OrderException(this, "visitId=[" + visitId + "]不存在");
		}

		this.setVisit(visit);

		if (this.type == null) {
			throw new OrderException(this, "orderType不能为空");
		}

		this.type.check(this);
	}

	public void verify() {
		this.resolve();
		this.setState(State_Executing);
	}

	/**
	 * @roseuid 584F494100C2
	 */
	public void resolve() {
		this.addExecutes(this.type.resolveOrder(this).getExecutes());
	}

	/**
	 * @roseuid 584F5A920055
	 */
	public void addExecutes(List<OrderExecute> orderExecutes) {
		if (this.orderExecutes.size() == 0) {
			this.orderExecutes = orderExecutes;
		} else {
			this.orderExecutes.addAll(orderExecutes);
		}
	}

	/**
	 * @roseuid 584F5C1E019C
	 */
	public void save() {
		this.getService(OrderRepo.class).save(this);
	}

	/**
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

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public List<OrderExecute> getOrderExecutes() {
		return orderExecutes;
	}

	public void setOrderExecutes(List<OrderExecute> orderExecutes) {
		this.orderExecutes = orderExecutes;
	}

	public Doctor getCreator() {
		return creator;
	}

	public void setCreator(Doctor creator) {
		this.creator = creator;
	}

	public InPatientDept getBelongDept() {
		return belongDept;
	}

	public void setBelongDept(InPatientDept belongDept) {
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

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public Dept getExecuteDept() {
		return executeDept;
	}

	public void setExecuteDept(Dept executeDept) {
		this.executeDept = executeDept;
	}
}
