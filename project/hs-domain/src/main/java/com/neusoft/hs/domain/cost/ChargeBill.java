//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeBill.java

package com.neusoft.hs.domain.cost;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_charge_bill")
public class ChargeBill extends IdEntity {

	private float balance;

	private String type;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@OneToMany(mappedBy = "chargeBill", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<ChargeRecord> chargeRecords;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public static final String State_Normal = "正常";

	/**
	 * @roseuid 5850A31301AE
	 */
	public void addChargeRecord(ChargeRecord chargeRecord) {
		this.chargeRecords.add(chargeRecord);
	}

	/**
	 * @param chargeRecords2
	 * @roseuid 5850A3D500DE
	 */
	public void charging(List<ChargeRecord> chargeRecords) {

		if (chargeRecords.size() == 0) {
			this.chargeRecords = chargeRecords;
		} else {
			this.chargeRecords.addAll(chargeRecords);
		}

		float theBalance = 0F;
		for (ChargeRecord chargeRecord : chargeRecords) {
			chargeRecord.setChargeBill(this);
			theBalance += chargeRecord.getAmount();
		}

		this.balance -= theBalance;

	}

	/**
	 * @roseuid 5850A40703E7
	 */
	public void save() {
		this.getService(ChargeBillRepo.class).save(this);
	}

	/**
	 * @param chargeRecords
	 * @roseuid 5850BDE60140
	 */
	public void unCharging(List<ChargeRecord> chargeRecords) {
		float balance = 0F;
		ChargeRecord newChargeRecord = null;
		for (ChargeRecord chargeRecord : chargeRecords) {
			newChargeRecord = chargeRecord.undo();
			this.addChargeRecord(newChargeRecord);
			balance += newChargeRecord.getAmount();
		}

		this.balance -= balance;

	}

	/**
	 * @roseuid 5853417903C7
	 */
	public void balance() {

	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(List<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

}
