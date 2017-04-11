//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DispensingDrugOrderExecute.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.hs.domain.order.OrderExecute;

@Entity
@DiscriminatorValue("TaskDrug")
public class TaskDrugOrderExecute extends OrderExecute {
}
