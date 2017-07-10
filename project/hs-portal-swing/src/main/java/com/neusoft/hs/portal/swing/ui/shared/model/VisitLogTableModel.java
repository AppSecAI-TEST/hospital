package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.visit.VisitLog;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class VisitLogTableModel extends DefaultTableModel<VisitLog> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		VisitLog visitLog = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return visitLog.getId();
		case 1:
			return visitLog.getType();
		case 2:
			return visitLog.getInfo();
		case 3:
			return visitLog.getOperatorName();
		case 4:
			return DateFormatter.formatDate(visitLog.getCreateDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.Type, ConstMessagesCN.Labels.Info,
				ConstMessagesCN.Labels.Operator,
				ConstMessagesCN.Labels.CreateDate };
	}
}
