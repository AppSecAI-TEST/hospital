package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Nursing")
public class NursingOrderExecute extends OrderExecute {



}
