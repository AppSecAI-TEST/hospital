//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\Visit.java

package com.neusoft.hs.domain.visit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.hs.domain.cost.ChargeBill;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.VisitChargeItem;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordClip;
import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.AbstractUserDAO;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.patient.Patient;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 患者一次就诊代表了患者与医院之间具有连续行为并有短期目标的一次诊疗活动，它可能是门诊诊疗活动和住院诊疗活动的集合;
 * Visit在患者挂号时创建，或者在患者住院时创建； 一次诊疗活动会关联多次挂号； Visit通过@VisitDomainService.create创建;
 * 每一个Visit都会关联一个@Patient，通过cardNumber进行关联
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_visit", indexes = { @Index(columnList = "card_number") })
public class Visit extends IdEntity {

	@Column(name = "card_number", length = 64)
	private String cardNumber;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@Column(name = "state_desc", length = 128)
	private String stateDesc;

	@Column(length = 16)
	private String bed;

	@Column(name = "create_date")
	private Date createDate;

	private Boolean last;

	@Column(name = "voucher_date")
	private Date voucherDate;

	@Column(name = "into_ward_date")
	private Date intoWardDate;

	@Column(name = "plan_leave_ward_date")
	private Date planLeaveWardDate;

	@Column(name = "leave_ward_date")
	private Date leaveWardDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nurse_id")
	private Nurse respNurse;

	@Column(name = "nurse_name", length = 32)
	private String respNurseName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor respDoctor;

	@Column(name = "doctor_name", length = 32)
	private String respDoctorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Dept dept;

	@Column(name = "dept_name", length = 32)
	private String deptName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "previous_dept_id")
	private Dept previousDept;

	@Column(name = "previous_dept_name", length = 32)
	private String previousDeptName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private Dept area;

	@Column(name = "area_name", length = 32)
	private String areaName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@JsonIgnore
	@OneToMany(mappedBy = "visit")
	private List<VisitChargeItem> visitChargeItems;

	@JsonIgnore
	@OneToMany(mappedBy = "visit")
	@OrderBy("createDate DESC")
	private List<VisitLog> logs;

	@JsonIgnore
	@OneToMany(mappedBy = "visit")
	private List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "visit")
	private List<Apply> applys;

	@JsonIgnore
	@OneToMany(mappedBy = "visit")
	private List<OrderExecute> executes;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_bill_id")
	private ChargeBill chargeBill;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clip_id")
	private MedicalRecordClip medicalRecordClip;

	@JsonIgnore
	@OneToMany(mappedBy = "visit")
	private List<VisitChargeItem> chargeItems;

	public static final String State_WaitingDiagnose = "待门诊";

	public static final String State_Diagnosing = "门诊就诊中";

	public static final String State_Diagnosed_Executing = "门诊执行中";

	public static final String State_LeaveHospital = "已离院";

	public static final String State_WaitingEnterHospital = "待住院登记";

	public static final String State_NeedInitAccount = "待预存费用";

	public static final String State_NeedIntoWard = "待接诊";

	public static final String State_IntoWard = "在病房";

	public static final String State_TransferDepting = "转科中";

	public static final String State_NeedLeaveHospitalBalance = "待出院结算";

	public static final String State_OutHospital = "已出院";

	public static final String State_IntoRecordRoom = "在病案室";

	public static final String State_Archived = "已归档";

	public static List<String> getStates() {
		List<String> visitStates = new ArrayList<String>();

		visitStates.add(Visit.State_WaitingDiagnose);
		visitStates.add(Visit.State_Diagnosing);
		visitStates.add(Visit.State_Diagnosed_Executing);
		visitStates.add(Visit.State_LeaveHospital);
		visitStates.add(Visit.State_WaitingEnterHospital);
		visitStates.add(Visit.State_NeedInitAccount);
		visitStates.add(Visit.State_NeedIntoWard);
		visitStates.add(Visit.State_IntoWard);
		visitStates.add(Visit.State_TransferDepting);
		visitStates.add(Visit.State_NeedLeaveHospitalBalance);
		visitStates.add(Visit.State_OutHospital);
		visitStates.add(Visit.State_IntoRecordRoom);
		visitStates.add(Visit.State_Archived);

		return visitStates;
	}

	/**
	 * 是否初始化过收费单
	 * 
	 * @return
	 */
	public boolean isInitedAccount() {
		return this.chargeBill != null;
	}

	/**
	 * 初始化收费单
	 * 
	 * @param balance
	 *            初始预存金额 当等于0时为非预交金模式，大于0为预交金模式
	 * @param user
	 * @return
	 * @throws HsException
	 */
	public ChargeBill initAccount(float balance, AbstractUser user) {

		ChargeBill chargeBill = new ChargeBill();
		chargeBill.setBalance(balance);
		chargeBill.setState(ChargeBill.State_Normal);
		chargeBill.setVisit(this);
		if (balance > 0) {
			chargeBill.setChargeMode(ChargeBill.ChargeMode_PreCharge);

			ChargeRecord chargeRecord = new ChargeRecord();
			chargeRecord.setAmount(balance);
			chargeRecord.setCreateDate(DateUtil.getSysDate());
			chargeRecord.setHaveCost(false);
			chargeRecord.setChargeDept(user.getDept());
			chargeRecord.setType(ChargeRecord.Type_PreCharge);

			chargeBill.addChargeRecord(chargeRecord);
		} else {
			chargeBill.setChargeMode(ChargeBill.ChargeMode_NoPreCharge);
		}

		chargeBill.save();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_InitAccount);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

		LogUtil.log(this.getClass(), "用户[{}]为患者一次就诊[{}]初始化了收费单,金额为{}",
				user.getId(), this.getName(), balance);

		return chargeBill;
	}

	/**
	 * 患者一次就诊进入病房 该函数将分配床位号、责任护士、修改患者一次就诊的状态为【在病房】
	 * 
	 * @param user
	 * @param receiveVisitVO
	 * @throws HsException
	 * @roseuid 5852526403A5
	 */
	public void intoWard(ReceiveVisitVO receiveVisitVO, AbstractUser user)
			throws VisitException {
		if (!State_NeedIntoWard.equals(this.getState())) {
			throw new VisitException(this, "visit=[%s]的状态应为[%s]",
					this.getName(), State_NeedIntoWard);
		}

		Date sysDate = DateUtil.getSysDate();

		Nurse nurse = this.getService(AbstractUserDAO.class).findNurse(
				receiveVisitVO.getNurse().getId());

		this.setRespNurse(nurse);
		this.setArea(nurse.getDept());

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
	 * 当患者一次就诊出院时调用的函数 它将修改患者一次就诊的状态为【待出院结算】，并停止入院时的自动收费项目
	 * 
	 * @throws HsException
	 * @roseuid 58525F0D0273
	 */
	public void leaveWard(AbstractUser user) throws VisitException {
		if (!State_IntoWard.equals(this.getState())) {
			throw new VisitException(this, "visit=[%s]的状态应为[%s]",
					this.getName(), State_IntoWard);
		}

		this.setState(State_NeedLeaveHospitalBalance);

		Date sysDate = DateUtil.getSysDate();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_OutWard);
		visitLog.setOperator(user);
		visitLog.setCreateDate(sysDate);

		visitLog.save();

	}

	/**
	 * 患者做完出院结算后，通过该函数将患者一次就诊的状态设为【已出院】
	 * 
	 * @param user
	 * @throws HsException
	 */
	public void balance(AbstractUser user) throws VisitException {
		if (!State_NeedLeaveHospitalBalance.equals(this.getState())) {
			throw new VisitException(this, "visit=[%s]的状态应为[%s]",
					this.getName(), State_NeedLeaveHospitalBalance);
		}

		this.setState(State_OutHospital);

		this.chargeBill.balance();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_OutHospital);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

	}

	/**
	 * 转科申请
	 * 
	 * @param user
	 * @throws VisitException
	 */
	public void transferDeptSend(AbstractUser user) throws VisitException {

		if (!State_IntoWard.equals(this.getState())) {
			throw new VisitException(this, "visit=[%s]的状态应为[%s]",
					this.getName(), State_IntoWard);
		}

		this.setState(State_TransferDepting);

		Date sysDate = DateUtil.getSysDate();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_TransferDeptSend);
		visitLog.setOperator(user);
		visitLog.setCreateDate(sysDate);

		visitLog.save();
	}

	/**
	 * 转科确认
	 * 
	 * @param transferDeptVO
	 * @param user
	 * @throws VisitException
	 */
	public void transferDeptConfirm(TransferDeptVO transferDeptVO,
			AbstractUser user) throws VisitException {

		if (!State_TransferDepting.equals(this.getState())) {
			throw new VisitException(this, "visit=[%s]的状态应为[%s]",
					this.getName(), State_TransferDepting);
		}

		this.setState(State_IntoWard);
		this.setDept(transferDeptVO.getDept());
		this.setArea(transferDeptVO.getArea());
		this.setRespDoctor(transferDeptVO.getRespDoctor());
		this.setRespNurse(transferDeptVO.getNurse());
		this.setBed(transferDeptVO.getBed());

		Date sysDate = DateUtil.getSysDate();

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_TransferDeptConfirm);
		visitLog.setOperator(user);
		visitLog.setCreateDate(sysDate);

		visitLog.save();
	}

	/**
	 * 门诊离院
	 * 
	 * @param user
	 * @throws VisitException
	 */
	public void leaveHospital(AbstractUser user) throws VisitException {

		if (this.chargeBill.getBalance() != 0L) {
			throw new VisitException(this, "visit=[%s]的收费单余额[%s]不为零",
					this.getName(), this.chargeBill.getBalance());
		}
		this.setState(State_LeaveHospital);

		this.medicalRecordClip.leaveHospital(user);

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_LeaveHospital);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();
	}

	/**
	 * 移交到病案室
	 * 
	 * @param dept
	 * @param user
	 * @throws VisitException
	 */
	public void transferRecordRoom(Dept dept, AbstractUser user)
			throws VisitException {
		if (!this.getState().equals(State_OutHospital)) {
			throw new VisitException(this, "患者[%s]的状态[%s]不是[%s]不能移交档案室", name,
					state, State_OutHospital);
		}
		this.setDept(dept);
		this.setState(Visit.State_IntoRecordRoom);

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_Transfer);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();
	}

	/**
	 * 归档患者一次就诊
	 * 
	 * @param user
	 */
	public void archive(AbstractUser user) {
		this.setState(Visit.State_Archived);

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_Archived);
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public MedicalRecordClip getMedicalRecordClip() {
		return medicalRecordClip;
	}

	public void setMedicalRecordClip(MedicalRecordClip medicalRecordClip) {
		this.medicalRecordClip = medicalRecordClip;
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

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
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
		if (respNurse != null) {
			this.respNurseName = respNurse.getName();
		}
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
		if (respDoctor != null) {
			this.respDoctorName = respDoctor.getName();
		}
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		// 保留原部门
		if (this.dept != null) {
			this.previousDept = this.dept;
			this.previousDeptName = this.dept.getName();
		}
		// 设置部门
		this.dept = dept;
		if (dept != null) {
			this.deptName = dept.getName();
		}
	}

	public Dept getPreviousDept() {
		return previousDept;
	}

	public String getPreviousDeptName() {
		return previousDeptName;
	}

	public Dept getArea() {
		return area;
	}

	public void setArea(Dept area) {
		this.area = area;
		if (area != null) {
			this.areaName = area.getName();
		}
	}

	public List<VisitChargeItem> getChargeItems() {
		return chargeItems;
	}

	public void setChargeItems(List<VisitChargeItem> chargeItems) {
		this.chargeItems = chargeItems;
	}

	public String getRespNurseName() {
		return respNurseName;
	}

	public void setRespNurseName(String respNurseName) {
		this.respNurseName = respNurseName;
	}

	public String getRespDoctorName() {
		return respDoctorName;
	}

	public void setRespDoctorName(String respDoctorName) {
		this.respDoctorName = respDoctorName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return name;
	}

	public void delete() {
		this.getService(VisitRepo.class).delete(this);
	}

}
