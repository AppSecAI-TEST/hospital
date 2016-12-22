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

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.pharmacy.DrugTypeRepo;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@Table(name = "domain_order")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Order extends IdEntity {

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@Column(name = "plan_start_date")
	private Date planStartDate;

	private Integer count;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private OrderType type;

	@OneToMany(mappedBy = "order", cascade = { CascadeType.ALL })
	@OrderBy("planStartDate DESC")
	private List<OrderExecute> orderExecutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	private Doctor creator;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Transient
	private String visitId;

	/**
	 * @throws HsException
	 * @roseuid 584E6696009D
	 */
	public void check() throws HsException {

		Visit visit = this.getService(VisitDomainService.class).find(visitId);
		if (visit == null) {
			throw new HsException("visitId=[" + visitId + "]不存在");
		}

		this.setVisit(visit);

		if (this.type == null) {
			throw new HsException("orderType不能为空");
		}

		this.type.check(this);
	}

	/**
	 * @roseuid 584F494100C2
	 */
	public void resolve() {

	}

	/**
	 * @roseuid 584F5A920055
	 */
	public void addExecutes() {

	}

	/**
	 * @roseuid 584F5C1E019C
	 */
	public void save() {
		this.getService(OrderRepo.class).save(this);
	}

	/**
	 * @roseuid 5850AF1E016C
	 */
	public void cancel() {

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

}
