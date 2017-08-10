package com.neusoft.hs.portal.swing.ui.forms.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JCheckBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.orderexecute.OrderExecuteAppService;
import com.neusoft.hs.domain.organization.Dept;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.organization.UserAdminDomainService;
import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.portal.framework.security.UserUtil;
import com.neusoft.hs.portal.swing.ui.forms.order.model.DoctorComboBoxModelPanel;
import com.neusoft.hs.portal.swing.ui.forms.order.model.NurseComboBoxModelPanel;
import com.neusoft.hs.portal.swing.ui.forms.order.view.EnterHospitalIntoWardOrderExecutePanel;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderExecuteFinishFrame;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderExecuteOpenFrame;
import com.neusoft.hs.portal.swing.ui.forms.order.view.OrderExecutePanel;
import com.neusoft.hs.portal.swing.ui.forms.order.view.TransferDeptConfirmOrderExecutePanel;
import com.neusoft.hs.portal.swing.ui.shared.controller.AbstractFrameController;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.NurseComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.Notifications;

@Controller
public class OrderExecuteFinishController extends AbstractFrameController {

	@Autowired
	private OrderExecuteFinishFrame orderExecuteFinishFrame;

	@Autowired
	private OrderExecuteOpenFrame orderExecuteOpenFrame;

	@Autowired
	private OrderExecuteAppService orderExecuteAppService;

	@Autowired
	private UserAdminDomainService userAdminDomainService;

	@PostConstruct
	private void prepareListeners() {
		registerAction(orderExecuteFinishFrame.getDisplayAllCB(),
				(e) -> loadOrderExecutes());
		registerAction(orderExecuteFinishFrame.getOpenBtn(), (e) -> open());
		registerAction(orderExecuteFinishFrame.getConfirmBtn(), (e) -> finish());
		registerAction(orderExecuteFinishFrame.getCloseBtn(),
				(e) -> closeWindow());

		registerAction(orderExecuteOpenFrame.getFinishBtn(),
				(e) -> openFinish());
		registerAction(orderExecuteOpenFrame.getCloseBtn(),
				(e) -> closeOpenWindow());

	}

	@Override
	public void prepareAndOpenFrame() throws HsException {
		loadOrderExecutes();
		orderExecuteFinishFrame.setVisible(true);
	}

	private void loadOrderExecutes() {
		try {
			Sort sort = new Sort(Direction.DESC, "planStartDate");
			Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, sort);

			List<OrderExecute> entities = null;
			JCheckBox displayAllCB = this.orderExecuteFinishFrame
					.getDisplayAllCB();
			if (displayAllCB.isSelected()) {
				entities = orderExecuteAppService
						.getAllNeedExecuteOrderExecutes(UserUtil.getUser(),
								pageable);
			} else {
				entities = orderExecuteAppService.getNeedExecuteOrderExecutes(
						UserUtil.getUser(), pageable);
			}

			OrderExecuteTableModel orderExecuteTableModel = this.orderExecuteFinishFrame
					.getOrderExecuteTableModel();
			orderExecuteTableModel.clear();
			orderExecuteTableModel.addEntities(entities);
		} catch (HsException e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}

	}

	private void open() {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteFinishFrame
					.getSelectedOrderExecutes();
			if (orderExecutes == null || orderExecutes.size() != 1) {
				Notifications.showFormValidationAlert("请选择一条执行条目");
			}

			this.prepareAndOpenPanel(orderExecutes.get(0));

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void prepareAndOpenPanel(OrderExecute orderExecute)
			throws HsException {

		orderExecuteOpenFrame.init(orderExecute);

		OrderExecutePanel orderExecutePanel = orderExecuteOpenFrame.getPanel();
		if (orderExecutePanel instanceof EnterHospitalIntoWardOrderExecutePanel) {
			loadNurses((EnterHospitalIntoWardOrderExecutePanel) orderExecutePanel);
		} else if (orderExecutePanel instanceof TransferDeptConfirmOrderExecutePanel) {
			loadDoctors((TransferDeptConfirmOrderExecutePanel) orderExecutePanel);
			loadNurses((TransferDeptConfirmOrderExecutePanel) orderExecutePanel);
		}

		orderExecuteOpenFrame.setVisible(true);
	}

	private void loadDoctors(DoctorComboBoxModelPanel panel) throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = panel.getOrderExecute().getExecuteDept();
		depts.add(dept);

		List<Doctor> doctors = this.userAdminDomainService.findDoctor(depts,
				pageable);

		DoctorComboBoxModel doctorComboBoxModel = panel
				.getRespDoctorComboBoxModel();

		doctorComboBoxModel.clear();
		doctorComboBoxModel.addElements(doctors);
	}

	private void loadNurses(NurseComboBoxModelPanel panel) throws HsException {
		Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = UserUtil.getUser().getDept();
		depts.add(dept);

		List<Nurse> nurses = this.userAdminDomainService.findNurse(depts,
				pageable);

		NurseComboBoxModel nurseComboBoxModel = panel
				.getRespNurseComboBoxModel();

		nurseComboBoxModel.clear();
		nurseComboBoxModel.addElements(nurses);
	}

	private void finish() {
		try {
			List<OrderExecute> orderExecutes = this.orderExecuteFinishFrame
					.getSelectedOrderExecutes();

			List<String> orderExecuteIds = new ArrayList<String>();
			for (OrderExecute orderExecute : orderExecutes) {
				orderExecuteIds.add(orderExecute.getId());
			}

			orderExecuteAppService.finish(orderExecuteIds, null,
					UserUtil.getUser());

			loadOrderExecutes();

		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void openFinish() {
		try {
			OrderExecutePanel panel = orderExecuteOpenFrame.getPanel();
			OrderExecute orderExecute = panel.getOrderExecute();

			orderExecuteAppService.finish(orderExecute.getId(),
					panel.getParams(), UserUtil.getUser());

			loadOrderExecutes();
			closeOpenWindow();
		} catch (Exception e) {
			e.printStackTrace();
			Notifications.showFormValidationAlert(e.getMessage());
		}
	}

	private void closeWindow() {
		orderExecuteFinishFrame.dispose();
	}

	private void closeOpenWindow() {
		orderExecuteOpenFrame.dispose();
	}
}
