package com.neusoft.hs.portal.swing.ui.main_menu.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.util.DateUtil;
import com.neusoft.hs.platform.util.SysDateUpdateEvent;
import com.neusoft.hs.platform.util.VersionUtil;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;
import com.neusoft.hs.portal.swing.util.UIUtil;

@Component
public class MainMenuFrame extends JFrame implements
		ApplicationListener<SysDateUpdateEvent> {

	private JButton outPatientBtn;

	private JButton inPatientBtn;

	private JButton maintainBtn;

	private JLabel logoLabel;

	private JLabel sysDateLbl;
	private JButton updateSysDateBtn;

	private final static int Width = 370;
	private final static int Height = 230;

	public MainMenuFrame() {
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

		JPanel workspacePanel = new JPanel(new GridLayout(2, 1, 20, 20));

		JPanel operationPanel = new JPanel();
		operationPanel.setLayout(new GridLayout(1, 3, 20, 20));

		outPatientBtn = new JButton(ConstMessagesCN.Labels.OutPatient);
		inPatientBtn = new JButton(ConstMessagesCN.Labels.InPatient);
		maintainBtn = new JButton(ConstMessagesCN.Labels.REPORTS);

		operationPanel.add(outPatientBtn);
		operationPanel.add(inPatientBtn);
		operationPanel.add(maintainBtn);

		workspacePanel.add(operationPanel);

		JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel versionLbl = new JLabel();
		versionLbl.setText(ConstMessagesCN.Labels.Version + ":"
				+ VersionUtil.getVersion() + " "
				+ ConstMessagesCN.Labels.BuildDate + ":"
				+ VersionUtil.getBuildDate());

		Image iconImage = UIUtil.getImage("hope.jpg");
		ImageIcon background = new ImageIcon(iconImage);// 背景图片

		logoLabel = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		logoLabel.setBounds(230, 0, background.getIconWidth(),
				background.getIconHeight());
		logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		versionPanel.add(logoLabel);
		versionPanel.add(versionLbl);

		workspacePanel.add(versionPanel);

		add(workspacePanel, BorderLayout.CENTER);

		JPanel sysDatePanel = new JPanel(new GridLayout(1, 2, 2, 2));
		sysDateLbl = new JLabel();
		sysDatePanel.add(sysDateLbl);

		updateSysDateBtn = new JButton(ConstMessagesCN.Labels.UpdateSysDate);
		sysDatePanel.add(updateSysDateBtn);

		add(sysDatePanel, BorderLayout.SOUTH);

		refreshDate();
	}

	private void refreshDate() {
		sysDateLbl.setText(ConstMessagesCN.Labels.SimulationSysDate + ":"
				+ DateUtil.toString(DateUtil.getSysDate()));
	}

	@Override
	public void onApplicationEvent(SysDateUpdateEvent event) {
		refreshDate();
	}

	public JButton getOutPatientBtn() {
		return outPatientBtn;
	}

	public JButton getInPatientBtn() {
		return inPatientBtn;
	}

	public JButton getMaintainBtn() {
		return maintainBtn;
	}

	public JButton getUpdateSysDateBtn() {
		return updateSysDateBtn;
	}

	public JLabel getLogoLabel() {
		return logoLabel;
	}
}
