package com.neusoft.hs.portal.swing.ui.main_menu.view;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.util.VersionUtil;
import com.neusoft.hs.portal.framework.util.UIUtil;
import com.neusoft.hs.portal.swing.util.Borders;
import com.neusoft.hs.portal.swing.util.ConstMessagesCN;
import com.neusoft.hs.portal.swing.util.LookAndFeelUtils;

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
		Image iconImage = UIUtil.getImage("eve.jpg");
		setIconImage(iconImage); 
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
				+ ConstMessagesCN.Labels.BuildDate + ":"
				+ VersionUtil.getBuildDate());

		Image iconImage = UIUtil.getImage("hope.jpg");
		ImageIcon background = new ImageIcon(iconImage);// 背景图片
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(230, 0, background.getIconWidth(),
				background.getIconHeight());
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// VersionDialog d = new VersionDialog();
				// d.setModal(true);
				// d.setVisible(true);
			}
		});

		versionPanel.add(label);
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
