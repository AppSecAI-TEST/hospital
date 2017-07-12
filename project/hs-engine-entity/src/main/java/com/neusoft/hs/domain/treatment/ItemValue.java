package com.neusoft.hs.domain.treatment;

import com.neusoft.hs.domain.visit.Visit;

/**
 * 与患者一次就诊有关的项目值
 * 
 * @author kingbox
 *
 */
public interface ItemValue {
	
	public String toString();

	public Visit getVisit();

	public Itemable getItem();

}
