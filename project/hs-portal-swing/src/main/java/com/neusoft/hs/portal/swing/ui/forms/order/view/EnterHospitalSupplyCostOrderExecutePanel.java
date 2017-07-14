package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.neusoft.hs.domain.order.EnterHospitalSupplyCostOrderExecute;
import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.widget.SpinnerNumber;

public class EnterHospitalSupplyCostOrderExecutePanel extends OrderExecutePanel {

	private SpinnerNumber balanceSN;

	public EnterHospitalSupplyCostOrderExecutePanel(OrderExecute orderExecute) {
		super(orderExecute);

		JLabel balanceLbl = new JLabel(ConstMessagesCN.Labels.Balance);
		add(balanceLbl);

		balanceSN = new SpinnerNumber();
		add(balanceSN);
	}

	@Override
	public Map<String, Object> getParams() throws UIException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (balanceSN.getInteger() == null || balanceSN.getInteger() <= 0) {
			throw new UIException("请录入初始化金额");
		}
		params.put(EnterHospitalSupplyCostOrderExecute.Balance,
				Float.valueOf(balanceSN.getInteger()));

		return params;
	}

}
