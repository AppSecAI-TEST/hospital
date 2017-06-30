package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class ChargeRecordTableModel extends DefaultTableModel<ChargeRecord> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ChargeRecord chargeRecord = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return chargeRecord.getId();
		case 1:
			return chargeRecord.getChargeItemName();
		case 2:
			return chargeRecord.getAmount();
		case 3:
			return chargeRecord.getCount();
		case 4:
			return chargeRecord.getPrice();
		case 5:
			return chargeRecord.getType();
		case 6:
			return DateFormatter.formatDate(chargeRecord.getCreateDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.ChargeItemName,
				ConstMessagesCN.Labels.Amount, ConstMessagesCN.Labels.Count,
				ConstMessagesCN.Labels.Price, ConstMessagesCN.Labels.Type,
				ConstMessagesCN.Labels.CreateDate };
	}
}
