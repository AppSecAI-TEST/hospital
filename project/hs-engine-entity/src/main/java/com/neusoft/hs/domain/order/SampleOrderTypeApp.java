//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\SampleOrderTypeApp.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Sample")
public class SampleOrderTypeApp extends OrderTypeApp {

	public SampleOrderTypeApp() {
		super();
	}

	public SampleOrderTypeApp(OrderType orderType) {
		super(orderType);
	}
}
