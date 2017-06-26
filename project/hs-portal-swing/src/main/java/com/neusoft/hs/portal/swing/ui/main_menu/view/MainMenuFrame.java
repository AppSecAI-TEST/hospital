package com.neusoft.hs.portal.swing.ui.main_menu.view;

import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.util.VersionUtil;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

import javax.swing.*;

import java.awt.*;

@Component
public class MainMenuFrame extends JFrame {

	private JButton formsBtn;
	private JButton reportsBtn;

	public MainMenuFrame() {
		setFrameUp();
		initComponents();
		pack();
	}

	private void setFrameUp() {
		getRootPane().setBorder(Borders.createEmptyBorder());
		setTitle(ConstMessagesCN.Labels.MAIN_MENU);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		LookAndFeelUtils.setWindowsLookAndFeel();
		setLayout(new GridLayout(2, 1, 20, 20));
	}

	private void initComponents() {

		JPanel operationPanel = new JPanel();
		operationPanel.setLayout(new GridLayout(1, 2, 20, 20));

		formsBtn = new JButton(ConstMessagesCN.Labels.FORMS);
		reportsBtn = new JButton(ConstMessagesCN.Labels.REPORTS);

		operationPanel.add(formsBtn);
		operationPanel.add(reportsBtn);

		add(operationPanel);

		JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel versionLbl = new JLabel();
		versionLbl.setText(ConstMessagesCN.Labels.Version + ":"
				+ VersionUtil.getVersion() + " "
				+ ConstMessagesCN.Labels.Version + ":"
				+ VersionUtil.getBuildDate());

		versionPanel.add(versionLbl);

		add(versionPanel);

	}

	public JButton getFormsBtn() {
		return formsBtn;
	}

	public JButton getReportsBtn() {
		return reportsBtn;
	}
}
