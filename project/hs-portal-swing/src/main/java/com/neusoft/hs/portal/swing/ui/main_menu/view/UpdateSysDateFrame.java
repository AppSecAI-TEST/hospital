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

	private final static int Width = 370;
	private final static int Height = 230;

	public UpdateSysDateFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		Image iconImage = UIUtil.getImage("eve.jpg");
		setIconImage(iconImage);
		setTitle(ConstMessagesCN.Labels.MAIN_MENU);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setResizable(false);
		LookAndFeelUtils.setWindowsLookAndFeel();
		setLayout(new BorderLayout());
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
