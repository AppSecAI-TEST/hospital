package com.neusoft.hs.portal.swing.ui.forms.medicalrecord.controller;

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
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordException;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.businessview.visit.VisitBusinessView;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.medicalrecord.view.CreateMedicalRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.MedicalRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class CreateMedicalRecordController extends AbstractFrameController {

	@Autowired
	private CreateMedicalRecordFrame createMedicalRecordFrame;

	@Autowired
	private VisitDomainService visitDomainService;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;

	@Autowired
	private VisitBusinessView visitBusinessView;

	@PostConstruct
	private void prepareListeners() {
		registerAction(createMedicalRecordFrame.getVisitCB(),
				(e) -> refreshMedicalRecord());

		registerAction(createMedicalRecordFrame.getCreateOutPatientRecordBtn(),
				(e) -> createOutPatientRecordMR());

		registerAction(createMedicalRecordFrame.getCloseBtn(),
				(e) -> closeWindow());

		registerAction(createMedicalRecordFrame.getTable(), new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				viewMedicalRecord(e);
			}
		});
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {

		loadVisits();

		createMedicalRecordFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = visitBusinessView.findVisits(UserUtil.getUser(),
				pageable);

		VisitComboBoxModel visitComboBoxModel = createMedicalRecordFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void createOutPatientRecordMR() {
		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
		}

		try {
			MedicalRecord medicalRecord = medicalRecordAppService.create(
					MedicalRecordType.OutPatientRecord, visit,
					UserUtil.getUser());

			JFrame viewJFrame = (JFrame) medicalRecord.getRender().play(
					medicalRecord);
			viewJFrame.setVisible(true);
		} catch (HsException e1) {
			e1.printStackTrace();
			Notifications.showFormValidationAlert(e1.getMessage());
		}
	}

	private Visit getVisit() {
		return createMedicalRecordFrame.getVisitComboBoxModel()
				.getSelectedItem();
	}

	private void refreshMedicalRecord() {

		MedicalRecordTableModel medicalRecordTableModel = createMedicalRecordFrame
				.getMedicalRecordTableModel();
		medicalRecordTableModel.clear();

		Visit visit = createMedicalRecordFrame.getVisitComboBoxModel()
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

			MedicalRecordTableModel medicalRecordTableModel = createMedicalRecordFrame
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
		createMedicalRecordFrame.dispose();
	}
}
