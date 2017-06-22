//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\ChargeBill.java

package com.neusoft.hs.domain.cost;

import java.util.ArrayList;
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

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 收费单 每一个患者一次就诊都关联一个收费单 收费单记录当前余额，并包含多个费用条目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_cost_charge_bill")
public class ChargeBill extends IdEntity {

	private float balance;

	private float consume;

	private String type;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@NotEmpty(message = "收费模式不能为空")
	@Column(length = 32, name = "charge_mode")
	private String chargeMode;

	@OneToMany(mappedBy = "chargeBill", cascade = { CascadeType.ALL })
	@OrderBy("createDate DESC")
	private List<ChargeRecord> chargeRecords;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "visit_name", length = 16)
	private String visitName;

	public static final String State_Normal = "正常";

	public static final String ChargeMode_PreCharge = "预交金模式";

	public static final String ChargeMode_NoPreCharge = "非预交金模式";

	/**
	 * 向收费单增加费用条目（当收费模式为【非预交金模式】时，将创建成对出现的费用条目）
	 * 
	 * @param chargeRecords2
	 * @throws CostException
	 * @roseuid 5850A3D500DE
	 */
	public Float charging(List<ChargeRecord> chargeRecords)
			throws CostException {

		if (this.visit.getState().equals(Visit.State_OutHospital)
				|| this.visit.getState().equals(Visit.State_LeaveHospital)) {
			throw new CostException("患者一次就诊[" + this.visit.getName() + "]状态为["
					+ this.visit.getState() + "],不能增加费用条目");
		}

		float theConsume = 0F;

		ChargeRecord payChargeRecord;
		for (ChargeRecord chargeRecord : chargeRecords) {
			if (chargeRecord.getType() == null) {
				chargeRecord.setType(ChargeRecord.Type_ShouldCharge);
			}
			this.addChargeRecord(chargeRecord);

			if (this.chargeMode.equals(ChargeMode_NoPreCharge)) {
				payChargeRecord = chargeRecord.createPayRecord();
				theConsume += payChargeRecord.getAmount();// 金额为正
				this.addChargeRecord(payChargeRecord);
			}
		}

		if (this.chargeMode.equals(ChargeMode_PreCharge)) {
			float theBalance = 0F;
			for (ChargeRecord chargeRecord : chargeRecords) {
				theBalance += chargeRecord.getAmount();// 金额为负
			}
			this.balance += theBalance;
			theConsume -= theBalance;// 金额为负
		}

		this.consume += theConsume;

		return theConsume;
	}

	/**
	 * 取消收费条目（通过增加反向金额的费用条目实现取消）
	 * 
	 * @param chargeRecords
	 * @roseuid 5850BDE60140
	 */
	public Float unCharging(List<ChargeRecord> chargeRecords) {
		float balance = 0F;
		ChargeRecord newChargeRecord = null;
		for (ChargeRecord chargeRecord : chargeRecords) {
			newChargeRecord = chargeRecord.undo();
			this.addChargeRecord(newChargeRecord);
			balance += newChargeRecord.getAmount();// 金额为正
		}

		this.balance += balance;
		this.consume -= balance;// 金额为正

		return balance;
	}

	public void balance() {
		if (this.balance == 0) {
			return;
		}
		ChargeRecord chargeRecord = new ChargeRecord();
		chargeRecord.setAmount(-this.balance);
		chargeRecord.setType(ChargeRecord.Type_Balance);
		chargeRecord.setHaveCost(false);

		this.addChargeRecord(chargeRecord);

		this.balance = 0;
	}

	/**
	 * 向收费单增加费用条目
	 * 
	 * @roseuid 5850A31301AE
	 */
	public void addChargeRecord(ChargeRecord chargeRecord) {
		if (this.chargeRecords == null) {
			this.chargeRecords = new ArrayList<ChargeRecord>();
		}
		this.chargeRecords.add(chargeRecord);

		chargeRecord.setChargeBill(this);
		if (chargeRecord.getCreateDate() == null) {
			chargeRecord.setCreateDate(DateUtil.getSysDate());
		}
	}

	public String getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
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
		this.visitName = visit.getName();
	}

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public float getConsume() {
		return consume;
	}

	public void setConsume(float consume) {
		this.consume = consume;
	}

	/**
	 * @roseuid 5850A40703E7
	 */
	public void save() {
		this.getService(ChargeBillRepo.class).save(this);
	}

}
