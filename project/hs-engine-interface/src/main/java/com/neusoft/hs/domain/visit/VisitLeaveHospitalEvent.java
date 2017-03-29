//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\visit\\VisitIntoWardedEvent.java

package com.neusoft.hs.domain.visit;

import org.springframework.context.ApplicationEvent;

/**
 * 门诊离院事件
 * 
 * @author kingbox
 *
 */
public class VisitLeaveHospitalEvent extends ApplicationEvent {

	public VisitLeaveHospitalEvent(Object source) {
		super(source);
	}
}
