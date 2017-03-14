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
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.cost.VisitChargeItem;
import com.neusoft.hs.domain.order.Apply;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.patient.Patient;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.exception.HsException;
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
@Table(name = "domain_visit")
public class Visit extends IdEntity {

	@Column(name = "card_number", length = 64)
	private String cardNumber;

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

	@OneToMany(mappedBy = "visit", cascade = { CascadeType.ALL })
	private List<VisitChargeItem> visitChargeItems;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor respDoctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Dept dept;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

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

	public static final String State_WaitingDiagnose = "待门诊";

	public static final String State_Diagnosing = "门诊就诊中";

	public static final String State_Diagnosed_Executing = "门诊执行中";

	public static final String State_LeaveHospital = "已离院";

	public static final String State_WaitingEnterHospital = "待住院登记";

	public static final String State_NeedInitAccount = "待预存费用";

	public static final String State_NeedIntoWard = "待接诊";

	public static final String State_IntoWard = "在病房";

	public static final String State_NeedLeaveHospitalBalance = "待出院结算";

	public static final String State_OutHospital = "已出院";

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
	public ChargeBill initAccount(float balance, AbstractUser user)
			throws HsException {

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

			chargeBill.addChargeRecord(chargeRecord);
		} else {
			chargeBill.setChargeMode(ChargeBill.ChargeMode_NoPreCharge);
		}

		this.setChargeBill(chargeBill);

		VisitLog visitLog = new VisitLog();
		visitLog.setVisit(this);
		visitLog.setType(VisitLog.Type_InitAccount);
		visitLog.setOperator(user);
		visitLog.setCreateDate(DateUtil.getSysDate());

		visitLog.save();

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
	 * 当患者一次就诊出院时调用的函数 它将修改患者一次就诊的状态为【待出院结算】，并停止入院时的自动收费项目
	 * 
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

	/**
	 * 患者做完出院结算后，通过该函数将患者一次就诊的状态设为【已出院】
	 * 
	 * @param user
	 * @throws HsException
	 */
	public void balance(AbstractUser user) throws HsException {
		if (!State_NeedLeaveHospitalBalance.equals(this.getState())) {
			throw new HsException("visit=[" + this.getName() + "]的状态应为["
					+ State_NeedLeaveHospitalBalance + "]");
		}

		this.setState(State_OutHospital);

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

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
}
