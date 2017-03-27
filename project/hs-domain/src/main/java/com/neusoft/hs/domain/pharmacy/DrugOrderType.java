//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.Order;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;
import com.neusoft.hs.domain.order.TemporaryOrder;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;

/**
 * 药品医嘱类型
 * 
 * @author kingbox
 *
 */
@Entity
@DiscriminatorValue("Drug")
public class DrugOrderType extends OrderType {

	@OneToOne(fetch = FetchType.EAGER)
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
	public void resolveOrder(OrderTypeApp orderTypeApp) throws OrderException {

		Order order = orderTypeApp.getOrder();
		DrugUseMode drugUseMode = ((DrugOrderTypeApp) orderTypeApp)
				.getDrugUseMode();

		if (order instanceof TemporaryOrder) {
			// 分解执行条目
			drugUseMode.resolve(order, this);
			if (order.getResolveOrderExecutes().size() == 0) {
				throw new OrderException(order, "没有分解出执行条目");
			}
			// 设置执行时间
			for (OrderExecute execute : order.getResolveOrderExecutes()) {
				execute.fillPlanDate(order.getPlanStartDate(),
						order.getPlanStartDate());
			}
		} else {
			LongOrder longorder = (LongOrder) order;
			int resolveDays;
			if (order.isInPatient()) {
				resolveDays = LongOrder.ResolveDays;// 住院长嘱分解指定天数
			} else {
				resolveDays = DateUtil.calDay(longorder.getPlanStartDate(),
						longorder.getPlanEndDate());// 门诊长嘱一次性分解完

			}
			for (int day = 0; day < resolveDays; day++) {
				// 计算执行时间
				List<Date> executeDates = longorder.calExecuteDates(day);

				for (Date executeDate : executeDates) {
					// 清空上一频次的执行条目集合
					order.clearResolveFrequencyOrderExecutes();
					// 分解执行条目
					drugUseMode.resolve(order, this);
					if (order.getResolveFrequencyOrderExecutes().size() == 0) {
						throw new OrderException(order, "没有分解出执行条目");
					}
					// 设置执行时间
					for (OrderExecute execute : order
							.getResolveFrequencyOrderExecutes()) {
						execute.fillPlanDate(executeDate, executeDate);
					}
				}
			}
			if (order.isInPatient()) {
				// 没有分解出执行条目，设置之前分解的最后一条为last
				if (order.getResolveOrderExecutes().size() == 0) {
					OrderExecute lastOrderExecute = order.getLastOrderExecute();
					lastOrderExecute.setLast(true);
					lastOrderExecute.save();
				}
			} else {
				order.getResolveOrderExecutes()
						.get(order.getResolveOrderExecutes().size() - 1)
						.setLast(true);
			}

		}

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
