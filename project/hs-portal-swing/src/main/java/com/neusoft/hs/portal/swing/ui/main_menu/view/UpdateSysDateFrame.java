package com.neusoft.hs.portal.swing.ui.main_menu.view;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;
import com.neusoft.hs.portal.swing.util.UIUtil;
import com.neusoft.hs.portal.swing.widget.SpinnerDate;

@Component
public class UpdateSysDateFrame extends JFrame {

	private JButton confirmBtn;
	private JButton closeBtn;

	private SpinnerDate sysDateSD;

	private static final int DEFAULT_WIDTH = 400;

	private static final int DEFAULT_HEIGHT = 100;

	public UpdateSysDateFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		setTitle(ConstMessagesCN.Labels.UpdateSysDate);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void initComponents() {

		setLayout(new BorderLayout());

		JPanel workspacePanel = new JPanel(new BorderLayout());

		JLabel sysDateLbl = new JLabel(ConstMessagesCN.Labels.SysDate);
		workspacePanel.add(sysDateLbl, BorderLayout.WEST);

		sysDateSD = new SpinnerDate("yyyy-MM-dd HH:mm");
		workspacePanel.add(sysDateSD, BorderLayout.CENTER);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		confirmBtn = new JButton(ConstMessagesCN.Labels.CONFIRM_BTN);
		buttonPanel.add(confirmBtn);

		closeBtn = new JButton(ConstMessagesCN.Labels.CLOSE_BTN);
		buttonPanel.add(closeBtn);

		add(buttonPanel, BorderLayout.SOUTH);

	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public JButton getCloseBtn() {
		return closeBtn;
	}

	public SpinnerDate getSysDateSD() {
		return sysDateSD;
	}

}
