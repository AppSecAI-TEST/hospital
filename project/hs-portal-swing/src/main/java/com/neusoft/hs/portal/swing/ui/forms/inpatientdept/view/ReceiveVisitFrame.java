package com.neusoft.hs.portal.swing.ui.forms.inpatientdept.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.organization.Nurse;
import com.neusoft.hs.domain.visit.Visit;
import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.shared.model.NurseComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class ReceiveVisitFrame extends JFrame {

	private VisitTableModel visitTableModel;

	private JTable table;

	private JTextField bedTF;

	private JComboBox<Nurse> resNurseCB;
	
	private NurseComboBoxModel respNurseComboBoxModel;

	private JButton confirmBtn;

	private static final int DEFAULT_WIDTH = 500;

	private static final int DEFAULT_HEIGHT = 300;

	private static final int TEXT_FIELD_COLUMNS = 20;

	@Autowired
	public ReceiveVisitFrame(VisitTableModel visitTableModel,
			NurseComboBoxModel respNurseComboBoxModel) {

		this.visitTableModel = visitTableModel;
		this.respNurseComboBoxModel = respNurseComboBoxModel;

		table = new JTable(this.visitTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		JLabel bedLbl = new JLabel(ConstMessagesEN.Labels.Bed);
		buttonPanel.add(bedLbl);

		bedTF = new JTextField(TEXT_FIELD_COLUMNS);
		buttonPanel.add(bedTF);

		JLabel nurseLbl = new JLabel(ConstMessagesEN.Labels.RespNurse);
		buttonPanel.add(nurseLbl);

		resNurseCB = new JComboBox<>(this.respNurseComboBoxModel);
		buttonPanel.add(resNurseCB);

		confirmBtn = new JButton(ConstMessagesEN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		add(buttonPanel, BorderLayout.SOUTH);

		setFrameUp();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesEN.Labels.InitBlanace);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public String getBed() throws UIException {
		if (bedTF.getText() == null || bedTF.getText().length() == 0) {
			throw new UIException("请录入床位号");
		}
		return bedTF.getText();
	}

	public Nurse getNurse() throws UIException {
		return respNurseComboBoxModel.getSelectedItem();
	}

	public Visit getSelectedVisit() throws UIException {
		if (this.table.getSelectedRow() == -1) {
			throw new UIException("请选择要初始化的患者");
		}
		return visitTableModel.getVisit(this.table.getSelectedRow());
	}

	public void clearBed() {
		bedTF.setText(null);
	}
}
