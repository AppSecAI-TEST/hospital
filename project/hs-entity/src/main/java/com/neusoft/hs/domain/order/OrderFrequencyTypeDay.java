package com.neusoft.hs.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Day")
public class OrderFrequencyTypeDay extends OrderFrequencyType {

	public OrderFrequencyTypeDay() {
		super("每天", "每天", "每天");
	}

	@Override
	public List<Date> calExecuteDates(LongOrder order, Date currentDate) {
		List<Date> dates = new ArrayList<Date>();
		dates.add(currentDate);
		return dates;
	}

}
