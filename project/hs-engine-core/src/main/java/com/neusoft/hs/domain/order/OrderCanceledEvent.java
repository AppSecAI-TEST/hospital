package com.neusoft.hs.domain.order;


public class OrderCanceledEvent extends OrderOperationEvent {

	public OrderCanceledEvent(Object source) {
		super(source);
	}
}