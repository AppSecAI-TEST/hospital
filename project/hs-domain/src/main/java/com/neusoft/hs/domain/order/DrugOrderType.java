//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.PharmacyDomainService;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("DrugOrderType")
public class DrugOrderType extends OrderType {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Transient
	private String drugTypeSpecId;

	@Override
	public void check(Order order) throws HsException {
		if (drugTypeSpecId == null) {
			throw new HsException("drugTypeSpecId不能为空");
		}

		DrugTypeSpec drugTypeSpec = this
				.getService(PharmacyDomainService.class).findDrugTypeSpec(
						drugTypeSpecId);

		if (drugTypeSpec == null) {
			throw new HsException("drugTypeSpecId=[" + drugTypeSpecId + "]不存在");
		}

		List<DrugType> drugTypes = this.getService(PharmacyDomainService.class)
				.findByDrugTypeSpec(drugTypeSpec);

		L: for (DrugType drugType : drugTypes) {
			if (drugType.getStock() >= order.getCount()) {
				this.drugType = drugType;
				break L;
			}
		}
		if (this.drugType == null) {
			throw new HsException("drugTypeSpecId=[" + drugTypeSpecId + "]库存不足");
		}

		this.drugType.withhold(order.getCount());

	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}

	public String getDrugTypeSpecId() {
		return drugTypeSpecId;
	}

	public void setDrugTypeSpecId(String drugTypeSpecId) {
		this.drugTypeSpecId = drugTypeSpecId;
	}
}
