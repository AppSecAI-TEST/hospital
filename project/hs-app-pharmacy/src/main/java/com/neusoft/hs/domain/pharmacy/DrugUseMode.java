//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\OrderUseMode.java

package com.neusoft.hs.domain.pharmacy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.platform.entity.SuperEntity;

/**
 * 药品用法 同一药品不同的用法将产生不同的医嘱执行条目
 * 
 * @author kingbox
 *
 */
@Entity
@Table(name = "app_pharmacy_drug_use_mode")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class DrugUseMode extends SuperEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@NotEmpty(message = "代码不能为空")
	@Column(length = 32)
	private String code;

	@NotEmpty(message = "名称不能为空")
	@Column(length = 64)
	private String name;

	@OneToMany(mappedBy = "drugUseMode", cascade = { CascadeType.ALL })
	private List<DrugOrderTypeApp> drugOrderTypeApps;

	@OneToMany(mappedBy = "orderUseMode", cascade = { CascadeType.ALL })
	private List<DrugUseModeAssistMaterial> orderUseModeAssistMaterials;

	/**
	 * 分解药品医嘱条目
	 * 
	 * @param drugOrderType
	 * @param order
	 * @return
	 * @roseuid 586D9239030F
	 */
	public abstract void resolve(Order order, DrugOrderType drugOrderType);

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DrugOrderTypeApp> getDrugOrderTypeApps() {
		return drugOrderTypeApps;
	}

	public void setDrugOrderTypeApps(List<DrugOrderTypeApp> drugOrderTypeApps) {
		this.drugOrderTypeApps = drugOrderTypeApps;
	}

	public List<DrugUseModeAssistMaterial> getOrderUseModeAssistMaterials() {
		return orderUseModeAssistMaterials;
	}

	public void setOrderUseModeAssistMaterials(
			List<DrugUseModeAssistMaterial> orderUseModeAssistMaterials) {
		this.orderUseModeAssistMaterials = orderUseModeAssistMaterials;
	}

	public void addOrderUseModeAssistMaterial(
			DrugUseModeAssistMaterial orderUseModeAssistMaterial) {
		if(this.orderUseModeAssistMaterials == null){
			this.orderUseModeAssistMaterials = new ArrayList<DrugUseModeAssistMaterial>();
		}
		this.orderUseModeAssistMaterials.add(orderUseModeAssistMaterial);
	}

	public DrugUseModeAssistMaterial getTheOrderUseModeChargeItem(String key) {
		if (orderUseModeAssistMaterials == null) {
			return null;
		}
		for (DrugUseModeAssistMaterial orderUseModeAssistMaterial : orderUseModeAssistMaterials) {
			if (orderUseModeAssistMaterial.getSign().equals(key)) {
				return orderUseModeAssistMaterial;
			}
		}
		return null;
	}

	public List<DrugUseModeAssistMaterial> getOrderUseModeChargeItems() {
		return orderUseModeAssistMaterials;
	}

	public void setOrderUseModeChargeItems(
			List<DrugUseModeAssistMaterial> orderUseModeChargeItems) {
		this.orderUseModeAssistMaterials = orderUseModeChargeItems;
	}

}
