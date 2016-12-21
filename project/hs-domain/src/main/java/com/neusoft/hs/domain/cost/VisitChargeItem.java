//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\cost\\VisitChargeItem.java

package com.neusoft.hs.domain.cost;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.entity.IdEntity;

@Entity
@Table(name = "domain_visit_charge_item")
public class VisitChargeItem extends IdEntity {

	@Column(name = "start_date")
	private Date startDate;

	@NotEmpty(message = "状态不能为空")
	@Column(length = 32)
	private String state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	public static final String State_Normal = "正常";

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
		this.getRepo(VisitChargeItemRepo.class).save(this);
	}

}
