//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
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
@DiscriminatorValue("Drug")
public class DrugOrderType extends OrderType {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_type_id")
	private DrugType drugType;

	@Transient
	private String drugTypeSpecId;

	@Override
	public void check(Order order) throws OrderException {
		if (this.drugType == null) {
			// 通过药品规格计算药品类型
			if (drugTypeSpecId == null) {
				throw new OrderException(order, "drugTypeSpecId不能为空");
			}

			DrugTypeSpec drugTypeSpec = this.getService(
					PharmacyDomainService.class).findDrugTypeSpec(
					drugTypeSpecId);

			if (drugTypeSpec == null) {
				throw new OrderException(order, "drugTypeSpecId=["
						+ drugTypeSpecId + "]不存在");
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
						+ drugTypeSpecId + "]库存不足");
			}

			// 根据计算的药品类型找到合适的医嘱类型
			order.setType(this.drugType.getDrugOrderType());
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
	public List<OrderExecute> resolveOrder(Order order) throws OrderException {

		List<OrderExecute> executes = new ArrayList<OrderExecute>();
		if (order instanceof TemporaryOrder) {
			// 分解执行条目
			List<OrderExecute> tempExecutes = this.getDrugTypeOrderResolver(
					order).resolve(order, this);
			if (tempExecutes.size() == 0) {
				throw new OrderException(order, "没有分解出执行条目");
			}
			// 设置执行时间
			for (OrderExecute execute : tempExecutes) {
				execute.setPlanStartDate(order.getPlanStartDate());
				execute.setPlanEndDate(order.getPlanStartDate());
			}
			// 收集执行条目
			executes.addAll(tempExecutes);
		} else {
			List<OrderExecute> tempExecutes;
			LongOrder longOrder = (LongOrder) order;
			for (int day = 0; day < LongOrder.ResolveDays; day++) {
				// 计算执行时间
				List<LongOrderExecuteDateVO> executeDates = longOrder
						.calExecuteDates(day);

				for (LongOrderExecuteDateVO executeDate : executeDates) {
					// 分解执行条目
					tempExecutes = this.getDrugTypeOrderResolver(order)
							.resolve(order, this);
					if (tempExecutes.size() == 0) {
						throw new OrderException(order, "没有分解出执行条目");
					}
					// 设置执行时间
					for (OrderExecute execute : tempExecutes) {
						execute.setPlanStartDate(executeDate.getPlanStartDate());
						execute.setPlanEndDate(executeDate.getPlanStartDate());
					}
					tempExecutes.get(tempExecutes.size() - 1).setLast(
							executeDate.isLast());
					// 收集执行条目
					executes.addAll(tempExecutes);
				}
			}
		}

		return executes;
	}

	private DrugTypeOrderResolver getDrugTypeOrderResolver(Order order)
			throws OrderException {
		if (order.getUseType().equals(Order.UserType_Oral)) {
			return new OralOrderResolver();
		} else if (order.getUseType().equals(Order.UserType_Infusion)) {
			return new InfusionOrderResolver();
		} else {
			throw new OrderException(order, "医嘱用法有问题userType=["
					+ order.getUseType() + "]");
		}
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
