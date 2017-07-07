package com.neusoft.hs.portal.swing.ui.forms.recordroom.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.ConstMessagesCN;

@Component
public class PositionFrame extends JFrame {

	private JTextField positionTF;

	private JButton confirmBtn;

	private static final int DEFAULT_WIDTH = 200;

	private static final int DEFAULT_HEIGHT = 100;

	@Autowired
	public PositionFrame() {
		setFrameUp();
		initComponents();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.position);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		JLabel positionJL = new JLabel(ConstMessagesCN.Labels.position);
		workspacePanel.add(positionJL, BorderLayout.WEST);

		positionTF = new JTextField();
		workspacePanel.add(positionTF, BorderLayout.CENTER);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JTextField getPositionTF() {
		return positionTF;
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}
}
