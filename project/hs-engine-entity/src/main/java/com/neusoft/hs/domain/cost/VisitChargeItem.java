//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\VisitChargeItem.java

package com.neusoft.hs.domain.cost;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 与患者一次就诊关联的自动收费项目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_cost_visit_charge_item")
public class VisitChargeItem extends IdEntity {

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public static final String State_Normal = "正常";

	public static final String State_Stop = "已停止";

	/**
	 * 生成收费条目
	 * 
	 * @throws CostException
	 */
	public void charge() throws CostException {

		if (chargeItem.getChargingMode().equals(ChargeItem.ChargingMode_Day)) {
			Date sysDate = DateUtil.getSysDate();
			Date startDate = DateUtil.getSysDateStart();
			Date endDate = DateUtil.addDay(startDate, 1);
			List<ChargeRecord> oldChargeRecords = this.getService(
					ChargeRecordRepo.class)
					.findByVisitAndChargeItemAndCreateDate(visit, chargeItem,
							startDate, endDate);
			if (oldChargeRecords.size() > 0) {
				throw new CostException("患者[%s]的收费项目[%s]在[%s]时间的已经收费",
						visit.getName(), chargeItem.getName(),
						DateUtil.toString(sysDate));
			}
		}

		List<ChargeRecord> chargeRecords = new ArrayList<ChargeRecord>();
		ChargeRecord chargeRecord;

		chargeRecord = new ChargeRecord();

		chargeRecord.setPrice(chargeItem.getPrice());
		chargeRecord.setCount(1);
		chargeRecord.setAmount(-chargeItem.getPrice());
		chargeRecord.setChargeItem(chargeItem);
		chargeRecord.setChargeDept(visit.getDept());
		chargeRecord.setBelongDept(visit.getDept());

		chargeRecords.add(chargeRecord);

		this.visit.getChargeBill().charging(chargeRecords);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public void save() {
		this.getService(VisitChargeItemRepo.class).save(this);
	}
}
