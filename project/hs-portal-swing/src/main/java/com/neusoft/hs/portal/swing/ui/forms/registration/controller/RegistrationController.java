package com.neusoft.hs.portal.swing.ui.forms.registration.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.registration.RegistrationAppService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.visit.CreateVisitVO;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.registration.view.CreateVoucherFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.OutPatientPlanRecordComboBoxModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class RegistrationController extends AbstractFrameController {

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	@Autowired
	private RegistrationAppService registrationAppService;

	@Autowired
	private CreateVoucherFrame createVoucherFrame;

	@PostConstruct
	private void prepareListeners() {

		registerAction(createVoucherFrame.getConfirmBtn(),
				(e) -> createVoucher());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOutPatientPlanRecords();
		createVoucherFrame.setVisible(true);
	}

	private void loadOutPatientPlanRecords() {

		List<OutPatientPlanRecord> entities = outPatientPlanDomainService
				.findNotFullPlanRecord(DateUtil.getSysDate());

		OutPatientPlanRecordComboBoxModel boxModel = createVoucherFrame
				.getOutPatientPlanRecordComboBoxModel();
		boxModel.clear();
		boxModel.addElements(entities);
	}

	private void createVoucher() {

		try {
			CreateVisitVO createVisitVO = createVoucherFrame.getCreateVisitVO();

			OutPatientPlanRecord planRecord = createVoucherFrame
					.getOutPatientPlanRecordComboBoxModel().getSelectedItem();

			registrationAppService.register(createVisitVO, planRecord.getId(),
					UserUtil.getUser());

			createVoucherFrame.dispose();
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

}
