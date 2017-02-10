package com.neusoft.hs.portal.swing.ui.reports.register.model;

import org.springframework.stereotype.Component;

import com.neusoft.hs.application.register.RegisterCount;
import com.neusoft.hs.portal.swing.ui.shared.model.DefaultTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class RegisterReportModel extends DefaultTableModel<RegisterCount> {

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesEN.Labels.PAYMENT_METHOD,
				ConstMessagesEN.Labels.PAYMENT_COUNT };
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		RegisterCount registerCount = entities.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return registerCount.getInPatientDept().getName();
		case 1:
			return registerCount.getCount();
		default:
			return "";
		}
	}

}
