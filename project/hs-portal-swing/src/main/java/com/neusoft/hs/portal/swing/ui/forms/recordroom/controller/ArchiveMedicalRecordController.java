package com.neusoft.hs.portal.swing.ui.forms.recordroom.controller;

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
import com.neusoft.hs.application.recordroom.QualityControlAppService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.recordroom.view.ArchiveMedicalRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.MedicalRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ArchiveMedicalRecordController extends AbstractFrameController {
	@Autowired
	private ArchiveMedicalRecordFrame archiveMedicalRecordFrame;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	@Autowired
	private QualityControlAppService qualityControlAppService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(archiveMedicalRecordFrame.getVisitCB(),
				(e) -> refreshMedicalRecord());
		registerAction(archiveMedicalRecordFrame.getArchiveBtn(),
				(e) -> pass());

		registerAction(archiveMedicalRecordFrame.getCloseBtn(),
				(e) -> closeWindow());

		registerAction(archiveMedicalRecordFrame.getTable(),
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						viewMedicalRecord(e);
					}
				});
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		archiveMedicalRecordFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitDomainService.findByStateAndDepts(
				Visit.State_IntoRecordRoom, UserUtil.getUser()
						.getOperationDepts(), pageable);

		VisitComboBoxModel visitComboBoxModel = archiveMedicalRecordFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private Visit getVisit() {
		return archiveMedicalRecordFrame.getVisitComboBoxModel()
				.getSelectedItem();
	}

	private void pass() {

		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
			return;
		}

		try {
			qualityControlAppService.pass(visit.getMedicalRecordClip().getId(),
					UserUtil.getUser());
			

			loadVisits();

			MedicalRecordTableModel medicalRecordTableModel = archiveMedicalRecordFrame
					.getMedicalRecordTableModel();
			medicalRecordTableModel.clear();
			medicalRecordTableModel.fireTableDataChanged();
		} catch (MedicalRecordException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void refreshMedicalRecord() {

		MedicalRecordTableModel medicalRecordTableModel = archiveMedicalRecordFrame
				.getMedicalRecordTableModel();
		medicalRecordTableModel.clear();

		Visit visit = archiveMedicalRecordFrame.getVisitComboBoxModel()
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

			MedicalRecordTableModel medicalRecordTableModel = archiveMedicalRecordFrame
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
		archiveMedicalRecordFrame.dispose();
	}

}
