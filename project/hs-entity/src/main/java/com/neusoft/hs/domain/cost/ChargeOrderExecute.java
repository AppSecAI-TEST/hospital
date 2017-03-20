package com.neusoft.hs.domain.cost;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;

@Entity
@DiscriminatorValue("Charge")
public class ChargeOrderExecute extends OrderExecute {
}
