package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TransportFluid")
public class TransportFluidOrderExecute extends DrugOrderExecute {
}
