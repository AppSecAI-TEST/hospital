package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class VoucherTableModel extends DefaultTableModel<Voucher> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Voucher voucher = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return voucher.getId();
		case 1:
			return voucher.getVisitName();
		case 2:
			return voucher.getNumber();
		case 3:
			return DateFormatter.formatDate(voucher.getCreateDate());
		case 4:
			return voucher.getVisit().getState();
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.Visit,
				ConstMessagesCN.Labels.VoucherNumber,
				ConstMessagesCN.Labels.CreateDate, ConstMessagesCN.Labels.State };
	}
}
