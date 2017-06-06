//Source file: F:\\my_workspace\\201611������ҽ�������\\DesignModel\\DesignElement\\domain\\order\\DispensingDrugOrderExecute.java

package com.neusoft.hs.domain.order;

import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.pharmacy.DispenseDrugWin;
import com.neusoft.hs.domain.pharmacy.DrugType;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.NumberUtil;

@Entity
@DiscriminatorValue("DispensingDrug")
public class DispensingDrugOrderExecute extends DrugOrderExecute {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dispense_drug_win_id")
	private DispenseDrugWin dispenseDrugWin;

	@Override
	protected void doExecuteBefore() throws OrderExecuteException {
		List<DispenseDrugWin> wins = this.getDrugType().getPharmacy()
				.getDispenseDrugWins();
		if (wins != null && wins.size() > 0) {
			dispenseDrugWin = wins.get(NumberUtil.random(wins.size()));
		}
	}

	@Override
	protected void doFinish(Map<String, Object> params, AbstractUser user)
			throws OrderExecuteException {
		try {
			this.getDrugType().send(getCount());
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	@Override
	protected void doCancel() throws OrderExecuteException {
		try {
			this.getDrugType().unSend(getCount());
		} catch (HsException e) {
			throw new OrderExecuteException(this, e);
		}
	}

	public DispenseDrugWin getDispenseDrugWin() {
		return dispenseDrugWin;
	}

	public void setDispenseDrugWin(DispenseDrugWin dispenseDrugWin) {
		this.dispenseDrugWin = dispenseDrugWin;
	}
}
