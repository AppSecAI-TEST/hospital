package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("DayOne")
public class OrderFrequencyTypeDayOne extends OrderFrequencyType {

	private Integer one;

	public OrderFrequencyTypeDayOne() {
		super();
	}

	public OrderFrequencyTypeDayOne(int one) {
		super("每天一次" + one, "每天一次" + one, "每天一次" + one);

		this.one = one;
	}

	@Override
	public List<Date> calExecuteDates(LongOrder order, Date currentDate) {
		List<Date> dates = new ArrayList<Date>();
		if (one == null || one == 0) {
			dates.add(currentDate);
		} else {
			Date date = DateUtil.addHour(currentDate, one);

			if (date.before(order.getPlanEndDate())
					&& date.after(order.getPlanStartDate())) {
				dates.add(date);
			}
		}
		return dates;
	}
}
