//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\OutpatientPlanRecord.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.ArrayList;
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

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.registration.RegistrationDomainService;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_outpatient_plan_record")
public class OutPatientPlanRecord extends IdEntity {

	@NotNull(message = "计划开始时间不能为空")
	@Column(name = "plan_start_date")
	private Date planStartDate;

	@Column(name = "plan_end_date")
	private Date planEndDate;

	private Boolean free;

	@Column(name = "current_allot_number")
	private Integer currentAllotNumber;

	@Column(name = "current_encounter_number")
	private Integer currentEncounterNumber;

	@Column(name = "max_allot_number")
	private Integer maxAllotNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private OutPatientRoom room;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "voucher_type_id")
	private VoucherType voucherType;

	@OneToMany(mappedBy = "planRecord", cascade = { CascadeType.REFRESH })
	private List<Voucher> vouchers;

	private static final int MaxAllotNumber = 50;

	/**
	 * @roseuid 58B7C8C602F7
	 */
	public OutPatientPlanRecord() {
		currentAllotNumber = 0;
		currentEncounterNumber = 1;
		free = true;
		maxAllotNumber = MaxAllotNumber;
	}

	/**
	 * @param voucher
	 * @throws VoucherException
	 * @roseuid 58B7D9F402FA
	 */
	public void occupy(Voucher voucher) throws VoucherException {
		if (this.currentAllotNumber > maxAllotNumber) {
			throw new VoucherException("诊室[" + room.getName() + "]号源已满");
		}
		voucher.setNumber(++currentAllotNumber);
		voucher.setPlanRecord(this);
		if (vouchers == null) {
			vouchers = new ArrayList<Voucher>();
		}
		vouchers.add(voucher);

		Visit visit = voucher.getVisit();

		List<ChargeRecord> chargeRecords = this.createChargeRecords(visit);
		// 生成费用记录
		visit.getChargeBill().charging(chargeRecords);

		this.save();
	}

	private List<ChargeRecord> createChargeRecords(Visit visit) {

		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();
		ChargeRecord chargeRecord;
		ChargeItem chargeItem;

		chargeRecord = new ChargeRecord();

		chargeItem = voucherType.getChargeItem();
		chargeRecord.setPrice(chargeItem.getPrice());
		chargeRecord.setCount(1);
		chargeRecord.setAmount(-chargeItem.getPrice());
		chargeRecord.setChargeItem(chargeItem);
		chargeRecord.setChargeDept(visit.getDept());
		chargeRecord.setBelongDept(visit.getDept());

		chargeRecords.add(chargeRecord);

		return chargeRecords;
	}

	/**
	 * @param voucher
	 * @throws VoucherException
	 * @roseuid 58B7D9F402FA
	 */
	public void repeatOccupy(Voucher voucher) throws VoucherException {
		voucher.setNumber(++currentAllotNumber);
		this.save();
	}

	public boolean nextVoucher() {

		if (currentEncounterNumber > currentAllotNumber) {
			return false;
		}

		// 第一个进入
		if (free) {
			Voucher current = this.getTheVoucher(currentEncounterNumber);
			current.enter();
			free = false;
			return true;
		} else {
			// 当前出
			Voucher current = this.getTheVoucher(currentEncounterNumber);
			current.out();

			currentEncounterNumber++;

			if (currentEncounterNumber > currentAllotNumber) {
				free = true;
				return false;
			}
			// 存在下一个入
			Voucher next = this.getTheVoucher(currentEncounterNumber);
			next.enter();

			return true;
		}

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

	public Integer getCurrentAllotNumber() {
		return currentAllotNumber;
	}

	public void setCurrentAllotNumber(Integer currentAllotNumber) {
		this.currentAllotNumber = currentAllotNumber;
	}

	public Integer getMaxAllotNumber() {
		return maxAllotNumber;
	}

	public void setMaxAllotNumber(Integer maxAllotNumber) {
		this.maxAllotNumber = maxAllotNumber;
	}

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
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

	public Integer getCurrentEncounterNumber() {
		return currentEncounterNumber;
	}

	public void setCurrentEncounterNumber(Integer currentEncounterNumber) {
		this.currentEncounterNumber = currentEncounterNumber;
	}

	/**
	 * @roseuid 58B7DA30038A
	 */
	public void save() {

	}

	private Voucher getTheVoucher(Integer number) {
		return this.getService(RegistrationDomainService.class).getTheVoucher(
				this, number);
	}

}
