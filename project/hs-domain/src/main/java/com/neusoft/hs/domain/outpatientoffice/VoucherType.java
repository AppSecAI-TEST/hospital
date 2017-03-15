//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\outpatientoffice\\VoucherType.java

package com.neusoft.hs.domain.outpatientoffice;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.cost.ChargeItem;
import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 挂号类型
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "domain_voucher_type")
public class VoucherType extends SuperEntity {
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 32)
	private String name;

	@OneToMany(mappedBy = "voucherType", cascade = { CascadeType.ALL })
	private List<OutPatientPlanRecord> planRecords;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_item_id")
	private ChargeItem chargeItem;

	/**
	 * @roseuid 58B7C8C6029E
	 */
	public VoucherType() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OutPatientPlanRecord> getPlanRecords() {
		return planRecords;
	}

	public void setPlanRecords(List<OutPatientPlanRecord> planRecords) {
		this.planRecords = planRecords;
	}

	public ChargeItem getChargeItem() {
		return chargeItem;
	}

	public void setChargeItem(ChargeItem chargeItem) {
		this.chargeItem = chargeItem;
	}

}
