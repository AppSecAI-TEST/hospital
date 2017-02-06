//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DrugOrderTypeApp.java

package com.neusoft.hs.domain.pharmacy;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.order.LongOrder;
import com.neusoft.hs.domain.order.OrderException;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.OrderType;
import com.neusoft.hs.domain.order.OrderTypeApp;
import com.neusoft.hs.domain.order.TemporaryOrder;

@Entity
@DiscriminatorValue("Drug")
public class DrugOrderTypeApp extends OrderTypeApp {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_use_mode_id")
	public DrugUseMode drugUseMode;

	public DrugOrderTypeApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugOrderTypeApp(OrderType orderType, DrugUseMode drugUseMode) {
		super(orderType);
		this.drugUseMode = drugUseMode;
	}

	@Override
	public void resolveOrder() throws OrderException {

		if (this.getOrder() instanceof TemporaryOrder) {
			// 分解执行条目
			this.drugUseMode.resolve(this.getOrder(),
					(DrugOrderType) this.getOrderType());
			if (this.getOrder().getResolveOrderExecutes().size() == 0) {
				throw new OrderException(this.getOrder(), "没有分解出执行条目");
			}
			// 设置执行时间
			for (OrderExecute execute : this.getOrder()
					.getResolveOrderExecutes()) {
				execute.fillPlanDate(this.getOrder().getPlanStartDate(), this
						.getOrder().getPlanStartDate());
			}
		} else {
			for (int day = 0; day < LongOrder.ResolveDays; day++) {
				// 计算执行时间
				List<Date> executeDates = ((LongOrder) this.getOrder())
						.calExecuteDates(day);

				for (Date executeDate : executeDates) {
					// 清空上一频次的执行条目集合
					this.getOrder().clearResolveFrequencyOrderExecutes();
					// 分解执行条目
					this.drugUseMode.resolve(this.getOrder(),
							(DrugOrderType) this.getOrderType());
					if (this.getOrder().getResolveFrequencyOrderExecutes()
							.size() == 0) {
						throw new OrderException(this.getOrder(), "没有分解出执行条目");
					}
					// 设置执行时间
					for (OrderExecute execute : this.getOrder()
							.getResolveFrequencyOrderExecutes()) {
						execute.fillPlanDate(executeDate, executeDate);
					}
				}
			}
			// 没有分解出执行条目，设置之前分解的最后一条为last
			if (this.getOrder().getResolveOrderExecutes().size() == 0) {
				OrderExecute lastOrderExecute = this.getOrder()
						.getLastOrderExecute();
				lastOrderExecute.setLast(true);
				lastOrderExecute.save();
			}
		}
	}

	public DrugUseMode getDrugUseMode() {
		return drugUseMode;
	}

	public void setDrugUseMode(DrugUseMode drugUseMode) {
		this.drugUseMode = drugUseMode;
	}

}
