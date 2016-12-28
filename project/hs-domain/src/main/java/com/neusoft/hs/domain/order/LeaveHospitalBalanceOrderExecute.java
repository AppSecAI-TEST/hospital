package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LeaveHospitalBalance")
public class LeaveHospitalBalanceOrderExecute extends OrderExecute {

}
