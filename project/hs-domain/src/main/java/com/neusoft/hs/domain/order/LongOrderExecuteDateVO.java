package com.neusoft.hs.domain.order;

import java.util.Date;

public class LongOrderExecuteDateVO {

	private Date planStartDate;

	private boolean isLast = false;

	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

}
