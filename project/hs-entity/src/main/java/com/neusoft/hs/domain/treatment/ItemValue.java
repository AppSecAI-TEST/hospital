package com.neusoft.hs.domain.treatment;

import com.neusoft.hs.domain.visit.Visit;

public interface ItemValue {
	
	public String toString();

	public Visit getVisit();

	public Itemable getItem();

}
