package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.pharmacy.ConfigureFluidOrder;
import com.neusoft.hs.domain.pharmacy.DispensingDrugOrder;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class ConfigureFluidOrderTableModel extends
		DefaultTableModel<ConfigureFluidOrder> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ConfigureFluidOrder configureFluidOrder = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return configureFluidOrder.getId();
		case 1:
			return configureFluidOrder.getState();
		case 2:
			return configureFluidOrder.getExecuteCount();
		case 3:
			return DateFormatter.formatDateTime(configureFluidOrder
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
