package com.neusoft.hs.portal.swing.ui.shared.model;

import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.DateFormatter;

public class MedicalRecordTableModel extends DefaultTableModel<MedicalRecord> {

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		MedicalRecord medicalRecord = entities.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return medicalRecord.getId();
		case 1:
			return medicalRecord.getVisitName();
		case 2:
			return medicalRecord.getTypeName();
		case 3:
			return medicalRecord.getState();
		case 4:
			return medicalRecord.getDoctorName();
		case 5:
			return DateFormatter.formatDate(medicalRecord.getCreateDate());
		default:
			return "";
		}
	}

	@Override
	public String[] getColumnLabels() {
		return new String[] { ConstMessagesCN.Labels.ID,
				ConstMessagesCN.Labels.VisitName, ConstMessagesCN.Labels.Type,
				ConstMessagesCN.Labels.State, ConstMessagesCN.Labels.Doctor,
				ConstMessagesCN.Labels.CreateDate };
	}
}
