package com.neusoft.hs.portal.swing.ui.forms.pharmacy.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.domain.pharmacy.DispenseDrugWin;
import com.neusoft.hs.portal.swing.ui.shared.model.DispenseDrugWinComboBoxModel;
import com.neusoft.hs.portal.swing.ui.shared.model.OrderExecuteTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class PharmacyExecuteFrame extends JFrame {

	JComboBox<DispenseDrugWin> dispenseDrugWinCB;
	DispenseDrugWinComboBoxModel dispenseDrugWinComboBoxModel;

	private OrderExecuteTableModel orderExecuteTableModel;

	protected JTable table;

	private JButton closeBtn;

	private static final int DEFAULT_WIDTH = 800;

	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public PharmacyExecuteFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.PharmacyExecute);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		this.orderExecuteTableModel = new OrderExecuteTableModel();
		table = new JTable(this.orderExecuteTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);

		workspacePanel.add(paneWithTable, BorderLayout.CENTER);

		JPanel operationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel dispenseDrugWinLbl = new JLabel(
				ConstMessagesCN.Labels.DispenseDrugWin);

		this.dispenseDrugWinComboBoxModel = new DispenseDrugWinComboBoxModel();
		dispenseDrugWinCB = new JComboBox<>(dispenseDrugWinComboBoxModel);

		operationPanel.add(dispenseDrugWinLbl);
		operationPanel.add(dispenseDrugWinCB);

		workspacePanel.add(operationPanel, BorderLayout.NORTH);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JComboBox<DispenseDrugWin> getDispenseDrugWinCB() {
		return dispenseDrugWinCB;
	}

	public DispenseDrugWinComboBoxModel getDispenseDrugWinComboBoxModel() {
		return dispenseDrugWinComboBoxModel;
	}

	public OrderExecuteTableModel getOrderExecuteTableModel() {
		return orderExecuteTableModel;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

}
