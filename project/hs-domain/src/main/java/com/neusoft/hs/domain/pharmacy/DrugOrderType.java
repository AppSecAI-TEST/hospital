//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.pharmacy;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.platform.exception.HsException;

@Entity
@DiscriminatorValue("Drug")
public class DrugOrderType extends OrderType {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Transient
	private DrugTypeSpec drugTypeSpec;

	@Override
	public void check(Order order) throws OrderException {
		if (this.drugType == null) {

			if (drugTypeSpec == null) {
				throw new OrderException(order, "drugTypeSpec不存在");
			}

			List<DrugType> drugTypes = this.getService(
					PharmacyDomainService.class).findByDrugTypeSpec(
					drugTypeSpec);

			L: for (DrugType drugType : drugTypes) {
				if (drugType.getStock() >= order.getCount()) {
					this.drugType = drugType;
					break L;
				}
			}
			if (this.drugType == null) {
				throw new OrderException(order, "drugTypeSpecId=["
						+ drugTypeSpec.getId() + "]库存不足");
			}

			// 根据计算的药品类型找到合适的医嘱类型
			order.getTypeApp().setOrderType(this.drugType.getDrugOrderType());
		}

		// 临嘱预扣
		if (order instanceof TemporaryOrder) {
			try {
				this.drugType.withhold(order.getCount());
			} catch (HsException e) {
				throw new OrderException(order, e);
			}
		}

	}

	@Override
	public void delete(Order order) throws OrderException {
		// 解除临嘱预扣
		if (order instanceof TemporaryOrder) {
			try {
				this.drugType.unWithhold(order.getCount());
			} catch (HsException e) {
				throw new OrderException(order, e);
			}
		}
	}

	@Override
	public void resolveOrder(Order order) throws OrderException {

	}

	public DrugType getDrugType() {
		return drugType;
	}

	public void setDrugType(DrugType drugType) {
		this.drugType = drugType;
	}

	public DrugTypeSpec getDrugTypeSpec() {
		return drugTypeSpec;
	}

	public void setDrugTypeSpec(DrugTypeSpec drugTypeSpec) {
		this.drugTypeSpec = drugTypeSpec;
	}
}
