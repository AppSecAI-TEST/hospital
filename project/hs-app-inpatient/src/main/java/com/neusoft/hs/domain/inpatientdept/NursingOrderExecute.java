package com.neusoft.hs.domain.inpatientdept;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;

@Entity
@DiscriminatorValue("Nursing")
public class NursingOrderExecute extends OrderExecute {

}
