//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\Visit.java

package com.neusoft.hs.domain.visit;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.patient.Patient;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@Table(name = "domain_visit")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Visit extends IdEntity {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 16)
	private String name;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<VisitLog> logs;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<Order> orders;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<Apply> applys;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<OrderExecute> executes;

	@OneToOne(mappedBy = "visit", cascade = { CascadeType.ALL })
	private ChargeBill chargeBill;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	public abstract ChargeBill initAccount(float balance, AbstractUser user)
			throws HsException;

	/**
	 * @roseuid 585252D80085
	 */
	public void save() {
		this.getService(VisitRepo.class).save(this);
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

	public List<VisitLog> getLogs() {
		return logs;
	}

	public void setLogs(List<VisitLog> logs) {
		this.logs = logs;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderExecute> getExecutes() {
		return executes;
	}

	public void setExecutes(List<OrderExecute> executes) {
		this.executes = executes;
	}

	public ChargeBill getChargeBill() {
		return chargeBill;
	}

	public void setChargeBill(ChargeBill chargeBill) {
		this.chargeBill = chargeBill;
	}

	public List<Apply> getApplys() {
		return applys;
	}

	public void setApplys(List<Apply> applys) {
		this.applys = applys;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
