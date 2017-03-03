//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutpatientPlanRecord.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_outpatient_plan_record")
public class OutPatientPlanRecord extends IdEntity {

	@NotNull(message = "计划开始时间不能为空")
	@Column(name = "plan_start_date")
	private Date planStartDate;

	@Column(name = "plan_end_date")
	private Date planEndDate;

	@Column(name = "current_number")
	private Integer currentNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private OutPatientRoom room;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_type_id")
	private VoucherType voucherType;

	@OneToMany(mappedBy = "planRecord", cascade = { CascadeType.ALL })
	private List<Voucher> vouchers;

	/**
	 * @roseuid 58B7C8C602F7
	 */
	public OutPatientPlanRecord() {

	}

	/**
	 * @roseuid 58B7D9F402FA
	 */
	public void occupy() {

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

	public Integer getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}

	public OutPatientRoom getRoom() {
		return room;
	}

	public void setRoom(OutPatientRoom room) {
		this.room = room;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(VoucherType voucherType) {
		this.voucherType = voucherType;
	}

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	/**
	 * @roseuid 58B7DA30038A
	 */
	public void save() {

	}

}
