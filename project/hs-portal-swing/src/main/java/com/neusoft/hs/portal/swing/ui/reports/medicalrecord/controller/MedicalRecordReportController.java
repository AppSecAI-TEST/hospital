package com.neusoft.hs.portal.swing.ui.reports.medicalrecord.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.swing.ui.reports.medicalrecord.view.MedicalRecordReportFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.MedicalRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class MedicalRecordReportController extends AbstractFrameController {

	@Autowired
	private MedicalRecordReportFrame medicalRecordReportFrame;

	
	@Autowired
	private VisitAdminDomainService visitAdminDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(medicalRecordReportFrame.getVisitCB(),
				(e) -> refreshMedicalRecord());

		registerAction(medicalRecordReportFrame.getCloseBtn(),
				(e) -> closeWindow());

		registerAction(medicalRecordReportFrame.getTable(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewMedicalRecord(e);
			}
		});
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		medicalRecordReportFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitAdminDomainService.find(pageable);

		VisitComboBoxModel visitComboBoxModel = medicalRecordReportFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void refreshMedicalRecord() {

		MedicalRecordTableModel medicalRecordTableModel = medicalRecordReportFrame
				.getMedicalRecordTableModel();
		medicalRecordTableModel.clear();

		Visit visit = medicalRecordReportFrame.getVisitComboBoxModel()
				.getSelectedItem();

		if (visit != null) {
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
			List<MedicalRecord> entities = medicalRecordAppService
					.getMedicalRecords(visit, pageable);
			medicalRecordTableModel.addEntities(entities);
		}
	}

	private void viewMedicalRecord(MouseEvent e) {

		if (e.getClickCount() == 2) {
			final JTable table = (JTable) e.getSource();
			int currentRow = table.rowAtPoint(e.getPoint());

			MedicalRecordTableModel medicalRecordTableModel = medicalRecordReportFrame
					.getMedicalRecordTableModel();

			MedicalRecord medicalRecord = medicalRecordTableModel
					.getEntityByRow(currentRow);

			try {
				JFrame viewJFrame = (JFrame) medicalRecord.getRender().play(
						medicalRecord);
				viewJFrame.setVisible(true);
			} catch (MedicalRecordException e1) {
				e1.printStackTrace();
				Notifications.showFormValidationAlert(e1.getMessage());
			}
		}
	}

	private void closeWindow() {
		medicalRecordReportFrame.dispose();
	}
}
