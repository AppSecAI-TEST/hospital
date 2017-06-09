//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderType.java

package com.neusoft.hs.domain.order;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.neusoft.hs.domain.pharmacy.DrugTypeConsumeRecord;
import com.neusoft.hs.domain.pharmacy.DrugTypeSpec;
import com.neusoft.hs.domain.pharmacy.DrugUseMode;
import com.neusoft.hs.domain.pharmacy.PharmacyException;
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
	@JoinColumn(name = "drug_type_spec_id")
	private DrugTypeSpec drugTypeSpec;

	@Override
	public void check(Order order) throws OrderException {
		DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order
				.getTypeApp();
		// 临嘱预扣
		if (order instanceof TemporaryOrder) {
			try {
				drugOrderTypeApp.withhold(drugTypeSpec, order.getCount());
			} catch (PharmacyException e) {
				throw new OrderException(order, e);
			}
		}

	}

	@Override
	public void delete(Order order) throws OrderException {
		// 解除临嘱预扣
		if (order instanceof TemporaryOrder) {
			DrugOrderTypeApp drugOrderTypeApp = (DrugOrderTypeApp) order
					.getTypeApp();
			try {
				drugOrderTypeApp.unWithhold();
			} catch (PharmacyException e) {
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
			drugUseMode.resolve(order);
			if (order.getResolveOrderExecutes().size() == 0) {
				throw new OrderException(order, "没有分解出执行条目");
			}
			// 设置执行时间
			for (OrderExecute execute : order.getResolveOrderExecutes()) {
				execute.fillPlanDate(order.getPlanStartDate(),
						order.getPlanStartDate());
			}
		} else {
			// 长嘱分解
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
					drugUseMode.resolve(order);
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
				// 门诊长嘱，分解的最后一条就是last
				order.getResolveOrderExecutes()
						.get(order.getResolveOrderExecutes().size() - 1)
						.setLast(true);
			}
		}
	}

	public DrugTypeSpec getDrugTypeSpec() {
		return drugTypeSpec;
	}

	public void setDrugTypeSpec(DrugTypeSpec drugTypeSpec) {
		this.drugTypeSpec = drugTypeSpec;
	}
}
