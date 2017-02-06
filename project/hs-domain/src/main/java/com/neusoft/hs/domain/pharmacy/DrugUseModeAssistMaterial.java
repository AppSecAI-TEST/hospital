package com.neusoft.hs.domain.pharmacy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.AssistMaterial;
import com.neusoft.hs.platform.entity.SuperEntity;

@Entity
@Table(name = "domain_drug_use_mode_charge_item")
public class DrugUseModeAssistMaterial extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "use_mode_id")
	private DrugUseMode orderUseMode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assist_material_id")
	private AssistMaterial assistMaterial;

	@Column(length = 16)
	private String sign;

	@NotEmpty(message = "收费模式不能为空")
	@Column(name = "charge_mode", length = 32)
	private String chargeMode;

	public static final String everyOne = "everyOne";// 按频次收费

	public static final String everyDay = "everyDay";// 按天收费

	public static final String onlyOne = "onlyOne";// 只收一次

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DrugUseMode getOrderUseMode() {
		return orderUseMode;
	}

	public void setOrderUseMode(DrugUseMode orderUseMode) {
		this.orderUseMode = orderUseMode;
	}

	public AssistMaterial getAssistMaterial() {
		return assistMaterial;
	}

	public void setAssistMaterial(AssistMaterial assistMaterial) {
		this.assistMaterial = assistMaterial;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(String chargeMode) {
		this.chargeMode = chargeMode;
	}
}
