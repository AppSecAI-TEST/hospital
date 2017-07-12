package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class OutPatientPlanRecordTableModel extends
		DefaultTableModel<OutPatientPlanRecord> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OutPatientPlanRecord record = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return record.getId();
		case 1:
			return record.getDoctorName();
		case 2:
			return record.getRoomName();
		case 3:
			return DateFormatter.formatDate(record.getPlanStartDate());
		case 4:
			return DateFormatter.formatDate(record.getPlanEndDate());
		case 5:
			return record.getFree();
		case 6:
			return record.getCurrentAllotNumber();
		case 7:
			return record.getCurrentEncounterNumber();
		case 8:
			return record.getMaxAllotNumber();
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.Doctor, ConstMessagesCN.Labels.Room,
				ConstMessagesCN.Labels.PlanStartDate,
				ConstMessagesCN.Labels.PlanEndDate,
				ConstMessagesCN.Labels.Free,
				ConstMessagesCN.Labels.CurrentAllotNumber,
				ConstMessagesCN.Labels.CurrentEncounterNumber,
				ConstMessagesCN.Labels.MaxAllotNumber };
	}
}
