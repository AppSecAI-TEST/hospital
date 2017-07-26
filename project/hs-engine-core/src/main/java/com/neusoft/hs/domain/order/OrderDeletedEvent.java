package com.neusoft.hs.domain.order;


public class OrderDeletedEvent extends OrderOperationEvent {

	public OrderDeletedEvent(Object source) {
		super(source);
	}
}