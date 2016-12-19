//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\Visit.java

package com.neusoft.hs.domain.visit;

import java.util.Date;
import java.util.List;

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

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.VisitChargeItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_visit")
public class Visit extends SuperEntity {

	@NotEmpty(message = "名称不能为空")
	@Column(length = 16)
	private String name;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@Column(length = 16)
	private String bed;

	@Column(name = "into_ward_date")
	private Date intoWardDate;

	@Column(name = "leave_ward_date")
	private Date leaveWardDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nurse_id")
	private Nurse respNurse;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<VisitLog> logs;

	@OneToOne(mappedBy = "visit", cascade = { CascadeType.ALL })
	private MedicalRecordClip medicalRecordClip;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<Order> orders;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<VisitChargeItem> visitChargeItems;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<OrderExecute> executes;

	@OneToOne(mappedBy = "visit", cascade = { CascadeType.ALL })
	private ChargeBill chargeBill;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor respDoctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Dept respDept;

	/**
	 * @roseuid 58573EC602EB
	 */
	public Visit() {

	}

	/**
	 * @roseuid 584E0FAD00A9
	 */
	public void setState() {

	}

	/**
	 * @roseuid 584E13DB03E1
	 */
	public void setNurse() {

	}

	/**
	 * @roseuid 584E14180159
	 */
	public void setBed() {

	}

	/**
	 * @roseuid 584E17B0019F
	 */
	public void setIntoWardDate() {

	}

	/**
	 * @roseuid 5852526403A5
	 */
	public void intoWard() {

	}

	/**
	 * @roseuid 585252D80085
	 */
	public void save() {

	}

	/**
	 * @roseuid 58525F0D0273
	 */
	public void leaveWard() {

	}

	/**
	 * @roseuid 58525F4D0122
	 */
	public void setLeaveWardDate() {

	}

	/**
	 * @roseuid 58537EBF0298
	 */
	public void setRespDept() {

	}

	/**
	 * @roseuid 58537EE0008F
	 */
	public void setDoctor() {

	}

	/**
	 * @roseuid 585381610380
	 */
	public void setChargeBill() {

	}

	/**
	 * @roseuid 585394AD004B
	 */
	public void addVisitChargeItem() {

	}
}
