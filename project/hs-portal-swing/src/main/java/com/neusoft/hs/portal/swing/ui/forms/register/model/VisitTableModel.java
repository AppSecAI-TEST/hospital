package com.neusoft.hs.portal.swing.ui.forms.register.model;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.ui.shared.model.DefaultTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

@Component
public class VisitTableModel extends DefaultTableModel<Visit> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Visit visit = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return visit.getId();
		case 1:
			return visit.getName();
		case 2:
			return DateFormatter.formatDate(visit.getIntoWardDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesEN.Labels.ID,
				ConstMessagesEN.Labels.VisitName,
				ConstMessagesEN.Labels.IntoWardDate };
	}
}
