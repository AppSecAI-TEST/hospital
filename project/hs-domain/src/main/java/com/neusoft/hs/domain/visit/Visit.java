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
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.InPatientDept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

@Entity
@Table(name = "domain_visit")
public class Visit extends IdEntity {

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
	private InPatientDept respDept;

	public static final String State_NeedInitAccount = "待预存费用";

	public static final String State_NeedIntoWard = "待接诊";

	public static final String State_IntoWard = "在病房";

	public static final String State_NeedLeaveHospitalBalance = "待出院结算";

	public static final String State_LeaveHospital = "已出院";

	public ChargeBill initAccount(float balance, AbstractUser user)
			throws HsException {
		if (!Visit.State_NeedInitAccount.equals(state)) {
			throw new HsException("visit=[" + name + "]的状态应为["
					+ Visit.State_NeedInitAccount + "]");
		}

		ChargeBill chargeBill = new ChargeBill();
		chargeBill.setBalance(balance);
		chargeBill.setState(ChargeBill.State_Normal);
		chargeBill.setVisit(this);

		this.setChargeBill(chargeBill);
		this.setState(Visit.State_NeedIntoWard);

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
		if (!State_NeedIntoWard.equals(this.state)) {
			throw new HsException("visit=[" + name + "]的状态应为["
					+ State_NeedIntoWard + "]");
		}

		Date sysDate = DateUtil.getSysDate();
		this.respNurse = new Nurse(receiveVisitVO.getNurseId());
		this.bed = receiveVisitVO.getBed();
		this.state = State_IntoWard;
		this.intoWardDate = sysDate;

		MedicalRecordClip medicalRecordClip = new MedicalRecordClip();
		medicalRecordClip.setVisit(this);
		medicalRecordClip.setState(MedicalRecordClip.State_Normal);

		this.medicalRecordClip = medicalRecordClip;

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
		if (!State_IntoWard.equals(this.state)) {
			throw new HsException("visit=[" + name + "]的状态应为[" + State_IntoWard
					+ "]");
		}

		this.state = State_NeedLeaveHospitalBalance;

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
		if (!State_NeedLeaveHospitalBalance.equals(this.state)) {
			throw new HsException("visit=[" + name + "]的状态应为["
					+ State_NeedLeaveHospitalBalance + "]");
		}

		this.state = State_LeaveHospital;

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_LeaveHospital);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

	}

	/**
	 * @roseuid 585252D80085
	 */
	public void save() {
		this.getService(VisitRepo.class).save(this);
	}

	/**
	 * @roseuid 585394AD004B
	 */
	public void addVisitChargeItem() {

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

	public List<VisitLog> getLogs() {
		return logs;
	}

	public void setLogs(List<VisitLog> logs) {
		this.logs = logs;
	}

	public MedicalRecordClip getMedicalRecordClip() {
		return medicalRecordClip;
	}

	public void setMedicalRecordClip(MedicalRecordClip medicalRecordClip) {
		this.medicalRecordClip = medicalRecordClip;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<VisitChargeItem> getVisitChargeItems() {
		return visitChargeItems;
	}

	public void setVisitChargeItems(List<VisitChargeItem> visitChargeItems) {
		this.visitChargeItems = visitChargeItems;
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
