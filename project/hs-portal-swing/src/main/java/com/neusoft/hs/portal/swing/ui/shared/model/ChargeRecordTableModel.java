package com.neusoft.hs.portal.swing.ui.shared.model;

import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.cost.ChargeRecord;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

@Component
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

	public ChargeRecord getVisit(int rowIndex) {
		return entities.get(rowIndex);
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesEN.Labels.ID,
				ConstMessagesEN.Labels.ChargeItemName,
				ConstMessagesEN.Labels.Amount, ConstMessagesEN.Labels.Count,
				ConstMessagesEN.Labels.Price, ConstMessagesEN.Labels.Type,
				ConstMessagesEN.Labels.CreateDate };
	}
}
