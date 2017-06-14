package com.neusoft.hs.portal.swing.ui.forms.cashier.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.ui.forms.cashier.model.NeedInitAccountVisitTableModel;
import com.neusoft.hs.portal.swing.util.ConstMessagesEN;

@Component
public class CashierTableFrame extends JFrame {

	
	private NeedInitAccountVisitTableModel visitTableModel;

	private JTable table;

	private JButton confirmBtn;
	
	private static final int DEFAULT_WIDTH = 500;
	
	private static final int DEFAULT_HEIGHT = 300;

	@Autowired
	public CashierTableFrame(NeedInitAccountVisitTableModel visitTableModel) {
		
		this.visitTableModel = visitTableModel;
		
		table = new JTable(this.visitTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane paneWithTable = new JScrollPane(table);
		
		add(paneWithTable, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		confirmBtn = new JButton(ConstMessagesEN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);
		
        add(buttonPanel, BorderLayout.SOUTH);
        
        setFrameUp();
	}
	
    private void setFrameUp() {
        setTitle(ConstMessagesEN.Labels.Register);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
    }

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

}
