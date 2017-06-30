package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

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
			return visit.getState();
		case 3:
			return DateFormatter.formatDate(visit.getIntoWardDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.VisitName,
				ConstMessagesCN.Labels.State,
				ConstMessagesCN.Labels.IntoWardDate };
	}
}
