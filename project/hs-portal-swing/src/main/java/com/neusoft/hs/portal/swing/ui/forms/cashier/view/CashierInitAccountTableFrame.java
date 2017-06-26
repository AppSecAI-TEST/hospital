package com.neusoft.hs.portal.swing.ui.forms.cashier.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
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

import com.neusoft.hs.portal.framework.exception.UIException;
import com.neusoft.hs.portal.swing.ui.shared.model.VisitTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class CashierInitAccountTableFrame extends JFrame {

	private VisitTableModel visitTableModel;

	private JTable table;

	private JTextField balanceTF;

	private JButton confirmBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	private static final int TEXT_FIELD_COLUMNS = 20;

	@Autowired
	public CashierInitAccountTableFrame(VisitTableModel visitTableModel) {

		this.visitTableModel = visitTableModel;

		initComponents();
		setFrameUp();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.InitBlanace);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		table = new JTable(this.visitTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		add(paneWithTable, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		JLabel nameLbl = new JLabel(ConstMessagesCN.Labels.InitBlanace);
		buttonPanel.add(nameLbl);

		balanceTF = new JTextField(TEXT_FIELD_COLUMNS);
		buttonPanel.add(balanceTF);

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public Float getBalance() throws UIException {
		if (balanceTF.getText() == null || balanceTF.getText().length() == 0) {
			throw new UIException("请录入初始化金额");
		}
		return Float.valueOf(balanceTF.getText());
	}

	public String getSelectedVisitId() throws UIException {
		if (this.table.getSelectedRow() == -1) {
			throw new UIException("请选择要初始化的患者");
		}
		return (String) visitTableModel.getValueAt(this.table.getSelectedRow(),
				0);
	}

	public void clearBalance() {
		balanceTF.setText(null);
	}
}
