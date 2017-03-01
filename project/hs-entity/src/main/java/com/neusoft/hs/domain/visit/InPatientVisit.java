//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\entity\\visit\\InPatientVisit.java

package com.neusoft.hs.domain.visit;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.VisitChargeItem;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("InPatient")
public class InPatientVisit extends Visit {
	@Column(length = 16)
	private String bed;

	@Column(name = "into_ward_date")
	private Date intoWardDate;

	@Column(name = "plan_leave_ward_date")
	private Date planLeaveWardDate;

	@Column(name = "leave_ward_date")
	private Date leaveWardDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nurse_id")
	private Nurse respNurse;

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<VisitChargeItem> visitChargeItems;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor respDoctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private InPatientDept respDept;

	public static final String State_NeedInitAccount = "待预存费用";

	public static final String State_NeedIntoWard = "待接诊";

	public static final String State_IntoWard = "在病房";

	public static final String State_NeedLeaveHospitalBalance = "待出院结算";

	public static final String State_LeaveHospital = "已出院";

	public ChargeBill initAccount(float balance, AbstractUser user)
			throws HsException {
		if (!State_NeedInitAccount.equals(this.getState())) {
			throw new HsException("visit=[" + this.getName() + "]的状态应为["
					+ State_NeedInitAccount + "]");
		}

		ChargeBill chargeBill = new ChargeBill();
		chargeBill.setBalance(balance);
		chargeBill.setState(ChargeBill.State_Normal);
		chargeBill.setVisit(this);
		chargeBill.init(user);

		this.setChargeBill(chargeBill);
		this.setState(State_NeedIntoWard);

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_InitAccount);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

		return chargeBill;
	}

	/**
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 5852526403A5
	 */
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws HsException {
		if (!State_NeedIntoWard.equals(this.getState())) {
			throw new HsException("visit=[" + this.getName() + "]的状态应为["
					+ State_NeedIntoWard + "]");
		}

		Date sysDate = DateUtil.getSysDate();
		this.respNurse = new Nurse(receiveVisitVO.getNurseId());
		this.bed = receiveVisitVO.getBed();
		this.setState(State_IntoWard);
		this.intoWardDate = sysDate;

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_IntoWard);
		visitLog.setOperator(user);
		visitLog.setCreateDate(sysDate);

		visitLog.save();
	}

	/**
	 * @throws HsException
	 * @roseuid 58525F0D0273
	 */
	public void leaveWard(AbstractUser user) throws HsException {
		if (!State_IntoWard.equals(this.getState())) {
			throw new HsException("visit=[" + this.getName() + "]的状态应为["
					+ State_IntoWard + "]");
		}

		this.setState(State_NeedLeaveHospitalBalance);

		Date sysDate = DateUtil.getSysDate();

		for (VisitChargeItem visitChargeItem : this.visitChargeItems) {
			visitChargeItem.setState(VisitChargeItem.State_Stop);
			visitChargeItem.setEndDate(sysDate);
		}

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_LeaveWard);
		visitLog.setOperator(user);
		visitLog.setCreateDate(sysDate);

		visitLog.save();

	}

	public void balance(AbstractUser user) throws HsException {
		if (!State_NeedLeaveHospitalBalance.equals(this.getState())) {
			throw new HsException("visit=[" + this.getName() + "]的状态应为["
					+ State_NeedLeaveHospitalBalance + "]");
		}

		this.setState(State_LeaveHospital);

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_LeaveHospital);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public Date getIntoWardDate() {
		return intoWardDate;
	}

	public void setIntoWardDate(Date intoWardDate) {
		this.intoWardDate = intoWardDate;
	}

	public Date getPlanLeaveWardDate() {
		return planLeaveWardDate;
	}

	public void setPlanLeaveWardDate(Date planLeaveWardDate) {
		this.planLeaveWardDate = planLeaveWardDate;
	}

	public Date getLeaveWardDate() {
		return leaveWardDate;
	}

	public void setLeaveWardDate(Date leaveWardDate) {
		this.leaveWardDate = leaveWardDate;
	}

	public Nurse getRespNurse() {
		return respNurse;
	}

	public void setRespNurse(Nurse respNurse) {
		this.respNurse = respNurse;
	}

	public List<VisitChargeItem> getVisitChargeItems() {
		return visitChargeItems;
	}

	public void setVisitChargeItems(List<VisitChargeItem> visitChargeItems) {
		this.visitChargeItems = visitChargeItems;
	}

	public Doctor getRespDoctor() {
		return respDoctor;
	}

	public void setRespDoctor(Doctor respDoctor) {
		this.respDoctor = respDoctor;
	}

	public InPatientDept getRespDept() {
		return respDept;
	}

	public void setRespDept(InPatientDept respDept) {
		this.respDept = respDept;
	}
}
