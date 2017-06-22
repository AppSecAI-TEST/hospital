package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.inpatientdept.InPatientAppService;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.domain.visit.ReceiveVisitVO;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view.ReceiveVisitFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.NurseComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class ReceiveVisitController extends AbstractFrameController {

	@Autowired
	private InPatientAppService inPatientAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@Autowired
	private ReceiveVisitFrame receiveVisitFrame;

	@Autowired
	private VisitTableModel tableModel;

	@Autowired
	private NurseComboBoxModel respNurseComboBoxModel;

	@PostConstruct
	private void prepareListeners() {
		registerAction(receiveVisitFrame.getConfirmBtn(), (e) -> receive());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadReceiveVisits();
		loadNurses();
		receiveVisitFrame.setVisible(true);
	}

	private void loadReceiveVisits() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Visit> entities = inPatientAppService.getNeedReceiveVisits(
				UserUtil.getUser(), pageable);

		tableModel.clear();
		tableModel.addEntities(entities);
	}

	private void loadNurses() throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
		
		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = UserUtil.getUser().getDept();
		depts.add(dept);
		
		List<Nurse> nurses = this.userAdminDomainService.findNurse(depts, pageable);
		respNurseComboBoxModel.clear();
		respNurseComboBoxModel.addElements(nurses);
	}

	private void receive() {
		try {
			Visit visit = receiveVisitFrame.getSelectedVisit();
			String bed = receiveVisitFrame.getBed();
			Nurse respNurse = receiveVisitFrame.getNurse();

			ReceiveVisitVO receiveVisitVO = new ReceiveVisitVO();
			receiveVisitVO.setVisit(visit);
			receiveVisitVO.setBed(bed);
			receiveVisitVO.setNurse(respNurse);

			inPatientAppService.receive(receiveVisitVO, UserUtil.getUser());

			loadReceiveVisits();
			receiveVisitFrame.clearBed();

		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

}
