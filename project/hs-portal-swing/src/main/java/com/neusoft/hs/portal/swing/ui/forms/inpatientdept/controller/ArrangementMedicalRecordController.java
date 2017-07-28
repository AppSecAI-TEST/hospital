package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

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

import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.application.medicalrecord.MedicalRecordAppService;
import com.neusoft.hs.domain.inspect.InspectDomainService;
import com.neusoft.hs.domain.medicalrecord.MedicalRecord;
import com.neusoft.hs.domain.medicalrecord.MedicalRecordType;
import com.neusoft.hs.domain.organization.AbstractUser;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.domain.visit.VisitDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.businessview.visit.VisitBusinessView;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.ArrangementMedicalRecordFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.MedicalRecordTableModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ArrangementMedicalRecordController extends AbstractFrameController {

	@Autowired
	private ArrangementMedicalRecordFrame arrangementMedicalRecordFrame;

	@Autowired
	private InPatientAppService inPatientAppService;

	@Autowired
	private InspectDomainService inspectDomainService;

	@Autowired
	private MedicalRecordAppService medicalRecordAppService;
	
	@Autowired
	private VisitBusinessView visitBusinessView;

	@PostConstruct
	private void prepareListeners() {
		registerAction(arrangementMedicalRecordFrame.getVisitCB(),
				(e) -> refreshMedicalRecord());
		registerAction(
				arrangementMedicalRecordFrame.getCreateInWardRecordBtn(),
				(e) -> createInWardRecordMR());
		registerAction(
				arrangementMedicalRecordFrame.getCreateTemporaryOrderListBtn(),
				(e) -> createTemporaryOrderListMR());
		registerAction(
				arrangementMedicalRecordFrame.getCreateInspectResultBtn(),
				(e) -> createInspectResultMR());

		registerAction(arrangementMedicalRecordFrame.getCloseBtn(),
				(e) -> closeWindow());

		registerAction(arrangementMedicalRecordFrame.getTable(),
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

		arrangementMedicalRecordFrame.setVisible(true);
	}

	private void loadVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		
		AbstractUser user = UserUtil.getUser();

		List<Visit> entities = visitBusinessView.findVisits(user, pageable);

		VisitComboBoxModel visitComboBoxModel = arrangementMedicalRecordFrame
				.getVisitComboBoxModel();
		visitComboBoxModel.clear();
		visitComboBoxModel.addElement(null);
		visitComboBoxModel.addElements(entities);
	}

	private void createInspectResultMR() {
		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
			return;
		}
	}

	private void createInWardRecordMR() {

		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
			return;
		}

		try {
			inPatientAppService
					.arrangementMedicalRecord(MedicalRecordType.IntoWardRecord,
							visit, UserUtil.getUser());

			refreshMedicalRecord();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private void createTemporaryOrderListMR() {

		Visit visit = this.getVisit();
		if (visit == null) {
			Notifications.showFormValidationAlert("请选择患者");
			return;
		}

		try {
			inPatientAppService.arrangementMedicalRecord(
					MedicalRecordType.TemporaryOrderList, visit,
					UserUtil.getUser());

			refreshMedicalRecord();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private Visit getVisit() {
		return arrangementMedicalRecordFrame.getVisitComboBoxModel()
				.getSelectedItem();
	}

	private void refreshMedicalRecord() {

		MedicalRecordTableModel medicalRecordTableModel = arrangementMedicalRecordFrame
				.getMedicalRecordTableModel();
		medicalRecordTableModel.clear();

		Visit visit = arrangementMedicalRecordFrame.getVisitComboBoxModel()
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

			MedicalRecordTableModel medicalRecordTableModel = arrangementMedicalRecordFrame
					.getMedicalRecordTableModel();

			MedicalRecord medicalRecord = medicalRecordTableModel
					.getEntityByRow(currentRow);

			try {
				
				medicalRecord = medicalRecordAppService.find(medicalRecord
						.getId());
				
				JFrame viewJFrame = (JFrame) medicalRecord.getRender().play(
						medicalRecord);
				viewJFrame.setVisible(true);
			} catch (HsException e1) {
				e1.printStackTrace();
				Notifications.showFormValidationAlert(e1.getMessage());
			}
		}
	}

	private void closeWindow() {
		arrangementMedicalRecordFrame.dispose();
	}
}
