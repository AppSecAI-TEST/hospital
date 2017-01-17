package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.platform.util.DateUtil;

@Entity
@DiscriminatorValue("9H15H")
public class OrderFrequencyType9H15H extends OrderFrequencyType {

	public OrderFrequencyType9H15H() {
		super("每天2次/早9/下3", "每天2次/早9/下3", "每天2次/早9/下3");
	}

	@Override
	public List<Date> calExecuteDates(LongOrder order, Date currentDate) {

		List<Date> dates = new ArrayList<Date>();
		Date date;

		date = DateUtil.addHour(currentDate, 9);

		if (date.before(order.getPlanEndDate())
				&& date.after(order.getPlanStartDate())) {
			dates.add(date);
		}

		date = DateUtil.addHour(currentDate, 15);

		if (date.before(order.getPlanEndDate())
				&& date.after(order.getPlanStartDate())) {
			dates.add(date);
		}

		return dates;
	}

}
