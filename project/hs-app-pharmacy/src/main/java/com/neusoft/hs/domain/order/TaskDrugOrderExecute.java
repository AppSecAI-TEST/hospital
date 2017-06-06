//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DispensingDrugOrderExecute.java

package com.neusoft.hs.domain.order;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.pharmacy.DispenseDrugWin;

@Entity
@DiscriminatorValue("TaskDrug")
public class TaskDrugOrderExecute extends DrugOrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dispense_drug_win_id")
	private DispenseDrugWin dispenseDrugWin;

	@Override
	protected void doExecuteBefore() throws OrderExecuteException {
		OrderExecute previos = this.getPrevious();
		if (previos != null) {
			if (previos instanceof DispensingDrugOrderExecute) {
				DispensingDrugOrderExecute dispensingDrugOrderExecute = (DispensingDrugOrderExecute) previos;
				this.dispenseDrugWin = dispensingDrugOrderExecute
						.getDispenseDrugWin();
			}
			if (previos instanceof DrugOrderExecute) {
				DrugOrderExecute drugOrderExecute = (DrugOrderExecute) previos;
				this.setDrugType(drugOrderExecute.getDrugType());
			}
		}

	}

}
