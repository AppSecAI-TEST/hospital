package com.neusoft.hs.portal.swing.ui.forms.order.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.neusoft.hs.domain.order.OrderExecute;
import com.neusoft.hs.domain.order.TransferDeptConfirmOrderExecute;
import com.neusoft.hs.domain.organization.Doctor;
import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.forms.order.model.DoctorComboBoxModelPanel;
import com.neusoft.hs.portal.swing.ui.forms.order.model.NurseComboBoxModelPanel;
import com.neusoft.hs.portal.swing.ui.shared.model.DoctorComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.NurseComboBoxModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

public class TransferDeptConfirmOrderExecutePanel extends OrderExecutePanel
		implements NurseComboBoxModelPanel, DoctorComboBoxModelPanel {

	private JTextField bedTF;

	private JComboBox<Nurse> resNurseCB;
	private NurseComboBoxModel respNurseComboBoxModel;

	private JComboBox<Doctor> resDoctorCB;
	private DoctorComboBoxModel respDoctorComboBoxModel;

	private static final int TEXT_FIELD_COLUMNS = 20;

	public TransferDeptConfirmOrderExecutePanel(OrderExecute orderExecute) {
		super(orderExecute);

		JLabel bedLbl = new JLabel(ConstMessagesCN.Labels.Bed);
		add(bedLbl);

		bedTF = new JTextField(TEXT_FIELD_COLUMNS);
		add(bedTF);

		JLabel nurseLbl = new JLabel(ConstMessagesCN.Labels.RespNurse);
		add(nurseLbl);

		this.respNurseComboBoxModel = new NurseComboBoxModel();
		resNurseCB = new JComboBox<>(this.respNurseComboBoxModel);
		add(resNurseCB);

		JLabel doctorLbl = new JLabel(ConstMessagesCN.Labels.RespDoctor);
		add(doctorLbl);

		this.respDoctorComboBoxModel = new DoctorComboBoxModel();
		resDoctorCB = new JComboBox<>(this.respDoctorComboBoxModel);
		add(resDoctorCB);
	}

	public NurseComboBoxModel getRespNurseComboBoxModel() {
		return respNurseComboBoxModel;
	}

	public DoctorComboBoxModel getRespDoctorComboBoxModel() {
		return respDoctorComboBoxModel;
	}

	@Override
	public Map<String, Object> getParams() throws UIException {
		Map<String, Object> params = new HashMap<String, Object>();

		if (bedTF.getText() == null || bedTF.getText().length() == 0) {
			throw new UIException("请录入床位号");
		}

		params.put(TransferDeptConfirmOrderExecute.Bed, bedTF.getText());
		params.put(TransferDeptConfirmOrderExecute.RespNurse,
				respNurseComboBoxModel.getSelectedItem());
		params.put(TransferDeptConfirmOrderExecute.RespDoctor,
				respDoctorComboBoxModel.getSelectedItem());

		return params;
	}

}
