package com.neusoft.hs.portal.swing.ui.forms.outpatientdept.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.application.outpatientdept.OutPatientDeptAppService;
import com.neusoft.hs.domain.outpatientdept.OutPatientDeptException;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanDomainService;
import com.neusoft.hs.domain.outpatientoffice.OutPatientPlanRecord;
import com.neusoft.hs.domain.registration.Voucher;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.outpatientdept.view.OutPatientDeptFrame;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.VoucherTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OutPatientDeptController extends AbstractFrameController {

	@Autowired
	private OutPatientDeptFrame outPatientDeptFrame;

	@Autowired
	private OutPatientPlanDomainService outPatientPlanDomainService;

	@Autowired
	private OutPatientDeptAppService outPatientDeptAppService;

	private OutPatientPlanRecord record;

	@PostConstruct
	private void prepareListeners() {
		registerAction(outPatientDeptFrame.getNextBtn(), (e) -> nextVoucher());
		registerAction(outPatientDeptFrame.getCloseBtn(), (e) -> closeWindow());
	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadVouchers();
		outPatientDeptFrame.setVisible(true);
	}

	private void loadVouchers() throws HsException {
		record = outPatientDeptAppService.findPlanRecord(UserUtil.getUser(),
				DateUtil.getSysDate());
		if (record == null) {
			throw new HsException("您今天没有出诊记录（到统计业务中的门诊出诊计划中创建）");
		}
		List<Voucher> entities = record.getVouchers();

		VoucherTableModel tableModel = outPatientDeptFrame
				.getVoucherTableModel();
		tableModel.clear();
		tableModel.addEntities(entities);
	}

	private void nextVoucher() {
		try {
			boolean rtn = outPatientDeptAppService.nextVoucher(record.getId());
			loadVouchers();
			if (!rtn) {
				Notifications.showFormValidationAlert("没有患者待诊");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private void closeWindow() {
		outPatientDeptFrame.dispose();
	}
}
