package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.pharmacy.DispensingDrugOrder;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class DispensingDrugOrderTableModel extends
		DefaultTableModel<DispensingDrugOrder> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DispensingDrugOrder dispensingDrugOrder = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return dispensingDrugOrder.getId();
		case 1:
			return dispensingDrugOrder.getState();
		case 2:
			return dispensingDrugOrder.getExecuteCount();
		case 3:
			return DateFormatter.formatDateTime(dispensingDrugOrder
					.getFinishDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.State,
				ConstMessagesCN.Labels.ExecuteCount,
				ConstMessagesCN.Labels.FinishDate };
	}
}
