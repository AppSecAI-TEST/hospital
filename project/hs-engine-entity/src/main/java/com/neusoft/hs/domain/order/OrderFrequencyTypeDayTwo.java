package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("DayTwo")
public class OrderFrequencyTypeDayTwo extends OrderFrequencyType {

	private Integer one;

	private Integer two;

	public OrderFrequencyTypeDayTwo() {
		super();
	}

	public OrderFrequencyTypeDayTwo(int one, int two) {
		super("每天2次/" + one + "/" + two, "每天2次/" + one + "/" + two, "每天2次/"
				+ one + "/" + two);

		this.one = one;
		this.two = two;
	}

	@Override
	public List<Date> calExecuteDates(LongOrder order, Date currentDate) {

		List<Date> dates = new ArrayList<Date>();
		Date date;

		date = DateUtil.addHour(currentDate, one);

		if ((order.getPlanEndDate() == null || order.getPlanEndDate() != null
				&& date.before(order.getPlanEndDate()))
				&& date.after(order.getPlanStartDate())) {
			dates.add(date);
		}

		date = DateUtil.addHour(currentDate, two);

		if ((order.getPlanEndDate() == null || order.getPlanEndDate() != null
				&& date.before(order.getPlanEndDate()))
				&& date.after(order.getPlanStartDate())) {
			dates.add(date);
		}

		return dates;
	}

}
